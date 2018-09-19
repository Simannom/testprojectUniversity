package login_window.servlets;

import fillDB.SQLConnection;
import university_work.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FillWeb.ensureConnection();


        SQLConnection.breakConnection();
    }

    private static class FillWeb{

        public static void ensureConnection() {
            Connection connection = SQLConnection.getConnection();
            if (connection == null) {
                try {
                    connection = SQLConnection.establishConnection(
                            SQLConnection.default_user,SQLConnection.default_password
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        public static void onButtonClick(){
            /*
            function submitForm(x){
            for (int i = 0; i < Student.rows(); ++i)
            {
                if (x.id === ('b3_' + i)) {
                    document.getElementById('hid1').value = 'button3action';
                    out.println("b3_ pressed");
                }
            }
            //alert(document.getElementById('hid1').value);
            document.forms[0].submit();
            } */
        }

    }
}
