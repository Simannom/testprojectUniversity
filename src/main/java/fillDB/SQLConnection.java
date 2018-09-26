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
    public static final String default_user = "postgres";
    public static final String default_password = "12345";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;

    //здесь всё ок
    public static Connection establishConnection(String user, String password) throws SQLException, ClassNotFoundException  {

        //FillDB.fill();
        Class.forName("org.postgresql.Driver");
        //System.out.println("Драйвер подключен");

        con = DriverManager.getConnection(url, user, password);
        System.out.println("Соединение установлено SQLConnection");

        return con;

    }

    public static void ensureConnection() {
        Connection connection = SQLConnection.getConnection();
        if (connection == null) {
            try {
                connection = SQLConnection.establishConnection(
                        SQLConnection.default_user,SQLConnection.default_password
                );
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static Connection getConnection(){
        return con;
    }

    public static void breakConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("SQLConnection: Connection broken");
    }


}
