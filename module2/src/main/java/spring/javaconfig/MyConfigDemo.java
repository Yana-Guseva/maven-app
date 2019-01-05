package spring.javaconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.animals.Cat;
import spring.animals.Dog;
import spring.animals.Parrot;
import spring.weekday.Schedule;
import spring.weekday.WeekDayPrinter;

public class MyConfigDemo {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Dog dog = (Dog) context.getBean("dog");
        Cat cat = context.getBean(Cat.class);
        Parrot parrot = context.getBean("parrot", Parrot.class);

        dog.printAnimal();
        cat.printAnimal();
        parrot.printAnimal();

        WeekDayPrinter weekDayPrinter = context.getBean(WeekDayPrinter.class);
        weekDayPrinter.printWeekDay();

        Schedule schedule = (Schedule) context.getBean("schedule");
        System.out.println("Schedule for " + schedule.getWeekDay().getWeekDayName());
    }
}
