package university_work;

import java.util.ArrayList;

public class StudentGroup {

    Integer groupNumber;
    String faculty;

    public StudentGroup(Integer groupNumber, String faculty){
        this.groupNumber = groupNumber;
        this.faculty = faculty;
    }


    public String addSQLQuery (){
        String result = "INSERT INTO public.\"Groups\" (\n" +
                "\"GroupNumber\", \"Faculty\") VALUES (\n" +
                "\'" + this.groupNumber.toString() + "\'," +
                "\'" + this.faculty + "\'" +
                ")\n" +
                " returning \"GroupNumber\";" ;
        return result;
    }

}
