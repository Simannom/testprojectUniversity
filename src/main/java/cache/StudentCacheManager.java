package cache;

import net.sf.ehcache.Cache;
import university_work.Student;
import university_work.StudentResult;

import java.util.ArrayList;
import java.util.List;

import static cache.CacheSQLQueries.selectNames;
import static cache.CacheSQLQueries.selectParticularStudentResult;
import static university_work.Student.deletebyName;


public class StudentCacheManager {

    private static Cache basicCache = null;

    public static List <String> select(){
        //System.out.println("select 1");
        //ломается на проверке в веб-приложении
        if (basicCache == null) basicCache = DBCacheManager.cacheStart();
        //System.out.println("select 2");

        List <String> studentNames = selectNames();
        List <String> students = new ArrayList<>();

        for(int i = 0; i < studentNames.size(); ++i){
            if (basicCache.isKeyInCache(studentNames.get(i))){
                //System.out.println("select 3_"+i);
                String student = DBCacheManager.cacheGet(basicCache, studentNames.get(i));
                //System.out.println("select 4_"+i);
                students.add(student);
            }
            else {
                Student student = selectParticularStudentResult(studentNames.get(i));
                //System.out.println("select 5_"+i);
                DBCacheManager.cachePut(basicCache, student);
                //System.out.println("select 6_"+i);
                students.add(student.toStringTableRow());
            }
        }
        return students;
    }


    public static void add(Student student){
        if (!basicCache.isKeyInCache(student.getFullName())){
            student.addStudent();
            Student res = selectParticularStudentResult(student.getFullName());
            DBCacheManager.cachePut(basicCache, res);
        }
    }

    public static void delete(String fullname){
        if (basicCache.isKeyInCache(fullname)){
            DBCacheManager.cacheDel(basicCache, fullname);
        }

        deletebyName(fullname);

    }
}
