package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDogDao implements Dao<Dog> {
    private static final Map<UUID, Dog> dogs = new ConcurrentHashMap<>();

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID());
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @Override
    public Dog get(UUID id) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException();
        }
        return dogs.get(id);
    }

    @Override
    public Dog update(UUID id, Dog dog) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException();
        }
        dog.setId(id);
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @Override
    public void delete(UUID id) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException();
        }
        dogs.remove(id);
    }
}
