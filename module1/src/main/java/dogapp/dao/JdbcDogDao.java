package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class JdbcDogDao implements DogDao {
    private static final String SELECT_QUERY = "SELECT * FROM DOG WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO DOG (id, name, dateOfBirth, height, weight) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE DOG SET name = ?, dateOfBirth = ?, height = ?, weight = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM DOG WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public Dog get(UUID id) {
        Dog dog = executeQuery(SELECT_QUERY, ps -> ps.setObject(1, id));
        if (dog == null) {
            throw new DogNotFoundException(id);
        }
        return dog;
    }

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID());
        executeUpdate(INSERT_QUERY, ps -> {
            ps.setObject(1, dog.getId());
            ps.setString(2, dog.getName());
            ps.setObject(3, dog.getDateOfBirth());
            ps.setDouble(4, dog.getHeight());
            ps.setDouble(5, dog.getWeight());
        });
        return dog;
    }

    @Override
    public Dog update(Dog dog) {
        int updatedRows = executeUpdate(UPDATE_QUERY, ps -> {
            ps.setString(1, dog.getName());
            ps.setObject(2, dog.getDateOfBirth());
            ps.setDouble(3, dog.getHeight());
            ps.setDouble(4, dog.getWeight());
            ps.setObject(5, dog.getId());
        });
        if (updatedRows < 1) {
            throw new DogNotFoundException(dog.getId());
        }
        return dog;
    }

    @Override
    public void delete(UUID id) {
        int updatedRows = executeUpdate(DELETE_QUERY, ps -> ps.setObject(1, id));
        if (updatedRows < 1) {
            throw new DogNotFoundException(id);
        }
    }

    private Dog executeQuery(String query, AcceptParameters function) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            function.accept(ps);
            try (ResultSet resultSet = ps.executeQuery()) {
                return mapResultSetToDog(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int executeUpdate(String query, AcceptParameters function) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            function.accept(ps);
            return ps.executeUpdate();
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

    @FunctionalInterface
    public interface AcceptParameters {
        void accept(PreparedStatement ps) throws SQLException;
    }
}
