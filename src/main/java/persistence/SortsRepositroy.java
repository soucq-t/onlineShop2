package persistence;

import domain.Sorts;

import java.sql.SQLException;
import java.util.List;

public interface SortsRepositroy {
    List<Sorts> findAll() throws SQLException;

    Sorts save(Sorts sorts) throws SQLException;

    void delete(int id) throws SQLException;
    Sorts findByID(Integer id)throws SQLException;
}
