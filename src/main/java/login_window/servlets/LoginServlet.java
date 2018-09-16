package login_window.servlets;


import fillDB.SQLConnection;
import login_window.StringValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("web/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = User.fromRequestParameters(request);
        user.setAsRequestAttributes(request);
        List violations = user.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }

        String url = determineUrl(violations);
        request.getRequestDispatcher(url).forward(request, response);
    }

    private String determineUrl(List violations) {
        if (!violations.isEmpty()) {
            return "/WEB-INF/views/list.jsp";
        } else {
            return "/WEB-INF/views/login.jsp";
        }
    }

    private static class User {
        private final String name;
        private final String password;

        private User(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public static User fromRequestParameters(HttpServletRequest request){
            return new User(request.getParameter("UserName"),
                    request.getParameter("password"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("UserName", name);
            request.setAttribute("password", password);
        }

        public List validate() {
            List violations = new ArrayList<>();
            if (!StringValidator.validate(name)) {
                violations.add("Имя Пользователя является обязательным полем");
            }
            if (!StringValidator.validate(password)) {
                violations.add("Пароль является обязательным полем");
            }
            /*try {
                SQLConnection.establishConnection(name, password);
            } catch (SQLException ex){
                violations.add("Неправильное Имя Пользователя или Пароль");
            }
            */
            return violations;
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
