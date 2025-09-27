USE `santanderBaseChallenge`;
DELIMITER $$

CREATE PROCEDURE `sp_criar_rede_arestas`(IN p_inicio DATE, IN p_fim DATE)
BEGIN
  /* Idempotência: remove a materialização anterior do mesmo período */
  DELETE FROM `rede_arestas`
   WHERE `periodo_inicio` = p_inicio
     AND `periodo_fim`    = p_fim;

  /* Materialização sem temporary tables:
     - e      : volume u->v no período (sem self-loop)
     - outdeg : total pago por u
     - indeg  : total recebido por v
     - dep_out = vol_uv / out(u)
     - dep_in  = vol_uv / in (v)
  */
  INSERT INTO `rede_arestas` (
    `periodo_inicio`, `periodo_fim`, `u`, `v`, `vol_uv`, `dep_out`, `dep_in`
  )
  WITH
  e AS (
    SELECT
      TRIM(t.id_pgto) AS u,
      TRIM(t.id_rcbe) AS v,
      SUM(t.vl)       AS vol_uv
    FROM `transacao` t
    WHERE t.`dt_refe` BETWEEN p_inicio AND p_fim
      AND t.`id_pgto` <> t.`id_rcbe`
    GROUP BY TRIM(t.id_pgto), TRIM(t.id_rcbe)
  ),
  outdeg AS (
    SELECT u, SUM(vol_uv) AS out_u
    FROM e
    GROUP BY u
  ),
  indeg AS (
    SELECT v AS u, SUM(vol_uv) AS in_u
    FROM e
    GROUP BY v
  )
  SELECT
    p_inicio,
    p_fim,
    e.u,
    e.v,
    e.vol_uv,
    e.vol_uv / NULLIF(o.out_u, 0) AS dep_out,
    e.vol_uv / NULLIF(i.in_u, 0)  AS dep_in
  FROM e
  JOIN outdeg o ON o.u = e.u
  JOIN indeg  i ON i.u = e.v;
END $$
DELIMITER ;