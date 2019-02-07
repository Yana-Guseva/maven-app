package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@AllArgsConstructor
public class JdbcDogDao implements DogDao {
    private final DataSource dataSource;

    @Override
    public Dog get(UUID id) {
        Dog dog = executeQuery(String.format("SELECT * FROM DOG WHERE id = '%s'", id));
        if (dog == null) {
            throw new DogNotFoundException(id);
        }
        return dog;
    }

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID());
        executeUpdate(String.format(Locale.US, "INSERT INTO DOG (id, name, dateOfBirth, height, weight) " +
                        "values ('%s', %s, %s, %f, %f)", dog.getId(), wrapInQuotesOrNull(dog.getName()),
                wrapInQuotesOrNull(dog.getDateOfBirth()), dog.getHeight(), dog.getWeight()));
        return dog;
    }

    @Override
    public Dog update(Dog dog) {
        int updatedRows = executeUpdate(String.format(Locale.US, "UPDATE DOG SET name = %s, dateOfBirth = %s, " +
                        "height = %f, weight = %f WHERE id = '%s'", wrapInQuotesOrNull(dog.getName()),
                wrapInQuotesOrNull(dog.getDateOfBirth()), dog.getHeight(), dog.getWeight(), dog.getId()));
        if (updatedRows < 1) {
            throw new DogNotFoundException(dog.getId());
        }
        return dog;
    }

    @Override
    public void delete(UUID id) {
        int updatedRows = executeUpdate(String.format(Locale.US, "DELETE FROM DOG WHERE id = '%s'", id));
        if (updatedRows < 1) {
            throw new DogNotFoundException(id);
        }
    }

    private Dog executeQuery(String query) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return mapResultSetToDog(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int executeUpdate(String query) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Dog mapResultSetToDog(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Dog dog = new Dog();
            dog.setId(resultSet.getObject("id", UUID.class));
            dog.setName(resultSet.getString("name"));
            dog.setDateOfBirth(resultSet.getObject("dateOfBirth", LocalDate.class));
            dog.setHeight(resultSet.getDouble("height"));
            dog.setWeight(resultSet.getDouble("weight"));
            return dog;
        }
        return null;
    }

    private String wrapInQuotesOrNull(Object value) {
        return value == null ? null : "'" + value.toString() + "'";
    }
}
