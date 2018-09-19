package login_window.servlets;


import fillDB.SQLConnection;
import login_window.StringValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


//@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = User.fromRequestParameters(request);
        //System.out.println(user.toString());

        user.setAsRequestAttributes(request);
        String url = user.determineUrl();
        //System.out.println(url);
        request.getRequestDispatcher(url).forward(request, response);
    }



    private static class User {
        private final String name;
        private final String password;

        private User(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public static User fromRequestParameters(HttpServletRequest request){
            return new User(request.getParameter("name"),
                    request.getParameter("pass"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("name", name);
            request.setAttribute("pass", password);
        }

        private String determineUrl() {
            if (this.validate()) {
                return "/views/list.jsp";
            } else {
                return "/views/login.jsp";
            }
        }

        public boolean validate() {
            if (StringValidator.validate(name) && StringValidator.validate(password)) {
                try {
                    //для работы надо включить библиотеку с драйвером в WEB-INF
                    Connection con = SQLConnection.establishConnection(name, password);
                } catch (SQLException e) {
                    System.out.println("No Connection to DB");
                    return false;
                } catch (ClassNotFoundException e) {
                    System.out.println("No Driver");
                    return false;
                }
                return true;
            }
                return false;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

    }
}
