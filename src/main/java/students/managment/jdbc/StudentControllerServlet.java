package students.managment.jdbc;

import students.managment.Student;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentControllerServlet", value = "/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    private StudentDBUtil studentDBUtil;

    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        // create our student db util and pass in the connection pool
        try {
            studentDBUtil = new StudentDBUtil(dataSource);
        } catch (Exception exception) {
            throw new ServletException(exception);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // list the students
        try {
            // read command parameter
            String command = request.getParameter("command");

            // if command is missing
            if (command == null) {
                command = "LIST";
            }

            // route to the appropriate method
            switch (command) {
                case "LIST":
                    listStudents(request, response);
                    break;
                case "LOAD":
                    loadStudent(request, response);
                    break;
                case "UPDATE":
                    updateStudent(request, response);
                    break;
                case "DELETE":
                    deleteStudent(request, response);
                default:
                    listStudents(request, response);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // read student id from the form data
        String studentId = request.getParameter("studentId");

        // delete student from database
        studentDBUtil.deleteStudent(studentId);

        // send them back to 'list students' page
        listStudents(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // read student info from the form data
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // create a new student object
        Student student = new Student(id, firstName, lastName, email);

        // preform update on database
        studentDBUtil.updateStudent(student);

        // send them back to the 'list students'
        listStudents(request, response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // read the student id from data
        String studentId = request.getParameter("studentId");

        // get student from db
        Student student = studentDBUtil.getStudent(studentId);

        // place student in the request attribute
        request.setAttribute("student", student);

        // sent to jsp page
        RequestDispatcher rd = request.getRequestDispatcher("update-student.jsp");
        rd.forward(request, response);

    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // read student info from the data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // create a new student object
        Student student = new Student(firstName, lastName, email);

        // add the student to the database
        studentDBUtil.addStudent(student);

        // send back to main page (the student list)
        // SEND AS REDIRECT to avoid multiple-browser reload issue
        response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // get students from db
        List<Student> students = studentDBUtil.getStudents();

        // add students to the req
        request.setAttribute("studentList", students);

        // send to JSP
        RequestDispatcher rd = request.getRequestDispatcher("list-students.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");

            // route to the appropriate method
            if ("ADD".equals(theCommand)) {
                addStudent(request, response);
            } else {
                listStudents(request, response);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }
}
