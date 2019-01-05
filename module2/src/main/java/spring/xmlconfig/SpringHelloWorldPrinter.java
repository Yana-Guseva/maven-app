package spring.xmlconfig;

public class SpringHelloWorldPrinter {
    private SpringHelloWorld springHelloWorld;

    public void setSpringHelloWorld(SpringHelloWorld springHelloWorld) {
        this.springHelloWorld = springHelloWorld;
    }

    public void printMessage() {
        System.out.println(springHelloWorld.getMessage());
    }
}
