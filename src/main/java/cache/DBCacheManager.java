package cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;

import university_work.Student;

import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;
import static university_work.StudentResult.listStudents;

public class DBCacheManager {


    private static CacheManager cacheManager = null;
    private static final Logger LOGGER = getLogger(DBCacheManager.class);

    public static Cache cacheStart() {
        LOGGER.info("Creating cache manager");
        Cache basicCache = null;

        try {
            cacheManager = CacheManager.getInstance();
            //cacheManager.addCache("basicCache");

            basicCache = cacheManager.getCache("basicCache");
        } catch (CacheException e) {
            //java.util.logging.Logger.getLogger(DBCacheManager.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
            System.out.println("Smth wrong in cacheStart 1");
            return null;
        }

        LOGGER.info("First basicCache fill");
        System.out.println("cacheStart 4");
        List<Student> students = listStudents ();
        for (int i = 0; i < students.size(); ++i)
            DBCacheManager.cachePut(basicCache, students.get(i));


        return basicCache;
    }

    public static void cachePut(Cache basicCache, Student student){
        LOGGER.info("Putting to cache " + student.getFullName());
        String stringStudent = student.toStringTableRow();
        basicCache.put(new Element(student.getFullName(), stringStudent));
    }

    public static String cacheGet(Cache basicCache, String fullName){
        String value = null;
        value = String.valueOf(basicCache.get(fullName).getObjectValue());
        LOGGER.info("Retrieved " + fullName +" '{}'", value);
        return value;
    }

    public static void cacheDel(Cache basicCache, String fullName){

        basicCache.remove(fullName);
        LOGGER.info("Removed " + fullName +" '{}'");
    }

    public static void cacheClose(){
        LOGGER.info("Closing cache manager");
        //надо научить чистить память
        cacheManager.shutdown();
    }
}
