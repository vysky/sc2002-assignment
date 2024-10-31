package hms.user;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hms.user.helpers.DailySchedule;

public class Doctor extends Staff {
    private Map<LocalDate, DailySchedule> personalSchedule;
    private Set<LocalDate> availableDates;

    public Doctor(String staffId, String name, String role, String gender, int age) {
        super(staffId, name, role, gender, age);
        this.personalSchedule = new HashMap<>();
        this.availableDates = new HashSet<>();
    }

    // GETTERS

    public DailySchedule getDailySchedule(LocalDate date) { // Gets the schedule for a given date
        return personalSchedule.get(date);
    }

    public Set<LocalDate> getAvailableDates() {
        return availableDates;
    }

    // SETTERS

    public void setDailySchedule(LocalDate date, DailySchedule schedule) {
        personalSchedule.put(date, schedule);
        availableDates.add(date);
    }
}