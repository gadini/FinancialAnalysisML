USE santanderBaseChallenge;
DELIMITER $$

CREATE PROCEDURE sp_calcular_metricas_por_periodo(IN p_inicio DATE, IN p_fim DATE)
BEGIN

  INSERT INTO metricas_financeiras (
    empresa_id,
    periodo_inicio,
    periodo_fim,
    media_recebimentos,
    media_pagamentos,
    qtd_clientes
  )
  SELECT
    e.id AS empresa_id,
    p_inicio AS periodo_inicio,
    p_fim    AS periodo_fim,

    COALESCE(AVG(CASE WHEN tb.id_rcbe = e.id_externo THEN tb.vl END), 0) AS media_recebimentos,
    COALESCE(AVG(CASE WHEN tb.id_pgto = e.id_externo THEN tb.vl END), 0) AS media_pagamentos,

    COALESCE(
      COUNT(DISTINCT
        CASE
          WHEN tb.id_pgto = e.id_externo THEN tb.id_rcbe
          WHEN tb.id_rcbe = e.id_externo THEN tb.id_pgto
          ELSE NULL
        END
      ), 0
    ) AS qtd_clientes

  FROM empresa e
  LEFT JOIN transacao_base tb
    ON (tb.id_pgto = e.id_externo OR tb.id_rcbe = e.id_externo)
   AND tb.dt_refe BETWEEN p_inicio AND p_fim

  GROUP BY e.id

  ON DUPLICATE KEY UPDATE
    media_recebimentos = VALUES(media_recebimentos),
    media_pagamentos   = VALUES(media_pagamentos),
    qtd_clientes       = VALUES(qtd_clientes),
    updated_at         = CURRENT_TIMESTAMP;
END $$
DELIMITER ;
