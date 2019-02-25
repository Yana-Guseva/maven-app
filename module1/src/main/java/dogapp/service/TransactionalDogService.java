package dogapp.service;

import dogapp.JdbcConnectionHolder;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class TransactionalDogService implements DogService {

    private final DogServiceImpl dogServiceImpl;

    private final JdbcConnectionHolder connectionHolder;

    @Override
    public Dog getDog(UUID id) {
        return dogServiceImpl.getDog(id);
    }

    @Override
    public Dog createDog(Dog dog) {
        try {
            Dog result = dogServiceImpl.createDog(dog);
            connectionHolder.commit();
            return result;
        } catch (Exception e) {
            connectionHolder.rollback();
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }

    @Override
    public Dog updateDog(Dog dog) {
        try {
            Dog result = dogServiceImpl.updateDog(dog);
            connectionHolder.commit();
            return result;
        } catch (Exception e) {
            connectionHolder.rollback();
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }

    @Override
    public void deleteDog(UUID id) {
        try {
            dogServiceImpl.deleteDog(id);
            connectionHolder.commit();
        } catch (Exception e) {
            connectionHolder.rollback();
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }
}
