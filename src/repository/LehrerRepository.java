package repository;

import connection.ConnectionMySQL;
import classe.Lehrer;
import classe.Person;
import classe.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LehrerRepository implements CrudRepository<Lehrer> {
    static final String QUERY_SELECT = "SELECT * FROM LEHRER INNER JOIN PERSON ON LEHRER.personID = PERSON.personID";
    static final String QUERY_FIND = "SELECT * FROM LEHRER INNER JOIN PERSON ON LEHRER.personID = PERSON.personID WHERE lehrerID = ?";
    static final String QUERY_INSERT = "INSERT INTO LEHRER VALUES(?, ?)";
    static final String QUERY_DELETE = "DELETE FROM LEHRER WHERE lehrerID = ?";
    static final String QUERY_UPDATE = "UPDATE LEHRER SET personID = ? where lehrerID = ?";
    static final String QUERY_COURSES = "SELECT * FROM LEHRER " +
            "INNER JOIN KURS ON KURS.lehrerID = LEHRER.lehrerID " +
            "WHERE LEHRER.lehrerID = ?";
    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private java.sql.ResultSet resultSet;

    public LehrerRepository() throws SQLException {
        super();
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    @Override
    public Lehrer getOne(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, id);
        this.resultSet = preparedStatement.executeQuery();

        Lehrer lehrer = null;
        while (resultSet.next()) {
            lehrer = new Lehrer(new Person(resultSet.getLong("personID"), resultSet.getString("firstName"), resultSet.getString("lastName")), resultSet.getLong("lehrerID"));
        }

        return lehrer;
    }

    @Override
    public List<Lehrer> getAll() throws SQLException {
        List<Lehrer> lehrerList = new ArrayList<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);
        Lehrer lehrer;
        while (resultSet.next()) {
            lehrer = new Lehrer(new Person(resultSet.getLong("personID"), resultSet.getString("firstName"), resultSet.getString("lastName")), resultSet.getLong("lehrerID"));
            lehrerList.add(lehrer);
        }

        return lehrerList;
    }

    @Override
    public Lehrer create(Lehrer obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setLong(1, obj.getPersonID());
        preparedStatement.setLong(2, obj.getLehrerID());

        preparedStatement.executeUpdate();

        return obj;
    }

    @Override
    public void delete(Lehrer obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setLong(1, obj.getLehrerID());

        preparedStatement.executeUpdate();
    }

    @Override
    public Lehrer update(Lehrer obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        preparedStatement.setLong(1, obj.getPersonID());
        preparedStatement.setLong(2, obj.getLehrerID());

        preparedStatement.executeUpdate();

        return obj;
    }

    public List<Long> enrolledCourses(Lehrer obj) throws SQLException {
        List<Long> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_COURSES);
        preparedStatement.setLong(1, obj.getLehrerID());
        this.resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            list.add(resultSet.getLong("kursID"));
        }

        return list;
    }
}