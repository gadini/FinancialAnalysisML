CREATE DEFINER=`root`@`localhost` PROCEDURE `santanderbasechallenge`.`sp_calcular_metricas_por_periodo`(IN p_inicio DATE, IN p_fim DATE)
BEGIN
  /*
    Pré-requisito recomendado:
    ALTER TABLE `metricas_financeiras`
      ADD UNIQUE KEY `uk_empresa_periodo` (`empresa_id`,`periodo_inicio`,`periodo_fim`);
  */

  INSERT INTO `metricas_financeiras` (
    `empresa_id`,
    `periodo_inicio`,
    `periodo_fim`,
    `media_recebimentos`,
    `media_pagamentos`,
    `qtd_clientes`,
    `updated_at`
  )
  SELECT
    e.`id`                                  AS `empresa_id`,
    p_inicio                                 AS `periodo_inicio`,
    p_fim                                    AS `periodo_fim`,

    /* Média dos valores onde a empresa é RECEBEDORA */
    COALESCE(AVG(CASE WHEN t.`id_rcbe` = e.`id_externo` THEN t.`vl` END), 0) AS `media_recebimentos`,

    /* Média dos valores onde a empresa é PAGADORA */
    COALESCE(AVG(CASE WHEN t.`id_pgto` = e.`id_externo` THEN t.`vl` END), 0) AS `media_pagamentos`,

    /* Número de contrapartes distintas (clientes/fornecedores) no período */
    COALESCE(
      COUNT(DISTINCT CASE
        WHEN t.`id_pgto` = e.`id_externo` THEN t.`id_rcbe`
        WHEN t.`id_rcbe` = e.`id_externo` THEN t.`id_pgto`
        ELSE NULL
      END), 0
    ) AS `qtd_clientes`,

    CURRENT_TIMESTAMP AS `updated_at`

  FROM `empresa` e
  /* Filtra transações do período primeiro (melhor para performance) */
  LEFT JOIN (
    SELECT `id_pgto`, `id_rcbe`, `vl`, `dt_refe`
    FROM `transacao`
    WHERE `dt_refe` >= p_inicio
      AND `dt_refe` <  DATE_ADD(p_fim, INTERVAL 1 DAY)  -- intervalo [p_inicio, p_fim]
  ) t
    ON (t.`id_pgto` = e.`id_externo` OR t.`id_rcbe` = e.`id_externo`)

  GROUP BY e.`id`

  ON DUPLICATE KEY UPDATE
    `media_recebimentos` = VALUES(`media_recebimentos`),
    `media_pagamentos`   = VALUES(`media_pagamentos`),
    `qtd_clientes`       = VALUES(`qtd_clientes`),
    `updated_at`         = VALUES(`updated_at`);
END;