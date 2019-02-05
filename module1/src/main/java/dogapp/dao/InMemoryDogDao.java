package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDogDao implements DogDao {
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
            throw new DogNotFoundException(id);
        }
        return dogs.get(id);
    }

    @Override
    public Dog update(Dog dog) {
        if (!dogs.containsKey(dog.getId())) {
            throw new DogNotFoundException(dog.getId());
        }
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @Override
    public void delete(UUID id) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException(id);
        }
        dogs.remove(id);
    }
}
