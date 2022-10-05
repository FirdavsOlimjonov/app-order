package uz.pdp.apporder.utils;

public interface RestConstants {

    String INITIAL_SEARCHING_FUNCTION = "DROP FUNCTION IF EXISTS get_result_of_query;" +
            "CREATE OR REPLACE FUNCTION  get_result_of_query(sql_query character varying)\n" +
            "    returns TABLE(branchId int, " +
            "orderId bigint, " +
            "clientId varchar, " +
            "operatorId varchar, " +
            "paymentType varchar, " +
            "statusEnum varchar," +
            "  orderedAt timestamp without time zone)\n" +
            "    language plpgsql\n" +
            "as\n" +
            "$$\n" +

            "BEGIN\n" +
            "    RETURN QUERY\n" +
            "        EXECUTE sql_query;\n" +
            "END\n" +
            "$$";


    String INITIAL_PRODUCT_FUNCTION = "DROP FUNCTION IF EXISTS get_query_result;" +
            " create function get_query_result(sql_query text) " +
            "    returns TABLE(id integer, active boolean, description character varying, name character varying, price real, category_id integer, discount_id integer, promotion_id integer) " +
            "    language plpgsql " +
            "as " +
            "$$ " +
            "BEGIN " +
            "    RETURN QUERY " +
            "        EXECUTE sql_query; " +
            "END " +
            "$$;";
}
