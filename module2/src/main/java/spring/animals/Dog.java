package spring.animals;

import org.springframework.stereotype.Component;

@Component
public class Dog extends Animal {

    public Dog() {
        this.name = "dogName";
    }

    public void printAnimal() {
        System.out.println("Dog " + name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
