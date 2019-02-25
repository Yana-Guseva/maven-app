package dogapp.service;

import dogapp.dto.Dog;

import java.util.UUID;

public interface DogService {
    Dog getDog(UUID id);

    Dog createDog(Dog dog);

    Dog updateDog(Dog dog);

    void deleteDog(UUID id);
}
