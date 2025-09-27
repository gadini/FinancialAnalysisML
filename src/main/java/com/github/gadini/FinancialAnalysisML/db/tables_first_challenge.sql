use santanderBaseChallenge;

CREATE TABLE empresa (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  id_externo VARCHAR(64) NOT NULL,
  vl_fatu BIGINT NULL,
  vl_sldo BIGINT NULL,
  dt_abrt DATE NULL,
  ds_cnae VARCHAR(255) NULL,
  dt_refe DATE NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE INDEX idx_empresa_dt_refe ON empresa (dt_refe);

CREATE TABLE transacao (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  id_pgto  VARCHAR(64) NOT NULL,
  id_rcbe  VARCHAR(64) NOT NULL,
  vl       BIGINT NOT NULL,
  ds_tran  ENUM('PIX','TED','BOLETO','SISTEMICO') NOT NULL,
  dt_refe  DATE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE INDEX idx_transb_dt_refe ON transacao (dt_refe);
CREATE INDEX idx_transb_pgto   ON transacao (id_pgto);
CREATE INDEX idx_transb_rcbe   ON transacao (id_rcbe);

CREATE TABLE metricas_financeiras (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empresa_id BIGINT NOT NULL,
    periodo_inicio DATE NOT NULL,
    periodo_fim DATE NOT NULL,
    media_recebimentos DECIMAL(15,2),
    media_pagamentos DECIMAL(15,2),
    qtd_clientes INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_metricas_empresa FOREIGN KEY (empresa_id) REFERENCES empresa (id),
    CONSTRAINT uq_metricas_empresa_periodo UNIQUE (empresa_id, periodo_inicio, periodo_fim)
);

CREATE TABLE classificacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empresa_id BIGINT NOT NULL,
    momento ENUM('INICIO', 'EXPANSAO', 'MATURIDADE', 'DECLINIO') NOT NULL,
    data_analise DATE NOT NULL,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (empresa_id) REFERENCES santanderchallenge.empresa(id)
);
