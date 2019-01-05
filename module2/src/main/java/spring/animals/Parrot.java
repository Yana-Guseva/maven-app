package spring.animals;

import org.springframework.stereotype.Component;

@Component
public class Parrot extends Animal {

    public Parrot() {
        name = "parrotName";
    }

    public void printAnimal() {
        System.out.println("Parrot " + name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
