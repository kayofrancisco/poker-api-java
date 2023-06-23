CREATE TABLE usuarios (
	id serial4 NOT NULL,
	nome varchar NULL,
	email varchar NULL,
	senha varchar NULL,
	criado_em timestamp NULL,
	ultima_atualizacao timestamp NULL,
	ativo bool NULL,
	perfil_id int4 NOT NULL,
	CONSTRAINT usuarios_email_key UNIQUE (email),
	CONSTRAINT usuarios_pkey PRIMARY KEY (id),
    CONSTRAINT usuarios_fk FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);