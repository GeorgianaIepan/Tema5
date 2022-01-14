package repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {
    T create(T obj) throws SQLException;

    void delete(T obj) throws SQLException;

    T update(T obj) throws SQLException;

    T getOne(long id) throws SQLException;

    List<T> getAll() throws SQLException;


}
