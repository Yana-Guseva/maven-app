package dogapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dogapp.dto.Dog;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static dogapp.utils.DogTestUtils.DOG;
import static dogapp.utils.DogTestUtils.generateDog;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
@WebAppConfiguration
public class DogControllerMockMvcTest {

    private static final String BASE_URL = "/dog";
    private static final UUID EXIST_DOG_ID = UUID.fromString("8535af21-545c-4a5e-a78a-60c07b5a399a");
    private static final UUID ID_FOR_UPDATE = UUID.fromString("a332695f-cef5-41a6-984e-5213cb2fd372");
    private static final UUID ID_FOR_DELETE = UUID.fromString("7223739b-7a40-4fa1-9cae-3c5001a2f39f");

    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    public void shouldCreateDog() throws Exception {
        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DOG)))
                .andExpect(status().is(HttpStatus.OK.value()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", is(DOG.getName())))
                .andExpect(jsonPath("$.dateOfBirth[0]", is(DOG.getDateOfBirth().getYear())))
                .andExpect(jsonPath("$.dateOfBirth[1]", is(DOG.getDateOfBirth().getMonthValue())))
                .andExpect(jsonPath("$.dateOfBirth[2]", is(DOG.getDateOfBirth().getDayOfMonth())))
                .andExpect(jsonPath("$.height", is(DOG.getHeight())))
                .andExpect(jsonPath("$.weight", is(DOG.getWeight())));
    }

    @Test
    public void shouldGetDog() throws Exception {
        mvc.perform(get(BASE_URL + "/" + EXIST_DOG_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", is("Rema")))
                .andExpect(jsonPath("$.dateOfBirth[0]", is(2018)))
                .andExpect(jsonPath("$.dateOfBirth[1]", is(9)))
                .andExpect(jsonPath("$.dateOfBirth[2]", is(7)))
                .andExpect(jsonPath("$.height", is(60.0)))
                .andExpect(jsonPath("$.weight", is(17.0)));
    }

    @Test
    public void shouldUpdateDog() throws Exception {
        mvc.perform(put(BASE_URL + "/" + ID_FOR_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DOG)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", is(DOG.getName())))
                .andExpect(jsonPath("$.dateOfBirth[0]", is(DOG.getDateOfBirth().getYear())))
                .andExpect(jsonPath("$.dateOfBirth[1]", is(DOG.getDateOfBirth().getMonthValue())))
                .andExpect(jsonPath("$.dateOfBirth[2]", is(DOG.getDateOfBirth().getDayOfMonth())))
                .andExpect(jsonPath("$.height", is(DOG.getHeight())))
                .andExpect(jsonPath("$.weight", is(DOG.getWeight())));
    }

    @Test
    public void shouldDeleteDog() throws Exception {
        mvc.perform(delete(BASE_URL + "/" + ID_FOR_DELETE))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateNotExistingDog() throws Exception {
        mvc.perform(put(BASE_URL + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DOG)))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldThrowExceptionWhenCreateInvalidDog() throws Exception {
        Dog dog = generateDog();
        dog.setName(null);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateInvalidDog() throws Exception {
        Dog dog = generateDog();
        dog.setWeight(-9.);

        mvc.perform(put(BASE_URL + "/" + ID_FOR_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldThrowExceptionWhenGetNotExistingDog() throws Exception {
        mvc.perform(get(BASE_URL + "/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldThrowExceptionWhenDeleteNotExistingDog() throws Exception {
        mvc.perform(delete(BASE_URL + "/" + UUID.randomUUID()))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldThrowExceptionWhenWrongMediaType() throws Exception {
        mvc.perform(get(BASE_URL + "/" + EXIST_DOG_ID)
                .accept(ContentType.TEXT.toString()))
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
    }
}
