package fillDB;

public class SQLQueries {
    public SQLQueries() {
    }

    public static String createDBTableGroups() {
        String result = "CREATE TABLE public.\"Groups\"\n(\n    \"GroupNumber\" integer NOT NULL,\n    \"Faculty\" character varying(20) COLLATE pg_catalog.\"default\",\n    CONSTRAINT \"Groups_pkey\" PRIMARY KEY (\"GroupNumber\")\n)\nWITH (\n    OIDS = FALSE\n)\nTABLESPACE pg_default;\n\nALTER TABLE public.\"Groups\"\n    OWNER to postgres;";
        return result;
    }

    public static String createDBTableStudents() {
        String result = "CREATE TABLE public.\"Students\"\n(\n    \"FullName\" character varying(20) COLLATE pg_catalog.\"default\" ,\n    \"GroupNumber\" integer NOT NULL,\n    \"ScholarshipType\" integer NOT NULL,\n    start_date date,\n    CONSTRAINT \"Students_pkey\" PRIMARY KEY (\"FullName\")\n)\nWITH (\n    OIDS = FALSE\n)\nTABLESPACE pg_default;\n\nALTER TABLE public.\"Students\"\n    OWNER to postgres;";
        return result;
    }
}
