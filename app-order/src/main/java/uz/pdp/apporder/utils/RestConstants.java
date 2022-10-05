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

}
