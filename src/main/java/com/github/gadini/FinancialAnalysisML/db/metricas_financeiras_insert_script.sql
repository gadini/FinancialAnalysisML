USE santanderchallenge;

-- Definindo variáveis
SET @periodo_inicio = '2010-01-01';
SET @periodo_fim = '2025-12-31';

-- Inserção direta dos dados calculados
INSERT INTO santanderchallenge.metricas_financeiras (
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
    t.empresa_id,
    @periodo_inicio,
    @periodo_fim,
    AVG(CASE WHEN t.tipo = 'RECEBIMENTO' THEN t.valor END),
    AVG(CASE WHEN t.tipo = 'PAGAMENTO' THEN t.valor END),
    SUM(CASE WHEN t.tipo = 'CREDITO' THEN t.valor ELSE 0 END),
    SUM(CASE WHEN t.tipo = 'INVESTIMENTO' THEN t.valor ELSE 0 END),
    (SUM(CASE WHEN t.tipo = 'INADIMPLENCIA' THEN t.valor ELSE 0 END) /
     NULLIF(SUM(CASE WHEN t.tipo = 'RECEBIMENTO' THEN t.valor ELSE 0 END), 0)) * 100,
    1
FROM santanderchallenge.transacao AS t
WHERE t.data BETWEEN @periodo_inicio AND @periodo_fim
GROUP BY t.empresa_id;