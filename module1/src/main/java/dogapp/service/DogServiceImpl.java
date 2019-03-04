package dogapp.service;

import dogapp.dao.DogDao;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class DogServiceImpl {
    private DogDao dogDao;

    public Dog getDog(UUID id) {
        return dogDao.get(id);
    }

    public Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }

    public Dog updateDog(Dog dog) {
        return dogDao.update(dog);
    }

    public void deleteDog(UUID id) {
        dogDao.delete(id);
    }
}
