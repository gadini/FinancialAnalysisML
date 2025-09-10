-- =========================================================
-- 2) TRANSACOES (jan–mai/2025)
-- Regras de recebimento (id_rcbe) por perfil alvo:
--  - CNPJ_30001 (Empresa 1): Início → Início → Expansão → Expansão → Maturidade
--      Recebe ~6k, ~8k, ~30k, ~60k, ~70k
--  - CNPJ_30002 (Empresa 2): Maturidade → Maturidade → Maturidade → Declínio → Declínio
--      Recebe ~60k, ~62k, ~58k, ~35k, ~18k
--  - CNPJ_30003 (Empresa 3): Maturidade → Declínio → Declínio → Declínio → Declínio
--      Recebe ~50k, ~35k, ~25k, ~15k, ~8k
--  - CNPJ_30004 (Empresa 4): Início → Início → Início → Expansão → Maturidade
--      Recebe ~5k, ~7k, ~9k, ~30k, ~55k
-- =========================================================

-- ----------------
-- JANEIRO / 2025
-- ----------------
INSERT INTO transacao (id_pgto,    id_rcbe,     vl,   ds_tran, dt_refe) VALUES
('CNPJ_30002','CNPJ_30001',  4000, 'PIX',     '2025-01-10'),
('CNPJ_30003','CNPJ_30001',  2000, 'PIX',     '2025-01-18'),

('CNPJ_30001','CNPJ_30002', 40000, 'TED',     '2025-01-12'),
('CNPJ_30004','CNPJ_30002', 20000, 'PIX',     '2025-01-23'),

('CNPJ_30002','CNPJ_30003', 30000, 'PIX',     '2025-01-15'),
('CNPJ_30004','CNPJ_30003', 20000, 'BOLETO',  '2025-01-26'),

('CNPJ_30001','CNPJ_30004',  3000, 'PIX',     '2025-01-09'),
('CNPJ_30003','CNPJ_30004',  2000, 'BOLETO',  '2025-01-21');

-- ----------------
-- FEVEREIRO / 2025
-- ----------------
INSERT INTO transacao (id_pgto,    id_rcbe,     vl,   ds_tran, dt_refe) VALUES
('CNPJ_30002','CNPJ_30001',  5000, 'PIX',     '2025-02-08'),
('CNPJ_30003','CNPJ_30001',  3000, 'PIX',     '2025-02-17'),

('CNPJ_30001','CNPJ_30002', 42000, 'PIX',     '2025-02-11'),
('CNPJ_30004','CNPJ_30002', 20000, 'TED',     '2025-02-24'),

('CNPJ_30002','CNPJ_30003', 20000, 'BOLETO',  '2025-02-05'),
('CNPJ_30004','CNPJ_30003', 15000, 'PIX',     '2025-02-19'),

('CNPJ_30001','CNPJ_30004',  4000, 'PIX',     '2025-02-06'),
('CNPJ_30003','CNPJ_30004',  3000, 'PIX',     '2025-02-27');

-- ----------------
-- MARÇO / 2025
-- ----------------
INSERT INTO transacao (id_pgto,    id_rcbe,     vl,   ds_tran, dt_refe) VALUES
('CNPJ_30002','CNPJ_30001', 15000, 'PIX',     '2025-03-04'),
('CNPJ_30004','CNPJ_30001', 15000, 'PIX',     '2025-03-13'),
('CNPJ_30003','CNPJ_30001',  5000, 'TED',     '2025-03-22'),

('CNPJ_30001','CNPJ_30002', 38000, 'PIX',     '2025-03-08'),
('CNPJ_30003','CNPJ_30002', 20000, 'BOLETO',  '2025-03-28'),

('CNPJ_30002','CNPJ_30003', 15000, 'PIX',     '2025-03-10'),
('CNPJ_30004','CNPJ_30003', 10000, 'PIX',     '2025-03-20'),

('CNPJ_30001','CNPJ_30004',  5000, 'PIX',     '2025-03-06'),
('CNPJ_30002','CNPJ_30004',  4000, 'BOLETO',  '2025-03-25');

-- ----------------
-- ABRIL / 2025
-- ----------------
INSERT INTO transacao (id_pgto,    id_rcbe,     vl,   ds_tran, dt_refe) VALUES
('CNPJ_30002','CNPJ_30001', 30000, 'PIX',     '2025-04-05'),
('CNPJ_30004','CNPJ_30001', 20000, 'TED',     '2025-04-18'),
('CNPJ_30003','CNPJ_30001', 10000, 'PIX',     '2025-04-26'),

('CNPJ_30001','CNPJ_30002', 20000, 'PIX',     '2025-04-09'),
('CNPJ_30003','CNPJ_30002', 15000, 'PIX',     '2025-04-14'),

('CNPJ_30001','CNPJ_30003',  7000, 'BOLETO',  '2025-04-03'),
('CNPJ_30002','CNPJ_30003',  8000, 'PIX',     '2025-04-21'),

('CNPJ_30001','CNPJ_30004', 12000, 'PIX',     '2025-04-07'),
('CNPJ_30003','CNPJ_30004', 18000, 'TED',     '2025-04-28');

-- ----------------
-- MAIO / 2025
-- ----------------
INSERT INTO transacao (id_pgto,    id_rcbe,     vl,   ds_tran, dt_refe) VALUES
('CNPJ_30002','CNPJ_30001', 40000, 'PIX',     '2025-05-06'),
('CNPJ_30003','CNPJ_30001', 20000, 'TED',     '2025-05-15'),
('CNPJ_30004','CNPJ_30001', 10000, 'PIX',     '2025-05-23'),

('CNPJ_30001','CNPJ_30002', 10000, 'PIX',     '2025-05-10'),
('CNPJ_30004','CNPJ_30002',  8000, 'BOLETO',  '2025-05-28'),

('CNPJ_30001','CNPJ_30003',  4000, 'PIX',     '2025-05-03'),
('CNPJ_30002','CNPJ_30003',  4000, 'PIX',     '2025-05-20'),

('CNPJ_30002','CNPJ_30004', 25000, 'PIX',     '2025-05-08'),
('CNPJ_30003','CNPJ_30004', 30000, 'TED',     '2025-05-27');