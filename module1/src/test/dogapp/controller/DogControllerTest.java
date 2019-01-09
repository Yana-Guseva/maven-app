package dogapp.controller;

import dogapp.Dog;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class DogControllerTest {

    private static final String BASE_URI = "http://localhost:8080/test/dog";
    private static final UUID DOG_ID = UUID.randomUUID();
    private static Dog dog = Dog.builder().id(DOG_ID)
            .name("Rema")
            .dateOfBirth(Date.from(LocalDate.of(2018, 9, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .height(60.)
            .weight(17.)
            .build();

    @BeforeClass
    public void setUp() {
        RestAssured.basePath = "/test/dog";
    }

    @Test
    public void shouldCreateDog() {
        Dog createdDog = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON).when().post(BASE_URI)
                .then().statusCode(200).extract().body().as(Dog.class);
        System.out.println(createdDog.getName());
    }

    @Test
    public void shouldGetDog() {
        Dog dog = given().contentType(ContentType.JSON).param("id", DOG_ID)
                .when().get(BASE_URI)
                .then().statusCode(200).extract().body().as(Dog.class);
        System.out.println(dog.getName());
    }
}
