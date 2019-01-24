package dogapp.dto;

import dogapp.utils.DogTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class DogValidationTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldMeetConstraintDogNameIsTooShort() {
        Dog dog = DogTestUtils.generateDog();
        dog.setName("");

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 100", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogNameIsTooLong() {
        Dog dog = DogTestUtils.generateDog();
        dog.setName(new String(new char[101]).replace("\0", "a"));

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 100", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogNameIsNull() {
        Dog dog = DogTestUtils.generateDog();
        dog.setName(null);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogBirthdayIsNow() {
        Dog dog = DogTestUtils.generateDog();
        dog.setDateOfBirth(LocalDate.now());

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a past date", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogHighIsNegative() {
        Dog dog = DogTestUtils.generateDog();
        dog.setHeight(-1.);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than 0", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogHighIsNull() {
        Dog dog = DogTestUtils.generateDog();
        dog.setHeight(null);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogWeightIsNegative() {
        Dog dog = DogTestUtils.generateDog();
        dog.setWeight(-1.);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than 0", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldMeetConstraintDogWeightIsNull() {
        Dog dog = DogTestUtils.generateDog();
        dog.setWeight(null);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }
}