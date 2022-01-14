package repository;

import connection.ConnectionMySQL;
import classe.Kurs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KursRepository implements CrudRepository<Kurs>{
    static final String QUERY_SELECT = "SELECT * FROM KURS";
    static final String QUERY_FIND = "SELECT * FROM KURS WHERE kursID = ?";
    static final String QUERY_INSERT = "INSERT INTO KURS VALUES(?, ?, ?, ?, ?)";
    static final String QUERY_DELETE = "DELETE FROM KURS WHERE kursID = ?";
    static final String QUERY_UPDATE = "UPDATE KURS SET name = ?, lehrerID = ?, maxEnrollment = ?, credits = ? where kursID = ?";
    static final String QUERY_STUDENTS = "SELECT * FROM KURS " +
            "INNER JOIN ENROLLED ON KURS.kursID = ENROLLED.kursID " +
            "WHERE KURS.kursID = ?";
    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private ResultSet resultSet;

    public KursRepository() throws SQLException {
        super();
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    @Override
    public Kurs getOne(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, id);
        this.resultSet = preparedStatement.executeQuery();

        Kurs kurs = null;
        while (resultSet.next()){
            kurs = new Kurs(resultSet.getString("name"), resultSet.getLong("lehrerID"), resultSet.getLong("kursID"), resultSet.getInt("maxEnrollment"), resultSet.getInt("credits"));
        }


        return kurs;
    }

    @Override
    public List<Kurs> getAll() throws SQLException {
        List<Kurs> kursList = new ArrayList<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);

        Kurs kurs = null;
        while (resultSet.next()){
            kurs = new Kurs(resultSet.getString("name"), resultSet.getLong("lehrerID"), resultSet.getLong("kursID"), resultSet.getInt("maxEnrollment"), resultSet.getInt("credits"));
            kursList.add(kurs);
        }

        return kursList;
    }

    @Override
    public Kurs create(Kurs obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setString(1, obj.getName());
        preparedStatement.setLong(2, obj.getLehrer());
        preparedStatement.setLong(3, obj.getKursID());
        preparedStatement.setInt(4, obj.getMaxEnrollment());
        preparedStatement.setInt(5, obj.getCredits());

        preparedStatement.executeUpdate();

        return obj;
    }

    @Override
    public void delete(Kurs obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setLong(1, obj.getKursID());

        preparedStatement.executeUpdate();
    }

    @Override
    public Kurs update(Kurs obj) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        preparedStatement.setString(1, obj.getName());
        preparedStatement.setLong(2, obj.getLehrer());
        preparedStatement.setInt(3, obj.getMaxEnrollment());
        preparedStatement.setInt(4, obj.getCredits());
        preparedStatement.setLong(5, obj.getKursID());

        preparedStatement.executeUpdate();

        return obj;
    }

    public List<Long> enrolledStudents(Kurs obj) throws SQLException{
        List<Long> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_STUDENTS);
        preparedStatement.setLong(1, obj.getKursID());
        this.resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(resultSet.getLong("studentID"));
        }

        return list;
    }
}