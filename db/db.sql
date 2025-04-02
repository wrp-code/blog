-- 创建博客
CREATE TABLE IF NOT EXISTS public.article
(
    id bigint,
    create_time timestamp without time zone NOT NULL DEFAULT now(),
    update_time timestamp without time zone NOT NULL DEFAULT now(),
    deleted smallint NOT NULL DEFAULT 0,
    title character varying(64)  NOT NULL,
    content text NOT NULL,
    user_id bigint NOT NULL,
    catalog_id bigint NOT NULL,
    hits bigint NOT NULL DEFAULT 0,
    CONSTRAINT article_pkey PRIMARY KEY (id)
);

-- 创建文件表
CREATE TABLE IF NOT EXISTS public.attachment
(
    id bigint,
    create_time timestamp without time zone NOT NULL DEFAULT now(),
    update_time timestamp without time zone NOT NULL DEFAULT now(),
    deleted smallint NOT NULL DEFAULT 0,
    user_id bigint NOT NULL,
    file_name character varying  NOT NULL,
    url character varying  NOT NULL,
    file_type smallint NOT NULL,
    file_size bigint NOT NULL,
    CONSTRAINT file_pkey PRIMARY KEY (id)
);

-- 创建用户表
CREATE TABLE IF NOT EXISTS public.b_user
(
    id bigint,
    create_time timestamp without time zone NOT NULL DEFAULT now(),
    update_time timestamp without time zone NOT NULL DEFAULT now(),
    deleted smallint NOT NULL DEFAULT 0,
    username character varying(15)  NOT NULL,
    password character varying(32)  NOT NULL,
    phone character varying(11)  NOT NULL,
    email character varying(32)  NOT NULL,
    description character varying(255) ,
    avatar bigint,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

-- 用户表电话设置唯一索引
CREATE UNIQUE INDEX IF NOT EXISTS b_user_phone_idx
    ON public.b_user USING btree
    (phone COLLATE pg_catalog."default" ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pg_default;

-- 用户表用户名设置唯一索引
CREATE UNIQUE INDEX IF NOT EXISTS b_user_username_idx
    ON public.b_user USING btree
    (username COLLATE pg_catalog."default" ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pg_default;

-- 创建目录表
CREATE TABLE IF NOT EXISTS public.catalog
(
    id bigint,
    create_time timestamp without time zone NOT NULL DEFAULT now(),
    update_time timestamp without time zone NOT NULL DEFAULT now(),
    deleted smallint NOT NULL DEFAULT 0,
    user_id bigint NOT NULL,
    catalog_name character varying(16)  NOT NULL,
    CONSTRAINT catalog_pkey PRIMARY KEY (id)
);