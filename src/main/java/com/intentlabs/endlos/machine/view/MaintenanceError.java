package com.intentlabs.endlos.machine.view;

import java.util.HashMap;
import java.util.Map;

public class MaintenanceError {

    private static final Map<String, String> errorMessage = new HashMap<>();

    static {
        errorMessage.put("resolve", "Resolved");
        errorMessage.put("forward", "Forward");
        errorMessage.put("reverse", "Reverse");
        errorMessage.put("stop", "Stop");
        errorMessage.put("tare", "Tare");
        errorMessage.put("calibrate", "Calibrate");
        errorMessage.put("open", "Open");
        errorMessage.put("close", "Close");
        errorMessage.put("plastic", "Plastic");
        errorMessage.put("glass", "Glass");
        errorMessage.put("FC_RIGHT_DOCK_ROTATION", "Right Conveyor Rotation Error");
        errorMessage.put("FC_LEFT_DOCK_ROTATION", "Left Conveyor Rotation Error");
        errorMessage.put("LOADCELL_ERROR", "Loadcell Error");
        errorMessage.put("PLASTIC_MOTOR_JAM", "Plastic Shredder Jam");
        errorMessage.put("PLASTIC_MOTOR_ROTATION", "Plastic Shredder Power Transmission Error");
        errorMessage.put("ALUMINIUM_MOTOR_JAM", "Aluminium Shredder Jam");
        errorMessage.put("ALUMINIUM_MOTOR_ROTATION", "Aluminium Shredder Power Transmission Error");
        errorMessage.put("GLASS_MOTOR_JAM", "Glass Shredder Jam");
        errorMessage.put("GLASS_MOTOR_ROTATION", "Glass Shredder Power Transmission Error");
        errorMessage.put("FRONT_TOP_DOOR_OPEN", "Front Top Door Open");
        errorMessage.put("FRONT_BOTTOM_DOOR_OPEN", "Front Bottom Door Open");
        errorMessage.put("LEFT_DOOR_OPEN", "Left Door Open");
        errorMessage.put("RIGHT_DOOR_OPEN", "Right Door Open");
        errorMessage.put("BACK_DOOR_OPEN", "Back Door Open");
        errorMessage.put("EMG_STATUS", "Emergency On");
        errorMessage.put("BIN_FULL_COUNT", "Storage Bin Full");
        errorMessage.put("REMOVE_BOTTLE", "Please remove container from the Conveyor and try inserting again.");
        errorMessage.put("DOOR_ERROR", "Sorter Door Error");
        errorMessage.put("PLASTIC_SORTER", "Plastic Sorter Flap Error");
        errorMessage.put("GLASS_SORTER", "Glass Sorter Flap Error");
        errorMessage.put("SHREDDER_CLEAN_CYCLE", "Machine is under cleaning process. Please wait for the green light to insert containers.");
        errorMessage.put("PLASTIC_BIN_FULL_COUNT", "Plastic Storage Bin Full");
        errorMessage.put("GLASS_BIN_FULL_COUNT", "Glass Storage Bin Full");
        errorMessage.put("ALUMINIUMN_BIN_FULL_COUNT", "Metal Cans Storage Bin Full");
        errorMessage.put("PRINTER_ROLE_STATUS", "Print paper has finished please contact store manager to refill");
    }
    public static String getMaintenanceErrorMessage(String errorMsg) {
        String[] errMsg = errorMsg.split("\\.");
        if (errMsg.length > 1) {
            String finalErrorMessage = errMsg[1];
            return errorMessage.getOrDefault(finalErrorMessage, "No Proper Error Message found");
        }
        else
            return errorMessage.getOrDefault(errorMsg, "No Proper Error Message found");
    }

}
