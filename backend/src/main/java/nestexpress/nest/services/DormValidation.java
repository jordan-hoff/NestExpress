package nestexpress.nest.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nestexpress.nest.entity.Dorm;
import nestexpress.nest.entity.User;

@Service
public class DormValidation {

    private static final int NO_DORM_SELECTED = 0;
    private static final int NO_ROOM_SELECTED = 0;
    private static final int BALLARD_DORM_ID  = 1;
    private static final int COBERLY_DORM_ID  = 2;
    private static final int RICE_DORM_ID     = 7;
    private static final int COBERLY_BALLARD_MIN_FLOOR = 1;
    private static final int RICE_HIGH_FLOOR_MIN_ROOM  = 50;
    private static final int RICE_LOW_FLOOR_MAX_ROOM   = 22;
    private static final int RICE_MIN_FLOOR   = 1;
    private static final int RICE_MAX_FLOOR   = 9;
    private static final int ROOM_NUM_LENGTH  = 4;

    private final DormService dormService;

    @Autowired
    public DormValidation(DormService dormService) {
        this.dormService = dormService;

    }

    public Map<String, String> dormValidation(User validUser) {
        Map<String, String> error = new HashMap<String, String>();
        Dorm validDorm            = new Dorm();

        validDorm = dormService.findDormById(validUser.getDorm());

        // Dorm selected must be valid.
        if (validUser.getDorm() < NO_DORM_SELECTED) {
            error.put("error", "Please select a dorm or select no dorm if applicable.");

            return error;
        }

        if (validUser.getDorm() > NO_DORM_SELECTED && validUser.getRoomNumber() == NO_ROOM_SELECTED) {
            error.put("error", "Please enter a room number in the dorm selected.");

            return error;
        }

        if (validUser.getDorm() == NO_DORM_SELECTED)
            validUser.setRoomNumber(NO_ROOM_SELECTED);

        // Rice Floor 1 Max:0-22 Floor 2-9 Max:0-22   Floor 10-11:50-72
        // Room Number must 4 digits and the first digit must match the dorm selected.
        if (validUser.getRoomNumber() != NO_ROOM_SELECTED) {
            if (String.valueOf(validUser.getRoomNumber()).length() != ROOM_NUM_LENGTH) {
                error.put("error", "Your room number must be four digits.");

                return error;
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) != validUser.getDorm()) {
                error.put("error", "The room number entered is not in the dorm selected (" +
                        validDorm.getDormName() + ").");

                return error;
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) > validDorm.getNumFloors()) {
                error.put("error", "The floor entered is not a valid floor.");

                return error;
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) > validDorm.getMaxRoom()) {
                error.put("error", "The room entered is a higher number than the highest room number " +
                        "in the dorm.");

                return error;
            }

            // Ballard and Coberly start at floor 1.
            else if ((Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) == BALLARD_DORM_ID ||
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) == COBERLY_DORM_ID) &&
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) < COBERLY_BALLARD_MIN_FLOOR) {
                error.put("error", "The floor entered is not a valid floor.");

                return error;

            // Special cases for Rice.
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) == RICE_DORM_ID) {
                // If the floor entered is 2-9.
                if ((Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) > RICE_MIN_FLOOR &&
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) <= RICE_MAX_FLOOR) &&
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM) {
                    error.put("error", "The room entered is invalid.");

                    return error;
                }

                // Rooms 7123-7149 should give an error.
                if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) == RICE_MIN_FLOOR &&
                    (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM &&
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM)) {
                    error.put("error", "The room entered is invalid.");

                    return error;
                }

                // If the floor entered is 0 and the room is less than 50.
                if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) == RICE_MIN_FLOOR-1 &&
                    Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM) {
                    error.put("error", "The room entered is invalid.");

                    return error;
                }
            }
        } else {
            if (validUser.getDorm() != NO_DORM_SELECTED) {
                error.put("error", "Please enter a room number in the dorm selected.");

                return error;
            }
        }

        return null;
    }
}