package dogapp.service;

import dogapp.aspect.Transactional;
import dogapp.aspectj.LogMe;
import dogapp.dao.DogDao;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DogServiceImpl {
    private final DogDao dogDao;

    @LogMe
    public Dog getDog(UUID id) {
        return dogDao.get(id);
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
