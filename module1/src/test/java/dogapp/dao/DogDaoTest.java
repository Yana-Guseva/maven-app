package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.UUID;

import static dogapp.utils.DogTestUtils.generateDog;
import static io.qala.datagen.RandomShortApi.alphanumeric;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("postgres-jdbc")
@ContextConfiguration({"classpath:test-context.xml", "classpath:context.xml"})
public class DogDaoTest {
    @Autowired
    private DogDao dogDao;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        Dog createdDog = createDog(generateDog());
        Dog newDog = generateDog();
        newDog.setId(createdDog.getId());
        dogDao.update(newDog);
        Dog updatedDog = dogDao.get(createdDog.getId());

        assertReflectionEquals(newDog, updatedDog);
    }

    @Test
    public void shouldDeleteDog() {
        Dog createdDog = createDog(generateDog());
        dogDao.delete(createdDog.getId());

        expectedException.expect(DogNotFoundException.class);
        expectedException.expectMessage("Dog not found " + createdDog.getId());
        dogDao.get(createdDog.getId());
    }

    @Test(expected = DogNotFoundException.class)
    public void shouldThrowDogNotFoundExceptionWhenUpdateNotExistingDog() {
        dogDao.update(generateDog());
    }

    @Test(expected = DogNotFoundException.class)
    public void shouldThrowDogNotFoundExceptionWhenGetNotExistingDog() {
        dogDao.get(UUID.randomUUID());
    }

    @Test(expected = DogNotFoundException.class)
    public void shouldThrowDogNotFoundExceptionWhenDeleteNotExistingDog() {
        dogDao.delete(UUID.randomUUID());
    }

    @Test
    public void shouldCreateDogWithMinConstraints() {
        Dog newDog = generateDog();
        newDog.setName("");
        newDog.setWeight(Double.MIN_VALUE);
        newDog.setHeight(Double.MIN_VALUE);
        newDog.setDateOfBirth(LocalDate.MIN);
        Dog dog = dogDao.create(newDog);
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldCreateDogWithMaxConstraints() {
        Dog newDog = generateDog();
        newDog.setName(alphanumeric(100));
        newDog.setWeight(Double.MAX_VALUE);
        newDog.setHeight(Double.MAX_VALUE);
        newDog.setDateOfBirth(LocalDate.MAX);
        Dog dog = dogDao.create(newDog);
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldCreateDogWithNullDateOfBirth() {
        Dog newDog = generateDog();
        newDog.setDateOfBirth(null);
        Dog dog = dogDao.create(newDog);
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldPreventSqlInjection() {
        Dog dog = dogDao.create(generateDog());
        dog.setName("'");
        assertReflectionEquals(dogDao.update(dog), dogDao.get(dog.getId()));
    }

    private Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }
}
