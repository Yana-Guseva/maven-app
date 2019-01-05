package spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.weekday.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Configuration
@ComponentScan("spring.animals")
@Import(Schedule.class)
public class MyConfig {

    @Bean
    public WeekDayPrinter getWeekDayPrinter() {
        return new WeekDayPrinter();
    }

    @Bean
    public WeekDay getDay() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return new Monday();
            case TUESDAY:
                return new Tuesday();
            case WEDNESDAY:
                return new Wednesday();
            case THURSDAY:
                return new Thursday();
            case FRIDAY:
                return new Friday();
            case SATURDAY:
                return new Saturday();
            default:
                return new Sunday();
        }
    }
}
