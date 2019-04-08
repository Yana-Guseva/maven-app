package dogapp.service;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static dogapp.utils.DogTestUtils.generateDog;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context.xml", "classpath:test-context.xml"})
public class DogServiceTest {
    @Autowired
    private DogServiceImpl dogServiceImpl;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldCreateDog() {
        Dog newDog = generateDog();
        Dog dog = dogServiceImpl.createDog(newDog);
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldGetDog() {
        Dog newDog = generateDog();
        Dog createdDog = createDog(newDog);
        Dog dog = dogServiceImpl.getDog(createdDog.getId());
        newDog.setId(dog.getId());

        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldUpdateDog() {
        Dog createdDog = createDog(generateDog());
        Dog newDog = generateDog();
        newDog.setId(createdDog.getId());
        dogServiceImpl.updateDog(newDog);
        Dog updatedDog = dogServiceImpl.getDog(createdDog.getId());

        assertReflectionEquals(newDog, updatedDog);
    }

    @Test
    public void shouldDeleteDog() {
        Dog createdDog = createDog(generateDog());
        dogServiceImpl.deleteDog(createdDog.getId());

        expectedException.expect(DogNotFoundException.class);
        expectedException.expectMessage("Dog not found " + createdDog.getId());
        dogServiceImpl.getDog(createdDog.getId());
    }

    private Dog createDog(Dog dog) {
        return dogServiceImpl.createDog(dog);
    }
}
