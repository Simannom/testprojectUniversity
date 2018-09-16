package fillDB;

import university_work.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnection {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/University";
    private static final String default_user = "postgres";
    private static final String default_password = "12345";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;


    public static Connection establishConnection(String user, String password) throws SQLException {

        //FillDB.fill();
        try {
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        con = DriverManager.getConnection(url, user, password);
        System.out.println("Соединение установлено");

        return con;

    }

    public static void breakConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

}
