CREATE TABLE rakes (
	id serial4 NOT NULL,
	criado_em timestamp NOT NULL,
	valor numeric(13, 2) NOT NULL,
	usuario_id int4 NULL,
	limite_id int4 NULL,
	CONSTRAINT rakes_pkey PRIMARY KEY (id),
    CONSTRAINT rakes_fk FOREIGN KEY (limite_id) REFERENCES limites(id),
    CONSTRAINT usuarios_fk FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
