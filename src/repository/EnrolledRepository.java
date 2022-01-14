package repository;

import connection.ConnectionMySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class EnrolledRepository {
    static final String QUERY_SELECT = "SELECT * FROM ENROLLED";
    static final String QUERY_FIND = "SELECT * FROM ENROLLED WHERE kursID = ? AND studentID = ?";
    static final String QUERY_INSERT = "INSERT INTO ENROLLED VALUES(?, ?)";
    static final String QUERY_DELETE = "DELETE FROM ENROLLED WHERE kursID = ? AND studentID = ?";
    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private java.sql.ResultSet resultSet;

    public EnrolledRepository() throws SQLException {
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    public boolean getOne(Long obj, Long obj2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, (Long) obj);
        preparedStatement.setLong(2, (Long) obj2);
        this.resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (resultSet.next()) {
            count++;
        }

        if (count != 0)
            return true;
        else
            return false;
    }

    public HashMap<Long, Long> getAll() throws SQLException {
        HashMap<Long, Long> map = new HashMap<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);

        while (resultSet.next()) {
            map.put(resultSet.getLong("kursID"), resultSet.getLong("studentID"));
        }

        return map;
    }

    public void create(Long obj, Long obj2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setLong(1, (Long) obj);
        preparedStatement.setLong(2, (Long) obj2);

        preparedStatement.executeUpdate();
    }

    public void delete(Long obj, Long obj2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setLong(1, (Long) obj);
        preparedStatement.setLong(2, (Long) obj2);

        preparedStatement.executeUpdate();
    }

}