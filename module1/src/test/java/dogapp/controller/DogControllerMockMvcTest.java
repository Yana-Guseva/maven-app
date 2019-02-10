package dogapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dogapp.dto.Dog;
import dogapp.utils.DogTestUtils;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static dogapp.utils.DogTestUtils.generateDog;
import static io.qala.datagen.RandomShortApi.negativeDouble;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context.xml", "classpath:context.xml"})
@WebAppConfiguration
public class DogControllerMockMvcTest {

    private static final String BASE_URL = "/dog";

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
        Dog createdDog = generateDog();

        MockHttpServletResponse response = mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdDog)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse();
        Dog dog = objectMapper.readValue(response.getContentAsString(), Dog.class);

        createdDog.setId(dog.getId());
        assertReflectionEquals(createdDog, dog);
    }

    @Test
    public void shouldGetDog() throws Exception {
        Dog createdDog = createDog(DogTestUtils.generateDog());

        MockHttpServletResponse response = mvc.perform(get(BASE_URL + "/" + createdDog.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse();
        Dog dog = objectMapper.readValue(response.getContentAsString(), Dog.class);

        assertReflectionEquals(createdDog, dog);
    }

    @Test
    public void shouldUpdateDog() throws Exception {
        Dog createdDog = createDog(DogTestUtils.generateDog());
        Dog newDog = generateDog();

        MockHttpServletResponse response = mvc.perform(put(BASE_URL + "/" + createdDog.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDog)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse();
        Dog dog = objectMapper.readValue(response.getContentAsString(), Dog.class);

        newDog.setId(dog.getId());
        assertReflectionEquals(newDog, dog);
    }

    @Test
    public void shouldDeleteDog() throws Exception {
        Dog createdDog = createDog(DogTestUtils.generateDog());

        mvc.perform(delete(BASE_URL + "/" + createdDog.getId()))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnNotFoundWhenUpdateNotExistingDog() throws Exception {
        Dog createdDog = createDog(DogTestUtils.generateDog());

        mvc.perform(put(BASE_URL + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdDog)))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateInvalidDog() throws Exception {
        Dog dog = generateDog();
        dog.setName(null);

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateInvalidDog() throws Exception {
        Dog dog = generateDog();
        dog.setWeight(negativeDouble());

        mvc.perform(put(BASE_URL + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldReturnNotFoundWhenGetNotExistingDog() throws Exception {
        mvc.perform(get(BASE_URL + "/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldReturnNotFoundWhenDeleteNotExistingDog() throws Exception {
        mvc.perform(delete(BASE_URL + "/" + UUID.randomUUID()))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldReturnNotAcceptableWhenWrongMediaType() throws Exception {
        mvc.perform(get(BASE_URL + "/" + UUID.randomUUID())
                .accept(ContentType.TEXT.toString()))
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
    }

    private Dog createDog(Dog dog) throws Exception {
        MockHttpServletResponse response = mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse();
        return objectMapper.readValue(response.getContentAsString(), Dog.class);
    }
}
