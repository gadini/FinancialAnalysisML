INSERT into santanderchallenge.empresa (id, cnpj, razao_social, nome_fantasia , data_abertura, segmento)
VALUES
(1, '00.000.000/0001-01', 'Empresa Fire Tecnologias', 'Empresa Fire', '2024-05-10', 'Tecnologia'),
(2, '00.000.000/0002-02', 'Empresa Water Logisticas', 'Empresa Water', '2023-03-15', 'Logística'),
(3, '00.000.000/0003-03', 'Empresa Earth Servicos', 'Empresa Earth', '2025-07-22', 'Serviços');

INSERT INTO santanderchallenge.transacao (empresa_id, data, tipo, valor, descricao) VALUES
(1, '2025-08-01', 'RECEBIMENTO', 100000.00, 'Receita de contrato com cliente'),
(1, '2025-08-05', 'PAGAMENTO', 40000.00, 'Pagamento a fornecedor de tecnologia'),
(1, '2025-08-10', 'INVESTIMENTO', 20000.00, 'Compra de equipamentos de rede'),
(1, '2025-08-15', 'CREDITO', 30000.00, 'Empréstimo bancário para capital de giro'),
(1, '2025-08-20', 'INADIMPLENCIA', 5000.00, 'Cliente não pagou fatura de julho');

INSERT INTO santanderchallenge.transacao (empresa_id, data, tipo, valor, descricao) VALUES
(2, '2025-08-01', 'RECEBIMENTO', 50000.00, 'Pagamento de transporte'),
(2, '2025-08-02', 'PAGAMENTO', 25000.00, 'Combustível e manutenção da frota'),
(2, '2025-08-05', 'CREDITO', 10000.00, 'Limite rotativo usado para despesas operacionais'),
(2, '2025-08-10', 'INADIMPLENCIA', 3000.00, 'Cliente atrasou pagamento de frete');

INSERT INTO santanderchallenge.transacao (empresa_id, data, tipo, valor, descricao) VALUES
(3, '2025-08-01', 'RECEBIMENTO', 15000.00, 'Serviços'),
(3, '2025-08-02', 'PAGAMENTO', 12000.00, 'Salários da equipe de suporte técnico'),
(3, '2025-08-03', 'CREDITO', 8000.00, 'Financiamento de curto prazo via fintech'),
(3, '2025-08-05', 'INADIMPLENCIA', 2000.00, 'Cliente não efetuou pagamento');

INSERT INTO santanderchallenge.empresa_relacao (empresa_origem_id, empresa_destino_id, tipo_relacao, data_inicio) VALUES
(1, 2, 'FORNECEDOR', '2024-01-10'),  -- Fire compra da Water
(2, 3, 'CLIENTE', '2024-03-05'),     -- Water vende para Earth
(3, 1, 'PARCEIRA', '2023-07-20');    -- Earth e Fire são parceiras