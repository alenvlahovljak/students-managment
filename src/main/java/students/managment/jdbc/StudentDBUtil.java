package students.managment.jdbc;

import students.managment.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDBUtil {
    private String URL = "jdbc:mysql://127.0.0.1:3306/web_student_tracker?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
    private String username = "alen";
    private String password = "alen12345678";

    private DataSource dataSource;

    public StudentDBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

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
            // get a connection
            connection = DriverManager.getConnection(URL, username, password);

            // create sql statement
            String sql = "select * from student order by last_name";
            statement = connection.createStatement();

            // execute query
            resultSet = statement.executeQuery(sql);

            // process result set
            while (resultSet.next()) {
                // retrieve data from row
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");

                // create new student object
                Student tempStudent = new Student(id, firstName, lastName, email);

                // add it to the list of students
                students.add(tempStudent);
            }

            return students;
        } finally {
            // close JDBC object
            closeConnection(connection, statement, resultSet);
        }

    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                // doesn't really close it,
                // just puts back in connection pool
                connection.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addStudent(Student student) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        // load driver
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (Exception e) {
//            System.out.println("JAVA: Class.forName() error");
//            e.printStackTrace();
//        }

        try {
            // get DB connection
            connection = DriverManager.getConnection(URL, username, password);

            // create sql insert
            String sql = "insert into student " + "(first_name, last_name, email) " + "value (?, ?, ?)";
            statement = connection.prepareStatement(sql);

            // set the param values for the student
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getEmail());

            // execute sql insert
            statement.executeUpdate();

        } finally {
            // clean up JDBC objects
            closeConnection(connection, statement, null);
        }
    }

    public Student getStudent(String theStudentId) throws Exception {
        Student student = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int studentId;

        try {
            // convert student id to int
            studentId = Integer.parseInt(theStudentId);

            // get connection to db
            connection = DriverManager.getConnection(URL, username, password);

            // create sql to get selected student
            String sql = "select * from student where id=?";

            // create prepared statement
            statement = connection.prepareStatement(sql);

            // set params
            statement.setInt(1, studentId);

            // execute statement
            resultSet = statement.executeQuery();

            // retrieve data from result set row
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");

                // use studentId during construction
                student = new Student(studentId, firstName, lastName, email);
            } else {
                throw new Exception("Could not find student id: " + studentId);
            }

            return student;

        } finally {
            // clean up JDBC object
            closeConnection(connection, statement, resultSet);
        }
    }

    public void updateStudent(Student student) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // get db connection
            connection = DriverManager.getConnection(URL, username, password);

            // create SQL update statement
            String sql = "update student " + "set first_name=?, last_name=?, email=? " + "where id=?";

            // prepare statement
            statement = connection.prepareStatement(sql);

            // set params
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getEmail());
            statement.setInt(4, student.getId());

            // execute SQL statement
            statement.execute();
        } finally {
            // clear up JDBC object
            closeConnection(connection, statement, null);
        }
    }

    public void deleteStudent(String theStudentId) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // convert student id to int
            int studentId = Integer.parseInt(theStudentId);

            // get connection to database
            connection = DriverManager.getConnection(URL, username, password);

            // create sql to delete student
            String sql = "delete from student where id=?";

            // prepare statement
            statement = connection.prepareStatement(sql);

            // set params
            statement.setInt(1, studentId);

            // execute sql statement
            statement.execute();

        } finally {
            // clean up JDBC code
            closeConnection(connection, statement, null);
        }
    }
}
