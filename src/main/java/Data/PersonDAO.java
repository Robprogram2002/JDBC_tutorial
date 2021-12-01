package Data;

import Domain.Person;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// this class represent the layer of business logic

public class PersonDAO implements IPersonaDAO {
    private static final String LIST = "SELECT * FROM public.\"Persons\" ORDER BY id ASC";
    private static final String INSERT = "INSERT INTO public.\"Persons\" (age, name, address, id) VALUES (?,?,?,?);";
    private static final String UPDATE = "UPDATE public.\"Persons\" SET age=?, name=?, address=? WHERE id=?";
    private static final String DELETE = "DELETE FROM public.\"Persons\" WHERE id=?";

    private Connection transactionalConnection;

    public PersonDAO() {

    }

    public PersonDAO(Connection connection) {
        this.transactionalConnection = connection;
    }

    public List<Person> list() {
        List<Person> persons = new ArrayList<>();
        try {
            Connection connection = this.transactionalConnection != null ? this.transactionalConnection
                    : MakeConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(LIST);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int age = result.getInt("age");
                int id = result.getInt("id");
                String name = result.getString("name");
                String address = result.getString("address");

                Person newPerson = new Person(name, age, address,id);
                persons.add(newPerson);
            }

            MakeConnection.close(statement);
            MakeConnection.close(result);
            if (this.transactionalConnection == null) {
                MakeConnection.close(connection);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return persons;
    }

    public void insert(Person person) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        int registers = 0;
        try {
            connection = this.transactionalConnection != null ? this.transactionalConnection
                    : MakeConnection.getConnection();
            statement = connection.prepareStatement(INSERT);
            statement.setInt(1, person.getAge() );
            statement.setString(2, person.getName());
            statement.setString(3, person.getAddress());
            statement.setInt(4, person.getId());

            registers = statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                assert statement != null;
                MakeConnection.close(statement);
                if (this.transactionalConnection == null) {
                    MakeConnection.close(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Person person) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        int registers = 0;
        try {
            connection = this.transactionalConnection != null ? this.transactionalConnection
                    : MakeConnection.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, person.getAge() );
            statement.setString(2, person.getName());
            statement.setString(3, person.getAddress());
            statement.setInt(4, person.getId());

            registers = statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                assert statement != null;
                MakeConnection.close(statement);
                if (this.transactionalConnection == null) {
                    MakeConnection.close(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Person person) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        int registers = 0;
        try {
            connection = this.transactionalConnection != null ? this.transactionalConnection
                    : MakeConnection.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, person.getId() );
            registers = statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                assert statement != null;
                MakeConnection.close(statement);
                if (this.transactionalConnection == null) {
                    MakeConnection.close(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
