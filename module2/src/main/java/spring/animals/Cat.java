package spring.animals;

import org.springframework.stereotype.Component;

@Component
public class Cat extends Animal {

    public Cat() {
        name = "catName";
    }

    public void printAnimal() {
        System.out.println("Cat " + name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
