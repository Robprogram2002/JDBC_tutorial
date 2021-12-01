package Data;

import Domain.Person;

import java.sql.SQLException;
import java.util.List;

// we create an interface for the data access layer so the actual implementation for the connection with the DB can be
// replaced with little code changes in other parts of the application.

public interface IPersonaDAO {

    public void insert(Person person) throws SQLException;

    public List<Person> list();

    public void update(Person person) throws  SQLException;

    public void delete(Person person)  throws SQLException;
}
