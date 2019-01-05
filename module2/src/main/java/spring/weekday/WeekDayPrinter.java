package spring.weekday;

import org.springframework.beans.factory.annotation.Autowired;

public class WeekDayPrinter {
    @Autowired
    private WeekDay weekDay;

    public void printWeekDay() {
        System.out.println("It's " + weekDay.getWeekDayName() + " today");
    }
}
