package dogapp.controller;

import dogapp.ErrorResponse;
import dogapp.dto.Dog;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static dogapp.utils.DogTestUtils.generateDog;
import static io.qala.datagen.RandomShortApi.negativeDouble;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class DogControllerTest {

    private static final String DOG_NOT_FOUND = "Dog not found";

    @BeforeClass
    public void setUp() {
        RestAssured.basePath = "/dog";
    }

    @Test
    public void shouldCreateDog() {
        Dog dog = generateDog();
        Dog createdDog = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post().then().statusCode(HttpStatus.OK.value()).extract().body().as(Dog.class);

        dog.setId(createdDog.getId());
        assertReflectionEquals(createdDog, dog);
    }

    @Test
    public void shouldGetDog() {
        Dog createdDog = createDog(generateDog());

        Dog dog = given().contentType(ContentType.JSON)
                .when().get("{id}", createdDog.getId()).then().statusCode(HttpStatus.OK.value()).extract().body().as(Dog.class);

        assertThat(createdDog.getId(), equalTo(dog.getId()));
        assertReflectionEquals(createdDog, dog);
    }

    @Test
    public void shouldUpdateDog() {
        Dog createdDog = createDog(generateDog());
        Dog newDog = generateDog();
        Dog updatedDog = given().body(newDog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().put("{id}", createdDog.getId()).then().statusCode(HttpStatus.OK.value()).extract().body().as(Dog.class);

        newDog.setId(updatedDog.getId());
        assertReflectionEquals(updatedDog, newDog);
    }

    @Test
    public void shouldDeleteDog() {
        Dog createdDog = createDog(generateDog());

        given().when().delete("{id}", createdDog.getId()).then().statusCode(HttpStatus.OK.value());
        given().when().get("{id}", createdDog.getId()).then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnDogNotFoundWhenUpdateNotExistingDog() {
        ErrorResponse errorResponse = given().body(generateDog()).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().put("{id}", UUID.randomUUID()).then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().body().as(ErrorResponse.class);
        assertThat(errorResponse.getMessage(), containsString(DOG_NOT_FOUND));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateInvalidDog() {
        Dog dog = createDog(generateDog());
        dog.setName(null);

        given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().put("{id}", dog.getId()).then().statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void shouldReturnBadRequestWhenUpdateInvalidDog() {
        Dog dog = generateDog();
        dog.setWeight(negativeDouble());

        given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post().then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnDogNotFoundWhenGetNotExistingDog() {
        ErrorResponse errorResponse = given().contentType(ContentType.JSON)
                .when().get("{id}", UUID.randomUUID()).then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().body().as(ErrorResponse.class);
        assertThat(errorResponse.getMessage(), containsString(DOG_NOT_FOUND));
    }

    @Test
    public void shouldReturnDogNotFoundWhenDeleteNotExistingDog() {
        ErrorResponse errorResponse = given().when().delete("{id}", UUID.randomUUID()).then()
                .statusCode(HttpStatus.NOT_FOUND.value()).extract().body().as(ErrorResponse.class);
        assertThat(errorResponse.getMessage(), containsString(DOG_NOT_FOUND));
    }

    @Test
    public void shouldUnsupportedMediaTyprWhenWrongMediaType() {
        given().body(generateDog()).accept(ContentType.TEXT).contentType(ContentType.TEXT)
                .when().post().then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    private Dog createDog(Dog dog) {
        return given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().body().as(Dog.class);
    }
}
