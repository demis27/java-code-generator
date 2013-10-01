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
CREATE TABLE "Test"."SimpleTable"
(
  "SimpleColumn" integer -- A simple column
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