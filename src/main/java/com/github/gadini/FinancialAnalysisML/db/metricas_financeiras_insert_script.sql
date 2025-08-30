use santanderchallenge;

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_calcular_metricas_por_periodo $$
CREATE PROCEDURE sp_calcular_metricas_por_periodo(IN p_inicio DATE, IN p_fim DATE)
BEGIN
    INSERT INTO metricas_financeiras (
        empresa_id,
        periodo_inicio,
        periodo_fim,
        media_recebimentos,
        media_pagamentos,
        volume_credito,
        total_investimentos,
        inadimplencia_percentual,
        qtd_clientes
    )
    SELECT
        e.id AS empresa_id,
        p_inicio AS periodo_inicio,
        p_fim AS periodo_fim,

        /* médias (sempre 0 se não houver transações) */
        COALESCE(AVG(CASE WHEN t.tipo = 'RECEBIMENTO' THEN t.valor END), 0) AS media_recebimentos,
        COALESCE(AVG(CASE WHEN t.tipo = 'PAGAMENTO'  THEN t.valor END), 0) AS media_pagamentos,

        /* somas */
        COALESCE(SUM(CASE WHEN t.tipo = 'CREDITO'      THEN t.valor END), 0) AS volume_credito,
        COALESCE(SUM(CASE WHEN t.tipo = 'INVESTIMENTO' THEN t.valor END), 0) AS total_investimentos,

        /* % inadimplência (sobre total do período, 0 se não houver) */
        COALESCE(ROUND(
            100 * SUM(CASE WHEN t.tipo = 'INADIMPLENCIA' THEN t.valor END)
            / NULLIF(SUM(t.valor), 0), 2), 0) AS inadimplencia_percentual,

        /* não derivável com as tabelas atuais */
        0 AS qtd_clientes
    FROM empresa e
    LEFT JOIN transacao t
        ON t.empresa_id = e.id
       AND t.data BETWEEN p_inicio AND p_fim
    GROUP BY e.id

    ON DUPLICATE KEY UPDATE
        media_recebimentos       = VALUES(media_recebimentos),
        media_pagamentos         = VALUES(media_pagamentos),
        volume_credito           = VALUES(volume_credito),
        total_investimentos      = VALUES(total_investimentos),
        inadimplencia_percentual = VALUES(inadimplencia_percentual),
        qtd_clientes             = VALUES(qtd_clientes),
        updated_at               = CURRENT_TIMESTAMP;
END $$
DELIMITER ;