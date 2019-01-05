package spring.xmlconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.weekday.Friday;

public class SpringHelloWorldDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        SpringHelloWorldPrinter printer = (SpringHelloWorldPrinter) context.getBean("springHelloWorldPrinter");
        printer.printMessage();

        Friday friday = context.getBean(Friday.class);
        System.out.println(friday.getWeekDayName());
    }
}
