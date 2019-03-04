package dogapp.controller;

import dogapp.dto.Dog;
import dogapp.service.DogServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "dog")
public class DogController {

    private final DogServiceImpl dogService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog createDog(@Valid @RequestBody Dog dog) {
        return dogService.createDog(dog);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog getDog(@PathVariable UUID id) {
        return dogService.getDog(id);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog updateDog(@PathVariable UUID id, @Valid @RequestBody Dog dog) {
        dog.setId(id);
        return dogService.updateDog(dog);
    }

    @DeleteMapping("{id}")
    public void deleteDog(@PathVariable UUID id) {
        dogService.deleteDog(id);
    }
}
