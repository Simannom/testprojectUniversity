package university_work;

import fillDB.FillDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fillDB.SQLConnection.getConnection;

public class StudentResult extends Student {
    private String faculty;

    public StudentResult(String fullName, Integer groupNumber, String faculty, Integer scholarshipType, Calendar startDate) {
        super(fullName, groupNumber, scholarshipType, startDate);
        this.faculty = faculty;
    }

    public StudentResult(String fullName, Integer groupNumber, String faculty, Integer scholarshipType, String dateString) {
        super(fullName, groupNumber, scholarshipType, dateString);
        this.faculty = faculty;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String toString(){
        return this.fullName + "\t" + this.groupNumber + "\t" + this.faculty + "\t"
                + this.scholarshipType + "\t" + toString(this.startDate) + "\n";
    }

    //with delete button
    public String toStringTableRow(){
        return "<tr>"
                +"<th>"+ this.fullName + "</th>"
                +"<th>" + this.groupNumber + "</th>"
                +"<th>" + this.faculty + "</th>"
                + "<th>" + this.scholarshipType + "</th>"
                + "<th>" + toString(this.startDate)+ "</th>"
                + "<th><button type=\"submit\" name=\"button\" value=\"del_" + this.fullName + "\" />Delete</button></th>"
                + "</tr>"
                + "\n";

    }

    public static String listSQLQuery(){
        String result = "SELECT * " +
                "FROM public.\"Students\" S LEFT JOIN public.\"Groups\" G ON S.\"GroupNumber\" = G.\"GroupNumber\" " +
                "ORDER BY \"FullName\"";
        return result;
    }


    public static List<Student> listStudents(Statement stmt) throws SQLException {
        List<Student> result = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(listSQLQuery());
        Student student = null;
        while (rs.next()) {
            student = new StudentResult (rs.getString("FullName"),
                    rs.getInt("GroupNumber"),
                    rs.getString("Faculty"),
                    rs.getInt("ScholarshipType"),
                    rs.getDate("start_date").toString()
            );
            result.add(student);
        }
        rs.close();
        return result;
    }

    public static List<Student> listStudents () {
        List<Student> students = null;
        try{
            Connection connection = getConnection();

            try {
                Statement statement;
                statement = connection.createStatement();
                students = StudentResult.listStudents(statement);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);

        }

        return students;
    }
    public static String stringListStudents() {
        String result = "";
        List <Student> students = listStudents();
        for(int i =0; i < students.size(); ++i)
            result += students.get(i).toString();
        return result;
    }

    public static String stringListStudentsTable (List <String> students){

        String result = "";
        for(int i =0; i < students.size(); ++i)
            result += students.get(i);

        result += "<tr>"
                + "<th>" + "<input type=\"text\" name=\"fullName\"><br />" + "</th>"
                + "<th>" + "<input type=\"int\" name=\"GroupNumber\"><br />" + "</th>"
                + "<th>" + "</th>"
                + "<th>" + "<input type=\"int\" name=\"ScholarshipType\"><br />" + "</th>"
                + "<th>" + "<input type=\"date\" name=\"start_date\"><br />" + "</th>"
                +"<th><button type=\"submit\" name=\"button\" value=\"add\" />Add</button></th>"
                + "</tr>"
                + "\n";
        return result;
    }

    public static String stringListStudentsWeb (){
        List <String> students = new ArrayList<>();
        List <Student> listStudents = listStudents();

        for (int i = 0; i < listStudents.size(); ++i){
            students.add(listStudents.get(i).toStringTableRow());
        }

        return stringListStudentsTable(students);
    }






}
