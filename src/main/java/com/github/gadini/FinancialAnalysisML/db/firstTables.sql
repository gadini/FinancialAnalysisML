use santanderchallenge;

CREATE TABLE empresa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cnpj VARCHAR(20) UNIQUE NOT NULL,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    data_abertura DATE,
    segmento VARCHAR(100),
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE transacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empresa_id BIGINT NOT NULL,
    data DATE NOT NULL,
    tipo ENUM('RECEBIMENTO', 'PAGAMENTO', 'CREDITO', 'INVESTIMENTO', 'INADIMPLENCIA') NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (empresa_id) references santanderchallenge.empresa(id)
);

CREATE TABLE classificacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empresa_id BIGINT NOT NULL,
    momento ENUM('INICIO', 'EXPANSAO', 'MATURIDADE', 'DECLINIO') NOT NULL,
    score_analitico DECIMAL(5,2), -- Ex: silhueta ou confiabilidade
    data_analise DATE NOT NULL,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (empresa_id) REFERENCES santanderchallenge.empresa(id)
);

CREATE TABLE empresa_relacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empresa_origem_id BIGINT NOT NULL,
    empresa_destino_id BIGINT NOT NULL,
    tipo_relacao ENUM('FORNECEDOR', 'CLIENTE', 'PARCEIRA', 'INVESTIDORA') NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    status ENUM('ATIVO', 'INATIVO') NOT NULL DEFAULT 'ATIVO',
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (empresa_origem_id) REFERENCES santanderchallenge.empresa(id),
    FOREIGN KEY (empresa_destino_id) REFERENCES santanderchallenge.empresa(id),

    UNIQUE (empresa_origem_id, empresa_destino_id, tipo_relacao)
);

CREATE TABLE metricas_financeiras (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empresa_id BIGINT NOT NULL,
    periodo_inicio DATE,
    periodo_fim DATE,
    media_recebimentos DECIMAL(15,2),
    media_pagamentos DECIMAL(15,2),
    volume_credito DECIMAL(15,2),
    total_investimentos DECIMAL(15,2),
    inadimplencia_percentual DECIMAL(5,2),
    qtd_clientes INT,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (empresa_id) REFERENCES santanderchallenge.empresa(id)
);

-- Talvez utilizar
create table historico_credito (
    id bigint auto_increment primary key,
    empresa_id bigint not null,
    data_referencia date not null,              -- geralmente mês/ano
    limite_concedido decimal(18,2) not null,
    utilizado decimal(18,2) not null,
    inadimplencia decimal(5,2),                 -- % atraso no período
    status varchar(20),                         -- ATIVO, BLOQUEADO, RENEGOCIADO...
    atualizado_em timestamp not null default current_timestamp on update current_timestamp,
    foreign key (empresa_id) references empresa(id)
);
