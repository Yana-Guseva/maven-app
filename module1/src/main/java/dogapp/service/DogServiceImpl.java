package dogapp.service;

import dogapp.aspectj.LogMe;
import dogapp.dao.DogDao;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
public class DogServiceImpl {
    private final DogDao dogDao;

    @LogMe
    @Transactional
    public Dog getDog(UUID id) {
        Dog dog = dogDao.get(id);
        dog.getDogDetails().size();
        return dog;
    }

    @LogMe
    @Transactional
    public Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }

    @LogMe
    @Transactional
    public Dog updateDog(Dog dog) {
        return dogDao.update(dog);
    }

    @LogMe
    @Transactional
    public void deleteDog(UUID id) {
        dogDao.delete(id);
    }
}
