CREATE TABLE IF NOT EXISTS `rede_arestas` (
  `periodo_inicio` DATE NOT NULL,
  `periodo_fim`    DATE NOT NULL,
  `u`              VARCHAR(64) NOT NULL,
  `v`              VARCHAR(64) NOT NULL,
  `vol_uv`         BIGINT NOT NULL,
  `dep_out`        DECIMAL(10,6) NOT NULL,
  `dep_in`         DECIMAL(10,6) NOT NULL,
  `created_at`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`periodo_inicio`, `periodo_fim`, `u`, `v`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS rede_metricas (
  periodo_inicio DATE NOT NULL,
  periodo_fim    DATE NOT NULL,
  id_externo     VARCHAR(64) NOT NULL,
  degree_out     DECIMAL(10,6),
  degree_in      DECIMAL(10,6),
  pagerank       DECIMAL(10,6),
  betweenness    DECIMAL(18,8),
  eigenvector    DECIMAL(10,6),
  exposure_out   DECIMAL(10,6),
  comunidade     BIGINT,
  created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (periodo_inicio, periodo_fim, id_externo)
) ENGINE=InnoDB;


