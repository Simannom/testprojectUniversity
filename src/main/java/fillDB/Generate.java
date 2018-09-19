package fillDB;

import university_work.Student;
import university_work.StudentGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class Generate {

    public static ArrayList <StudentGroup> generateDataGroups(){
        ArrayList<StudentGroup> result = new ArrayList<StudentGroup>();
        for (int i = 0; i < 10; ++i){
            result.add(new StudentGroup(100 + i, "Physics"));
            result.add(new StudentGroup(200 + i, "Mathematics"));
            result.add(new StudentGroup(300 + i, "Economics"));
            result.add(new StudentGroup(400 + i, "Linguistics"));
            result.add(new StudentGroup(500 + i, "Philosophy"));
        }
        return result;
    }

    public static ArrayList<Student> generateDataStudents(int numberOfStudents){
        ArrayList <Student> students = new ArrayList<Student>(numberOfStudents);
        String[] fullNames = {"Martha Jones", "Steven Taylor","Donna Noble","Rory Williams",
                "Martin Eden","Mickey Smith","River Song","Ian Chesterton", "Rose Tyler"
        };
        int randomizer;
        Random rnd = new Random();
        for (int i = 0; i < numberOfStudents; ++i){
            randomizer = rnd.nextInt(8);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.set(2000 + randomizer*2, Calendar.SEPTEMBER, 1);
            students.add (new Student(fullNames[i], (randomizer%5+1)*100 + randomizer, "",
                    randomizer%3 ,calendar));
        }
        return students;
    }

}
