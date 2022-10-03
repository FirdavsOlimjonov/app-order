package uz.pdp.apporder.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class DataLoader implements CommandLineRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Override
    @Transactional
    public void run(String... args)  {
        if (Objects.equals(ddlMode, "create") || Objects.equals(ddlMode, "create-drop")) {

            Connection connection = getConnection();
//            b.id, o.id, o.client_id, o.operator_id, o.payment_type,  o.status_enum, o.ordered_at
            try {
                Statement statement = connection.createStatement();
                statement.execute("CREATE OR REPLACE FUNCTION  get_result_of_query(sql_query character varying)\n" +
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
                        "$$;\n");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                closeConnection(connection);
            }


        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    protected Connection getConnection() {
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
            String user = "postgres";
            String password = "1223";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
