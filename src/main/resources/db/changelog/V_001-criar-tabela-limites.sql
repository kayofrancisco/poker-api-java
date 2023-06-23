CREATE TABLE limites (
	id serial4 NOT NULL,
	small_blind numeric(8, 2) NOT NULL,
	big_blind numeric(8, 2) NOT NULL,
	CONSTRAINT limites_big_blind_key UNIQUE (big_blind),
	CONSTRAINT limites_pkey PRIMARY KEY (id),
	CONSTRAINT limites_small_blind_key UNIQUE (small_blind)
);