package hms.user.helpers;

import java.time.LocalDate;
import java.util.Set;

import hms.user.Doctor;

public class ScheduleManager {

    public void setDoctorAvailabiity(Doctor doctor, LocalDate date, DailySchedule schedule) {
        doctor.setDailySchedule(date, schedule);
    }

    public DailySchedule getDoctorSchedule(Doctor doctor, LocalDate date) {
        return doctor.getDailySchedule(date);
    }

    public Set<LocalDate> getDoctorAvailDates(Doctor doctor) {
        return doctor.getAvailableDates();
    }
}
