import Data.IPersonaDAO;
import Data.MakeConnection;
import Data.PersonDAO;
import Domain.Person;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.List;


// this class represent the presentation or view layer

// the objective of the software design is minimize the cost of development

// Cohesion: How much a software component is responsible for doing only one thing for what it was created.
// All the other complementary tasks are delegated to other components

// Coupling: It measures the relation between the changes made in one component and the necessity of also make changes
// in the related components

public class TestConnection {
    private static final IPersonaDAO personDao = new PersonDAO();

    private static void printList() {
        List<Person> persons = personDao.list();
        for (Person person :
                persons) {
            System.out.println(person.toString());
        }
    }

    public static void main(String[] args) {
        // test list method
        printList();

        //test insert method
        //System.out.println("\n\n");
        //Person newPerson = new Person("Peter Acosta", 45, "Santa Barbara, California", 7);
        //personDao.insert(newPerson);
        //printList();

        //test update Method
        //System.out.println("\n\n");
        //newPerson.setName("David Gerard");
        //newPerson.setAddress("Mexico City");
        //newPerson.setAge(77);
        //personDao.update(newPerson);
        //printList();

        // test delete method
        //System.out.println("\n\n");
        //personDao.delete(new Person(1));
        //printList();

        // Now we are going to do a transactional example
        Connection connection = null;
        try {
            connection = MakeConnection.getPoolConnection();
            connection.setAutoCommit(false);
            IPersonaDAO transDao = new PersonDAO(connection);
            // let's do 3 operations in one transaction

            //test update Method
            Person updatePerson = new Person("David Gerard", 58,"Nuevo Leon, Monterrey, Mexico", 7);
            transDao.update(updatePerson);

            // test delete method
            transDao.delete(new Person(13));

            //test insert method
            Person newPerson = new Person("Amelie Paulette", 28, "In my heart", 14);
            transDao.insert(newPerson);

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("We enter to the rollback step of the transaction");
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            System.out.println("\n\n");
            printList();
        }

    }
}
