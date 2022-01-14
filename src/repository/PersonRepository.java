package repository;
import connection.ConnectionMySQL;
import classe.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository implements CrudRepository<Person>{
    static final String QUERY_SELECT = "SELECT * FROM PERSON";
    static final String QUERY_FIND = "SELECT * FROM PERSON WHERE personID = ?";
    static final String QUERY_INSERT = "INSERT INTO PERSON VALUES(?, ?, ?)";
    static final String QUERY_DELETE = "DELETE FROM PERSON WHERE personID = ?";
    static final String QUERY_UPDATE = "UPDATE PERSON SET firstName = ?, lastName = ? where personID = ?";
    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private java.sql.ResultSet resultSet;

    public PersonRepository() throws SQLException {
        super();
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    @Override
    public Person getOne(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, id);
        this.resultSet = preparedStatement.executeQuery();

        Person person = null;
        while (resultSet.next()){
            person = new Person((long) id, resultSet.getString("firstName"), resultSet.getString("lastName"));
        }

        return person;
    }

    @Override
    public List<Person> getAll() throws SQLException {
        List<Person> personList = new ArrayList<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);

        Person person;
        while (resultSet.next()){
            person = new Person(resultSet.getLong("personID"), resultSet.getString("firstName"), resultSet.getString("lastName"));
            personList.add(person);
        }
        return personList;
    }

    @Override
    public Person create(Person obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setInt(1, Math.toIntExact(obj.getPersonID()));
        preparedStatement.setString(2, obj.getFirstName());
        preparedStatement.setString(3, obj.getLastName());

        preparedStatement.executeUpdate();

        return obj;
    }

    @Override
    public void delete(Person obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setInt(1, Math.toIntExact(obj.getPersonID()));

        preparedStatement.executeUpdate();
    }

    @Override
    public Person update(Person obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        preparedStatement.setString(1, obj.getFirstName());
        preparedStatement.setString(2, obj.getLastName());
        preparedStatement.setInt(3, Math.toIntExact(obj.getPersonID()));

        preparedStatement.executeUpdate();

        return obj;
    }
}