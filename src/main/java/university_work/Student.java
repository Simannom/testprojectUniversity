package university_work;

import fillDB.FillDB;
import fillDB.SQLConnection;
import login_window.servlets.LoginServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fillDB.SQLConnection.getConnection;

public class Student //implements Serializable
{

    protected String fullName;
    protected Integer groupNumber;
    protected Integer scholarshipType;
    protected Calendar startDate;

    public Student(String fullName, Integer groupNumber, Integer scholarshipType, Calendar startDate){
        this.fullName = fullName;
        this.groupNumber = groupNumber;
        this.scholarshipType = scholarshipType % 3;
        this.startDate = startDate;
    }

    public Student(String fullName, Integer groupNumber, Integer scholarshipType, String dateString){
        this.fullName = fullName;
        this.groupNumber = groupNumber;
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

    public String getFullName(){
        return this.fullName;
    }

    public static Student fromRequestParameters(HttpServletRequest request){
        String fullName = request.getParameter("fullName");
        Integer groupNumber = Integer.parseInt(request.getParameter("GroupNumber"));
        if (groupNumber < 100 || groupNumber > 999) groupNumber = 0;
        Integer scholarshipType = Integer.parseInt(request.getParameter("ScholarshipType"));
        if (scholarshipType < 0 || scholarshipType > 2) scholarshipType = 0;
        return new Student(fullName,groupNumber ,scholarshipType,
                request.getParameter("start_date")
                );
    }

    public String toString(){
        return this.fullName + "\t " + this.groupNumber
                + "\t " + this.scholarshipType
                + "\t " + toString(this.startDate);
    }

    //with delete button
    public String toStringTableRow(){
        return "<tr>"
                +"<th>"+ this.fullName + "</th>"
                +"<th>" + this.groupNumber + "</th>"
                + "<th>" + this.scholarshipType + "</th>"
                + "<th>" + toString(this.startDate)+ "</th>"
                + "<th><button type=\"submit\" name=\"button\" value=\"del_" + this.fullName + "\" />Delete</button></th>"
                + "</tr>"
                + "\n";

    }

/*
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeBytes(this.toString());

    }
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{

    }
    private void readObjectNoData()
            throws ObjectStreamException{

    }
    */


    public static String toString(Calendar date) {
        DateFormat format1 = DateFormat.getDateInstance(DateFormat.SHORT);
        String out = format1.format(date.getTime());
        return out;
    }

    public String addSQLQuery () {
        String result = "INSERT INTO public.\"Students\" (\n" +
                "\"FullName\",\"GroupNumber\", \"ScholarshipType\", start_date) VALUES (\n" +
                "\'" + this.fullName + "\'," + this.groupNumber.toString() + ","+
                this.scholarshipType.toString() + "," + "\'"+ toString(this.startDate) + "\'" + ")\n";
        return result;
    }

    public void addStudent(Statement stmt) throws SQLException {
        stmt.executeUpdate(this.addSQLQuery());
    }

    public void addStudent() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            this.addStudent(statement);
        }
        catch (SQLException e){
            System.out.println("addStudent smth wrong");
        }
    }

    public static String deletebyNameSQLQuery(String fullName){
        String result = "DELETE FROM public.\"Students\" " +
                        "WHERE \"FullName\" IN" +
                        "(\'"+ fullName +"\');";
        return result;
    }

    public static void deleteStudentbyName(Statement stmt, String fullName) throws SQLException {
        stmt.executeUpdate(deletebyNameSQLQuery(fullName));
    }

    public static String listSQLQuery(){
        String result = "SELECT * FROM public.\"Students\" ORDER BY \"FullName\"";
        return result;
    }

    public static List<Student> listStudents(Statement stmt) throws SQLException {
        List<Student> result = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(listSQLQuery());
        Student student = null;
        while (rs.next()) {
            student = new Student (rs.getString("FullName"),
                    rs.getInt("GroupNumber"),
                    //rs.getString("Faculty"),
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
        return "SELECT COUNT(*) as rowcount FROM public.\"Students\"";
    }

    public static Integer rowsST(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery(rowsSQLQuery());
        rs.next();
        Integer res = rs.getInt("rowcount");
        rs.close();
        return res;
    }

    public static Integer rows(){
        Integer row_num = 0;
        try {
            //SQLConnection.ensureConnection();
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            //problem
            row_num = rowsST(statement);
            statement.close();
        }
        catch (SQLException e){
            System.out.println("Student rows smth wrong");
        }
        return row_num;
    }


    public static void deletebyName(String fullName){
        try {
            //SQLConnection.ensureConnection();
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            deleteStudentbyName(statement, fullName);
            statement.close();
        }
        catch (SQLException e){
            System.out.println("Student deletebyName smth wrong");
        }
    }

}
