package dogapp.service;

import dogapp.JdbcConnectionHolder;
import dogapp.dao.DogDao;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
public class DogService {
    private final DogDao dogDao;
    private final JdbcConnectionHolder connectionHolder;

    public Dog getDog(UUID id) {
        return dogDao.get(id);
    }

    public Dog createDog(Dog dog) {
        try {
            Connection connection = connectionHolder.getConnection();
            connection.setAutoCommit(false);
            try {
                Dog createdDog = dogDao.create(dog);
                connection.commit();
                return createdDog;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }

    public Dog updateDog(Dog dog) {
        try {
            Connection connection = connectionHolder.getConnection();
            connection.setAutoCommit(false);
            try {
                Dog updatedDog = dogDao.update(dog);
                connection.commit();
                return updatedDog;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }

    public void deleteDog(UUID id) {
        try {
            Connection connection = connectionHolder.getConnection();
            connection.setAutoCommit(false);
            try {
                dogDao.delete(id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }
}
