package university_work;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fillDB.SQLConnection.breakConnection;
import static fillDB.SQLConnection.establishConnection;


public class Main {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/University";
    private static final String user = "postgres";
    private static final String password = "12345";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;


    public static void main(String args[]) {

        //FillDB.fill();

        try {
            //Загружаем драйвер
            //Class.forName("org.postgresql.Driver");
            //System.out.println("Драйвер подключен");
            con = establishConnection(user, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Драйвер не пошёл");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();

            System.out.println(Student.stringListStudents(stmt));
            /*
            Student student = new Student("Sarah Connor", 308, 1, "01.09.2007");
            student.addStudent(stmt);
            System.out.println(Student.listStudents(stmt));
            student.deleteStudent(stmt);
            System.out.println(Student.listStudents(stmt));
            */

            stmt.close();
            con.close();
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            breakConnection();
        }

    }
}
