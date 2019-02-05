package dogapp.dto;

import dogapp.dao.DogDao;
import dogapp.exception.DogNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static dogapp.utils.DogTestUtils.generateDog;
import static org.testng.Assert.assertThrows;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jdbc")
@ContextConfiguration("classpath:test-context.xml")
public class DogDaoTest {
    @Autowired
    private DogDao dogDao;

    @Test
    public void shouldCreateDog() {
        Dog newDog = generateDog();
        Dog dog = dogDao.create(newDog);
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldGetDog() {
        Dog newDog = generateDog();
        Dog createdDog = createDog(newDog);
        Dog dog = dogDao.get(createdDog.getId());
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldUpdateDog() {
        Dog newDog = generateDog();
        Dog createdDog = createDog(newDog);
        newDog.setId(createdDog.getId());
        Dog updatedDog = dogDao.update(newDog);

        assertReflectionEquals(newDog, updatedDog);
    }

    @Test
    public void shouldDeleteDog() {
        Dog newDog = generateDog();
        Dog createdDog = createDog(newDog);
        dogDao.delete(createdDog.getId());

        assertThrows(DogNotFoundException.class, () -> dogDao.get(createdDog.getId()));
    }

    @Test
    public void shouldThrowDogNotFoundExceptionWhenUpdateNotExistingDog() {
        assertThrows(DogNotFoundException.class, () -> dogDao.update(generateDog()));
    }

    @Test
    public void shouldThrowDogNotFoundExceptionWhenGetNotExistingDog() {
        assertThrows(DogNotFoundException.class, () -> dogDao.get(UUID.randomUUID()));
    }

    @Test
    public void shouldThrowDogNotFoundExceptionWhenDeleteNotExistingDog() {
        assertThrows(DogNotFoundException.class, () -> dogDao.delete(UUID.randomUUID()));
    }

    private Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }
}
