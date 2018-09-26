package cache;

import university_work.Student;
import university_work.StudentResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static fillDB.SQLConnection.getConnection;

public class CacheSQLQueries {
    public static String selectNamesQuery(){
        return "SELECT \"FullName\" FROM public.\"Students\"";
    }

    public static List<String> selectNames(){
        List <String> studentNames = new ArrayList<>();
        try {
            //SQLConnection.ensureConnection();
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(selectNamesQuery());
            String student = null;
            while (rs.next()) {
                student = rs.getString("FullName");
                studentNames.add(student);
            }
            rs.close();

            statement.close();
        }
        catch (SQLException e){
            System.out.println("CacheSQLQueries selectNames smth wrong");
        }

        return studentNames;
    }

    public static String selectParticularStudentResultQuery(String fullName){
        return "SELECT * " +
                "FROM public.\"Students\" S LEFT JOIN public.\"Groups\" G ON S.\"GroupNumber\" = G.\"GroupNumber\" " +
                "WHERE s.\"FullName\" = \'" + fullName + "\'";
    }

    public static Student selectParticularStudentResult(String fullName){
        Student student = null;
        try {
            //SQLConnection.ensureConnection();
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(selectParticularStudentResultQuery(fullName));
            rs.next();
            student = new StudentResult(rs.getString("FullName"),
                    rs.getInt("GroupNumber"),
                    rs.getString("Faculty"),
                    rs.getInt("ScholarshipType"),
                    //возможно надо более адекватно брать дату
                    rs.getDate("start_date").toString()
            );


            rs.close();

            statement.close();
        }
        catch (SQLException e){
            System.out.println("CacheSQLQueries selectStudent smth wrong");
        }
        return student;
    }
}
