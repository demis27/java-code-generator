CREATE ROLE test LOGIN
  ENCRYPTED PASSWORD 'md505a671c66aefea124cc08b76ea6d30bb'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

--------------------------------------------------------------------------------
CREATE SCHEMA "Test"
    AUTHORIZATION test;

--------------------------------------------------------------------------------
CREATE TABLE "Test"."EmptyTable"
(

)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."EmptyTable"
  OWNER TO test;
COMMENT ON TABLE "Test"."EmptyTable"
  IS 'An empty table';

--------------------------------------------------------------------------------
-- Table: "Test"."SimpleTable"

-- DROP TABLE "Test"."SimpleTable";

CREATE TABLE "Test"."SimpleTable"
(
  "SimpleColumn" integer, -- A simple column
  not_null_column integer NOT NULL,
  unique_column integer,
  CONSTRAINT unique_column UNIQUE (unique_column)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."SimpleTable"
  OWNER TO test;
COMMENT ON TABLE "Test"."SimpleTable"
  IS 'A simple table';
COMMENT ON COLUMN "Test"."SimpleTable"."SimpleColumn" IS 'A simple column';

--------------------------------------------------------------------------------
CREATE TABLE "Test"."PrimaryTable"
(
  "SimplePrimaryKey" integer NOT NULL, -- Simple column for primary key
  "SimpleColumn" character varying(32), -- Simple character varying column
  CONSTRAINT "SimplePrimaryKey" PRIMARY KEY ("SimplePrimaryKey" )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."PrimaryTable"
  OWNER TO test;
COMMENT ON TABLE "Test"."PrimaryTable"
  IS 'A simple table with a primary key';
COMMENT ON COLUMN "Test"."PrimaryTable"."SimplePrimaryKey" IS 'Simple column for primary key';
COMMENT ON COLUMN "Test"."PrimaryTable"."SimpleColumn" IS 'Simple character varying column';

COMMENT ON CONSTRAINT "SimplePrimaryKey" ON "Test"."PrimaryTable" IS 'A simple Primary Key';

--------------------------------------------------------------------------------
CREATE TABLE "Test"."ComplexPrimaryTable"
(
  "PrimaryKeyPartOne" integer NOT NULL, -- First part of the primary key
  "PrimaryKeyPartTwo" character varying(32) NOT NULL, -- Second part of the primary key
  "SimpleColumn" boolean, -- Simple boolean column
  CONSTRAINT "PrimaryKey" PRIMARY KEY ("PrimaryKeyPartOne" , "PrimaryKeyPartTwo" )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."ComplexPrimaryTable"
  OWNER TO test;
COMMENT ON TABLE "Test"."ComplexPrimaryTable"
  IS 'A table with a complex primary key';
COMMENT ON COLUMN "Test"."ComplexPrimaryTable"."PrimaryKeyPartOne" IS 'First part of the primary key';
COMMENT ON COLUMN "Test"."ComplexPrimaryTable"."PrimaryKeyPartTwo" IS 'Second part of the primary key';
COMMENT ON COLUMN "Test"."ComplexPrimaryTable"."SimpleColumn" IS 'Simple boolean column';


--------------------------------------------------------------------------------
-- Table: "Test"."ForeignKeyTable"

-- DROP TABLE "Test"."ForeignKeyTable";

CREATE TABLE "Test"."ForeignKeyTable"
(
  "ForeignKeyPrimaryKey" integer NOT NULL, -- The primary key of the table
  "SimplePrimaryKey" integer, -- a foreign key on primary key table
  CONSTRAINT "ForeingKeyPrimaryKey" PRIMARY KEY ("ForeignKeyPrimaryKey" ), -- The primary key of the table
  CONSTRAINT "SimpleForeignKey" FOREIGN KEY ("SimplePrimaryKey")
      REFERENCES "Test"."PrimaryTable" ("SimplePrimaryKey") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."ForeignKeyTable"
  OWNER TO test;
COMMENT ON TABLE "Test"."ForeignKeyTable"
  IS 'a table with a foreign key on PrimaryKeyTable';
COMMENT ON COLUMN "Test"."ForeignKeyTable"."ForeignKeyPrimaryKey" IS 'The primary key of the table';
COMMENT ON COLUMN "Test"."ForeignKeyTable"."SimplePrimaryKey" IS 'a foreign key on primary key table';

COMMENT ON CONSTRAINT "ForeingKeyPrimaryKey" ON "Test"."ForeignKeyTable" IS 'The primary key of the table';


-- Index: "Test"."fki_SimpleForeignKey"

-- DROP INDEX "Test"."fki_SimpleForeignKey";

CREATE INDEX "fki_SimpleForeignKey"
  ON "Test"."ForeignKeyTable"
  USING btree
  ("SimplePrimaryKey" );

;

--------------------------------------------------------------------------------
-- Table: "Test"."MultiFkA"

-- DROP TABLE "Test"."MultiFkA";

CREATE TABLE "Test"."MultiFkA"
(
  pk_a integer NOT NULL,
  CONSTRAINT pk_a PRIMARY KEY (pk_a)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."MultiFkA"
  OWNER TO test;

-- Table: "Test"."MultiFkB"

-- DROP TABLE "Test"."MultiFkB";

CREATE TABLE "Test"."MultiFkB"
(
  pk_b integer NOT NULL,
  fk_a1 integer,
  fk_a2 integer,
  CONSTRAINT pk_b PRIMARY KEY (pk_b),
  CONSTRAINT fk_a1 FOREIGN KEY (fk_a1)
      REFERENCES "Test"."MultiFkA" (pk_a) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_a2 FOREIGN KEY (fk_a2)
      REFERENCES "Test"."MultiFkA" (pk_a) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."MultiFkB"
  OWNER TO test;

-- Table: "Test"."MultiFkC"

-- DROP TABLE "Test"."MultiFkC";

CREATE TABLE "Test"."MultiFkC"
(
  pk_c integer NOT NULL,
  fk_a1 integer,
  CONSTRAINT pk_c PRIMARY KEY (pk_c),
  CONSTRAINT fk_a1 FOREIGN KEY (fk_a1)
      REFERENCES "Test"."MultiFkA" (pk_a) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test"."MultiFkC"
  OWNER TO postgres;


--------------------------------------------------------------------------------
-- Table: "Test".ht_filtered

-- DROP TABLE "Test".ht_filtered;

CREATE TABLE "Test".ht_filtered
(
  ht_id integer NOT NULL,
  CONSTRAINT ht_pk PRIMARY KEY (ht_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Test".ht_filtered
  OWNER TO postgres;