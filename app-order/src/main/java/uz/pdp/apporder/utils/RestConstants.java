package uz.pdp.apporder.utils;

public interface RestConstants {
    String DEFAULT_PAGE_NUMBER = "1";
    String DEFAULT_PAGE_SIZE = "10";

    String AUTHENTICATION_HEADER = "Authorization";

    String INITIAL_SEARCHING_FUNCTION = "DROP FUNCTION IF EXISTS get_result_of_query;" +
            "CREATE OR REPLACE FUNCTION  get_result_of_query(sql_query character varying)\n" +
            "    returns TABLE(branch_id int, " +
            "order_id bigint, " +
            "client_id varchar, " +
            "operator_id varchar, " +
            "payment_type varchar, " +
            "status_enum varchar," +
            "  ordered_at timestamp without time zone)\n" +
            "    language plpgsql\n" +
            "as\n" +
            "$$\n" +

            "BEGIN\n" +
            "    RETURN QUERY\n" +
            "        EXECUTE sql_query;\n" +
            "END\n" +
            "$$";

}
