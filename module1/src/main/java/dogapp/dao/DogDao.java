package dogapp.dao;

import dogapp.dto.Dog;

import java.util.UUID;

public interface DogDao {
    Dog get(UUID id);

    Dog create(Dog dog);

    Dog update(Dog dog);

    void delete(UUID id);
}
