package dogapp.controller;

import dogapp.Dog;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class DogControllerTest {

    private static final String BASE_URI = "http://localhost:8080/dog";
    private static final UUID DOG_ID = UUID.randomUUID();
    private static Dog dog = Dog.builder().id(DOG_ID)
            .name("Rema")
            .dateOfBirth(LocalDate.of(2018, 9, 7))
            .height(60.)
            .weight(17.)
            .build();

    @BeforeClass
    public void setUp() {
        RestAssured.basePath = "/dog";
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void shouldCreateDog() {
        Response response = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(BASE_URI);
        Dog as = response.body().as(Dog.class);
//                .then().statusCode(200).extract().body();
        System.out.println(as.getName());
    }

    @Test
    public void shouldGetDog() {
        Dog dog = given().contentType(ContentType.JSON)
                .when().get(BASE_URI + "/" + DOG_ID)
                .then().statusCode(200).extract().body().as(Dog.class);
        System.out.println(dog.getName());
    }
}
