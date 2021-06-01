package students.managment.jdbc;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    // define datasource/connection pool for Resources Injection
//     @Resource(name = "jdbc/web_student_tracker")
//     private DataSource dataSource;


    private String URL = "jdbc:mysql://127.0.0.1:3306/web_student_tracker?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
    private String username = "alen";
    private String password = "alen12345678";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set up the print writer
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        // get a connection to the database
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        // load driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("JAVA: Class.forName() error");
            e.printStackTrace();
        }

        try {
            // create a SQL statements
//             connection = dataSource.getConnection();
            connection = DriverManager.getConnection(URL, username, password);

            // create SQL statement
            String sql = "select * from student";
            statement = connection.createStatement();

            // execute SQL query
            resultSet = statement.executeQuery(sql);

            // process the result set
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                out.println(email);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
