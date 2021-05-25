CREATE TABLE public.users
(
    id integer NOT NULL,
    firstname character varying(30) COLLATE pg_catalog."default" NOT NULL,
    lastname character varying(30) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default",
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    password character varying(30) COLLATE pg_catalog."default" NOT NULL,
    photo character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;
	
CREATE TABLE public.roles
(
    role_id integer NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (role_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.roles
    OWNER to postgres;
	
CREATE TABLE public.users_roles
(
    role_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_id)
        REFERENCES public.roles (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users_roles
    OWNER to postgres;

CREATE TABLE public.stores
(
    id integer NOT NULL,
    storename character varying(50) COLLATE pg_catalog."default" NOT NULL,
    user_id integer,
    CONSTRAINT stores_pkey PRIMARY KEY (id),
    CONSTRAINT pk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.stores
    OWNER to postgres;
	
CREATE TABLE public.privilege
(
    privilege_id integer NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT privilege_pkey PRIMARY KEY (privilege_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.privilege
    OWNER to postgres;
	
CREATE TABLE public.roles_privileges
(
    privilege_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT fk_privilege FOREIGN KEY (privilege_id)
        REFERENCES public.privilege (privilege_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_role FOREIGN KEY (role_id)
        REFERENCES public.roles (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.roles_privileges
    OWNER to postgres;
	
CREATE TABLE public.product
(
    id integer NOT NULL,
    productname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    productdescription character varying(200) COLLATE pg_catalog."default" NOT NULL,
    productprice character varying COLLATE pg_catalog."default" NOT NULL,
    quantity integer NOT NULL,
    store_id integer NOT NULL,
    photo character varying COLLATE pg_catalog."default",
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT fk_store FOREIGN KEY (store_id)
        REFERENCES public.stores (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.product
    OWNER to postgres;
	
CREATE TABLE public.orders
(
    id integer,
    user_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer,
    store_id integer,
    address character varying COLLATE pg_catalog."default",
    "time" character varying COLLATE pg_catalog."default",
    CONSTRAINT customer_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT product_fk FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.orders
    OWNER to postgres;