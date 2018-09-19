package fillDB;

import university_work.Student;
import university_work.StudentGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FillDB {
    // JDBC URL, username and password of postgreSQL server
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/University";
    private static final String user = "postgres";
    private static final String password = "12345";

    // JDBC variables for opening and managing connection
    private static Connection connection;
    private static Statement statement;


    public static void fill() {

        try {
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            //System.out.println("Драйвер подключен");

            //Создаём соединение
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Соединение установлено FillDB");

            // getting Statement object to execute query
            statement = connection.createStatement();

            //Создаём таблицу Groups
            try {
                ResultSet resultSetGroups = statement.executeQuery(SQLQueries.createDBTableGroups());
                resultSetGroups.close();
            }
            catch (SQLException ex){
                Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Заполняем таблицу Groups
            ArrayList<StudentGroup> groups = Generate.generateDataGroups();
            try {
                for (int i = 0; i < groups.size(); ++i) {
                    ResultSet rsGF = statement.executeQuery(groups.get(i).addSQLQuery());
                    rsGF.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Создаём таблицу Students
            try {
                ResultSet resultSetStudents = statement.executeQuery(SQLQueries.createDBTableStudents());
                resultSetStudents.close();
            }
            catch (SQLException ex) {
                    Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Заполняем таблицу Students
            try {
                ArrayList<Student> students = Generate.generateDataStudents(8);
                for (int i = 0; i < groups.size(); ++i) {
                    students.get(i).addStudent(statement);
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);
            }


            statement.close();
            connection.close();
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }

    }


}
