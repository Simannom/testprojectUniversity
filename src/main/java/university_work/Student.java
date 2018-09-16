package university_work;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class Student {

    private String fullName;
    private Integer groupNumber;
    private Integer scholarshipType;
    private Calendar startDate;

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
    public static String listStudents(Statement stmt) throws SQLException {
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

}
