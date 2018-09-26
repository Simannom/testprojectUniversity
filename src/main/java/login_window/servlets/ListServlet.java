package login_window.servlets;

import cache.DBCacheManager;
import fillDB.SQLConnection;
import university_work.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cache.StudentCacheManager.add;
import static cache.StudentCacheManager.delete;
import static university_work.Student.deletebyName;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //FillWeb.ensureConnection();

        String button = request.getParameter("button");

        String url = "";
        if ("list_button".equals(button)) {
            url = "/index.html";
            DBCacheManager.cacheClose();
            SQLConnection.breakConnection();
            //System.out.println(url);
        } else if (button.startsWith("del_")) {
            String fullname = button.substring(4);
            DBtoWeb.onDelButtonClick(fullname);
            url = "views/list.jsp";
        } else if ("add".equals(button)) {
            DBtoWeb.onAddButtonClick(request);
            url = "views/list.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    private static class DBtoWeb{

        public static void onDelButtonClick(String fullname){
            System.out.println("onDelButtonClick " + fullname);
            //удаляем строку без кэша
            //deletebyName(fullname);
            //удаление с кэшем
            delete(fullname);
        }

        public static void onAddButtonClick(HttpServletRequest request){
            System.out.println("onAddButtonClick");
            Student newStudent = Student.fromRequestParameters(request);
            System.out.println(newStudent.toString());
            //добавляем строку без кэша
            //newStudent.addStudent();

            //добавление через кэш
            add(newStudent);
        }

    }
}
