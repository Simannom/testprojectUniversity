package university_work;

import fillDB.FillDB;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fillDB.SQLConnection.getConnection;

public class Student {

    private String fullName;
    private Integer groupNumber;
    private String faculty;
    private Integer scholarshipType;
    private Calendar startDate;

    public Student(String fullName, Integer groupNumber, String faculty, Integer scholarshipType, Calendar startDate){
        this.fullName = fullName;
        this.groupNumber = groupNumber;
        this.faculty = faculty;
        this.scholarshipType = scholarshipType % 3;
        this.startDate = startDate;
    }

    public Student(String fullName, Integer groupNumber, String faculty, Integer scholarshipType, String dateString){
        this.fullName = fullName;
        this.groupNumber = groupNumber;
        this.faculty = faculty;
        this.scholarshipType = scholarshipType % 3;
        Calendar c = Calendar.getInstance();
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        try {
            c.setTime(format.parse(dateString));
        } catch (ParseException e) {
            c.set(1000, Calendar.SEPTEMBER, 1);
        }

        this.startDate = c;
    }

    public static String toString(Calendar date) {
        DateFormat format1 = DateFormat.getDateInstance(DateFormat.SHORT);
        String out = format1.format(date.getTime());
        return out;
    }

    public String addSQLQuery () {
        String result = "INSERT INTO public.\"Students\" (\n" +
                "\"FullName\",\"GroupNumber\", \"ScholarshipType\", start_date) VALUES (\n" +
                "\'" + this.fullName + "\'," + this.groupNumber.toString() + ","+
                this.scholarshipType.toString() + "," + "\'"+ toString(this.startDate) + "\'" + ")\n" +
                " returning \"GroupNumber\";";
        return result;
    }

    public void addStudent(Statement stmt) throws SQLException {
        stmt.executeUpdate(this.addSQLQuery());
    }

    public String deleteSQLQuery(){
        String result = "DELETE FROM public.\"Students\" " +
                        "WHERE \"FullName\" IN" +
                        "(\'"+ this.fullName +"\');";
        return result;
    }

    public void deleteStudent(Statement stmt) throws SQLException {
        stmt.executeUpdate(this.deleteSQLQuery());
    }

    public static String listSQLQuery(){
        String result = "SELECT * FROM public.\"Students\" S LEFT JOIN public.\"Groups\" G ON S.\"GroupNumber\" = G.\"GroupNumber\" ";
        return result;
    }
    public static String stringListStudents(Statement stmt) throws SQLException {
        String result = "";
        ResultSet rs = stmt.executeQuery(listSQLQuery());
        while (rs.next()) {
            result += "#" + rs.getRow()
                    + "\t" + rs.getString("FullName")
                    + "\t" + rs.getInt("GroupNumber")
                    + "\t" + rs.getString("Faculty")
                    + "\t" + rs.getInt("ScholarshipType")
                    + "\t" + rs.getDate("start_date")
                    + "\n"
            ;
        }
        rs.close();
        return result;
    }

    public static String stringListStudentsWebST(Statement stmt) throws SQLException {
        String result = "";
        ResultSet rs = stmt.executeQuery(listSQLQuery());
        int i = 0;
        while (rs.next()) {
            /*
            String result = "<th>" + this.fullName + "</th>"
                +  "<th>" + this.groupNumber.toString() + "</th>"
                +  "<th>" + this.faculty + "</th>"
                +  "<th>" + this.scholarshipType.toString() + "</th>"
                +  "<th>" + this.startDate.toString() + "</th>" ;
            * */
            result += "<tr>"
                    + "<th>" + rs.getString("FullName") + "</th>"
                    + "<th>" + rs.getInt("GroupNumber") + "</th>"
                    + "<th>" + rs.getString("Faculty") + "</th>"
                    + "<th>" + rs.getInt("ScholarshipType") + "</th>"
                    + "<th>" + rs.getDate("start_date") + "</th>"
                    +
                    "<!--" +
                    "<th><input type=\"button\" id=b1_" + i + "  value=\"Submit\" name=\"button\" onClick='submitForm(this)'/></th>" +
                    "<th><input type=\"button\" id=b2_" + i + "  value=\"Update\" name=\"button\" onClick='submitForm(this)'/></th>" +
                    "-->" +
                    "<th><input type=\"button\" id=b3_" + i + " value=\"Delete\" name=\"button\" onClick='submitForm(this)'/></th>" +
                    "</tr>"
                    + "\n";
            ++i;
        }
        rs.close();
        return result;
    }

    public static String stringListStudentsWeb (){

        try {
            Connection connection = getConnection();
            Statement statement;
            statement = connection.createStatement();
            String result = stringListStudentsWebST(statement);
            statement.close();
            return result;
        }
        catch (SQLException e){}
        return "";
    }

    public static List<Student> listStudents(Statement stmt) throws SQLException {
        List<Student> result = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(listSQLQuery());
        Student student = null;
        while (rs.next()) {
            student = new Student (rs.getString("FullName"),
                    rs.getInt("GroupNumber"),
                    rs.getString("Faculty"),
                    rs.getInt("ScholarshipType"),
                    //возможно надо более адекваьно брать дату
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
                students = Student.listStudents(statement);
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

    public static String rowsSQLQuery(){
        return "SELECT COUNT(*) FROM public.\"Students\"";
    }

    public static Integer rowsST(Statement statement) throws SQLException {
        return statement.executeUpdate(rowsSQLQuery());
    }

    public static Integer rows(){
        Integer row_num = 0;
        try{
            Connection connection = getConnection();

            try {
                Statement statement;
                statement = connection.createStatement();
                row_num = Student.rowsST(statement);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            row_num = 0;
            Logger.getLogger(FillDB.class.getName()).log(Level.SEVERE, null, ex);

        }
        return row_num;
    }

}
