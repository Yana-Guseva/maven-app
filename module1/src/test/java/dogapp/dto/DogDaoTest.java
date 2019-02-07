package dogapp.dto;

import dogapp.dao.DogDao;
import dogapp.exception.DogNotFoundException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import static dogapp.utils.DogTestUtils.generateDog;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jdbc")
@ContextConfiguration("classpath:test-context.xml")
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
        Dog newDog = generateDog();
        Dog createdDog = createDog(newDog);
        newDog.setId(createdDog.getId());
        Dog updatedDog = dogDao.update(newDog);

        assertReflectionEquals(newDog, updatedDog);
    }

    @Test(expected = DogNotFoundException.class)
    public void shouldDeleteDog() {
        Dog newDog = generateDog();
        Dog createdDog = createDog(newDog);
        dogDao.delete(createdDog.getId());

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
        newDog.setName(new String(new char[100]).replace("\0", "a"));
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
    public void shouldThrowRuntimeExceptionWhenDogNameIsNull() {
        Dog dog = generateDog();
        dog.setName(null);

        expectedException.expect(RuntimeException.class);
        expectedException.expectCause(IsInstanceOf.instanceOf(SQLException.class));
        expectedException.expectMessage("NULL not allowed for column \"NAME\"");
        dogDao.create(dog);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenDogNameIsTooLong() {
        Dog dog = generateDog();
        dog.setName(new String(new char[101]).replace("\0", "a"));

        expectedException.expect(RuntimeException.class);
        expectedException.expectCause(IsInstanceOf.instanceOf(SQLException.class));
        expectedException.expectMessage("Value too long for column \"NAME VARCHAR(100) NOT NULL\"");
        dogDao.create(dog);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenDogWeightIsNull() {
        Dog dog = generateDog();
        dog.setWeight(null);

        expectedException.expect(RuntimeException.class);
        expectedException.expectCause(IsInstanceOf.instanceOf(SQLException.class));
        expectedException.expectMessage("NULL not allowed for column \"WEIGHT\"");
        dogDao.create(dog);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenDogHeightIsNull() {
        Dog dog = generateDog();
        dog.setHeight(null);

        expectedException.expect(RuntimeException.class);
        expectedException.expectCause(IsInstanceOf.instanceOf(SQLException.class));
        expectedException.expectMessage("NULL not allowed for column \"HEIGHT\"");
        dogDao.create(dog);
    }

    @Test
    public void shouldAllowSqlInjection() {
        Dog newDog = generateDog();
        Dog dog = dogDao.create(newDog);
        dog.setName(String.format("'; DELETE FROM DOG WHERE id = '%s'; --", dog.getId()));
        dogDao.update(dog);

        expectedException.expect(DogNotFoundException.class);
        expectedException.expectMessage("Dog not found " + dog.getId());
        dogDao.get(dog.getId());

    }

    private Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }
}
