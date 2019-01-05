package spring.weekday;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Schedule {
    private WeekDay weekDay;

    public Schedule(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @Bean("schedule")
    public Schedule getSchedule() {
        return new Schedule(weekDay);
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }
}
