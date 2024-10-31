package hms.user.helpers;

import java.util.HashMap;
import java.util.Map;

public class DailySchedule {
    
    private Map<Integer, Boolean> slots; // slots = {hour, availability}

    public DailySchedule() {
        this.slots = new HashMap<>();

        for (int i = 9; i < 18; i++) { // Work hours from 0900-1700
            slots.put(i, true);
        }

        slots.put(12, false); // 1 hour for lunch
    }

    public void setUnavailable(int hr) {
        slots.put(hr, false);
    }

    public boolean isAvailable(int hr) {
        return slots.get(hr);
    }
}
