package ru.netology.data;

import com.codeborne.selenide.Selenide;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes WHERE created >= (SELECT MAX(created) FROM auth_codes);";
        var conn = getConnection();
        Selenide.sleep(1000);
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static DataHelper.AuthInfo addNewUser() {
        var conn = getConnection();
        var login = DataHelper.getRandomLogin();
        var password = DataHelper.getPasswordForRandomLogin();
        var id = DataHelper.getID();
        String sql = "INSERT INTO users (id, login, password) VALUES (?, ?, ?)";
        runner.update(conn, sql, id, login, password);
        return new DataHelper.AuthInfo(login, password);
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var conn = getConnection();
        runner.execute(conn, "DELETE from auth_codes");
        runner.execute(conn, "DELETE from card_transactions");
        runner.execute(conn, "DELETE from cards");
        runner.execute(conn, "DELETE from users");
    }

    @SneakyThrows
    public static void cleanDataBaseAfterAddNewRandomUser() {
        var conn = getConnection();
        String sql1 = "DELETE from auth_codes WHERE user_id = ?";
        runner.execute(conn, sql1, DataHelper.getID());
        String sql2 = "DELETE from users WHERE id = ?";
        runner.execute(conn, sql2, DataHelper.getID());
    }
}


