CREATE TABLE partidas (
	id serial4 NOT NULL,
	valor_ganho numeric(13, 2) NULL,
	quantidade_big_blind numeric(13, 2) NULL,
	quantidade_maos_inicio int4 NULL,
	quantidade_maos_fim int4 NULL,
	fichas_iniciais numeric(13, 2) NULL,
	fichas_finais numeric(13, 2) NULL,
	data_inicio timestamp NOT NULL,
	data_fim timestamp NULL,
	limite_id int4 NOT NULL,
	usuario_id int4 NULL,
	link varchar NULL,
	CONSTRAINT partidas_pkey PRIMARY KEY (id),
    CONSTRAINT partidas_fk_1 FOREIGN KEY (limite_id) REFERENCES limites(id),
    CONSTRAINT partidas_fk_2 FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
