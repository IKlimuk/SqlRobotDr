package repository;

import domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentSqRepository implements StudentRepository{
    private static final String DB_URL = "jdbc:mysql://robot-do-user-1968994-0.b.db.ondigitalocean.com:25060/klimuk";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_I6wlDKjGszZn1wvLr9t";

    private static final String SELECT_FROM_STUDENTS = "SELECT * FROM studentSql";
    private static final String INS_STUDENT = "INSERT INTO studentSql (name, age) VALUES (?, ?)";

    @Override
    public void save(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INS_STUDENT);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {

            }
            e.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_FROM_STUDENTS)) {
            while (resultSet.next()) {
                Student student = Student.builder().
                        id(resultSet.getInt("id")).
                        name(resultSet.getString("name")).
                        age(resultSet.getInt("age")).
                        groupId(resultSet.getInt("group_id")).
                        build();
                result.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
