-- ---------------------------------- --
-- Gmodel setup script for PostgreSQL --
-- ---------------------------------- --

CREATE DATABASE gmodel WITH ENCODING = 'UTF8';

\connect gmodel

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;


CREATE TABLE document (
    id character varying(36) NOT NULL,
    serialized_instance bytea NOT NULL
);


ALTER TABLE public.document OWNER TO postgres;

CREATE TABLE index (
    uuid character varying(36) NOT NULL,
    urr character varying(36) NOT NULL,
    name text NOT NULL,
    plural_name text NOT NULL,
    type text NOT NULL,
    identity character varying(36) NOT NULL,
    meta_element_id character varying(36) NOT NULL,
    content_id character varying(36) NOT NULL
);


ALTER TABLE public.index OWNER TO postgres;

ALTER TABLE ONLY document
    ADD CONSTRAINT document_pkey PRIMARY KEY (id);


ALTER TABLE ONLY index
    ADD CONSTRAINT index_pkey PRIMARY KEY (uuid);


ALTER TABLE ONLY index
    ADD CONSTRAINT index_content_id_fkey FOREIGN KEY (content_id) REFERENCES document(id);
