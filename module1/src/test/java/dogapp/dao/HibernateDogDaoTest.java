package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import org.hibernate.SessionFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.UUID;

import static dogapp.utils.DogTestUtils.generateDog;
import static io.qala.datagen.RandomShortApi.alphanumeric;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ActiveProfiles("default")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context.xml", "classpath:test-context.xml"})
public class HibernateDogDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private HibernateDogDao dogDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldCreateDog() {
        Dog newDog = dogDao.create(generateDog());
        flushAndClear();
        Dog createdDog = dogDao.get(newDog.getId());
        assertReflectionEquals(newDog, createdDog);
    }

    @Test
    public void shouldGetDog() {
        Dog createdDog = createDog(generateDog());
        flushAndClear();
        Dog dog = dogDao.get(createdDog.getId());
        assertReflectionEquals(createdDog, dog);
    }

    @Test
    public void shouldUpdateDog() {
        Dog createdDog = createDog(generateDog());
        flushAndClear();
        Dog newDog = generateDog();
        newDog.setId(createdDog.getId());
        dogDao.update(newDog);
        flushAndClear();
        Dog updatedDog = dogDao.get(createdDog.getId());
        assertReflectionEquals(newDog, updatedDog);
    }

    @Test
    public void shouldDeleteDog() {
        Dog createdDog = createDog(generateDog());
        flushAndClear();
        dogDao.delete(createdDog.getId());
        sessionFactory.getCurrentSession().flush();
        expectedException.expect(DogNotFoundException.class);
        expectedException.expectMessage("Dog not found " + createdDog.getId());
        dogDao.get(createdDog.getId());
    }

    @Test(expected = DogNotFoundException.class)
    public void shouldThrowDogNotFoundExceptionWhenUpdateNotExistingDog() {
        dogDao.update(generateDog());
        flushAndClear();
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
        newDog.setName("q");
        newDog.setWeight(Double.MIN_VALUE);
        newDog.setHeight(Double.MIN_VALUE);
        newDog.setDateOfBirth(LocalDate.of(-2, 1, 1));
        Dog createdDog = dogDao.create(newDog);
        flushAndClear();
        Dog dog = dogDao.get(createdDog.getId());
        newDog.setId(createdDog.getId());
        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldCreateDogWithMaxConstraints() {
        Dog newDog = generateDog();
        newDog.setName(alphanumeric(100));
        newDog.setWeight(Double.MAX_VALUE);
        newDog.setHeight(Double.MAX_VALUE);
        newDog.setDateOfBirth(LocalDate.now().minusDays(1));
        Dog createdDog = dogDao.create(newDog);
        flushAndClear();
        Dog dog = dogDao.get(createdDog.getId());
        newDog.setId(createdDog.getId());
        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldCreateDogWithNullDateOfBirth() {
        Dog newDog = generateDog();
        newDog.setDateOfBirth(null);
        Dog dog = dogDao.create(newDog);
        flushAndClear();
        newDog.setId(dog.getId());
        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldPreventSqlInjection() {
        Dog dog = dogDao.create(generateDog());
        flushAndClear();
        dog.setName("'");
        Dog updatedDog = dogDao.update(dog);
        flushAndClear();
        assertReflectionEquals(updatedDog, dogDao.get(dog.getId()));
    }

    private Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }

    private void flushAndClear() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}
