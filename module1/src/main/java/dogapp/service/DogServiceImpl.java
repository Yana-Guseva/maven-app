package dogapp.service;

import dogapp.aspect.Transactional;
import dogapp.dao.DogDao;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class DogServiceImpl {
    private DogDao dogDao;

    @Transactional
    public Dog getDog(UUID id) {
        return dogDao.get(id);
    }

    @Transactional
    public Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }

    @Transactional
    public Dog updateDog(Dog dog) {
        return dogDao.update(dog);
    }

    @Transactional
    public void deleteDog(UUID id) {
        dogDao.delete(id);
    }
}
