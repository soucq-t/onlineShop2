package persistence;

import domain.Sorts;

import java.sql.SQLException;
import java.util.List;

public interface SortsRepositroy {
    List<Sorts> findAll() throws SQLException;
}
