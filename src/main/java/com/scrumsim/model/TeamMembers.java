package com.scrumsim.model;

import java.util.Arrays;
import java.util.List;

public class TeamMembers {

    public static final String SAIRAJ_DALVI = "Sairaj Dalvi";
    public static final String PRANAV_IRLAPALE = "Pranav Irlapale";
    public static final String GUNJAN_PUROHIT = "Gunjan Purohit";
    public static final String SHREYAS_REVANKAR = "Shreyas Revankar";
    public static final String VIRAJ_RATHOR = "Viraj Rathor";

    public static final List<String> ALLOWED_MEMBERS = Arrays.asList(
        SAIRAJ_DALVI,
        PRANAV_IRLAPALE,
        GUNJAN_PUROHIT,
        SHREYAS_REVANKAR,
        VIRAJ_RATHOR
    );

    public static final List<String> ALLOWED_FIRST_NAMES = Arrays.asList(
        "Sairaj",
        "Pranav",
        "Gunjan",
        "Shreyas",
        "Viraj"
    );

    public static boolean isValidAssignee(String name) {
        if (name == null || name.trim().isEmpty()) {
            return true;
        }

        String trimmedName = name.trim();

        for (String member : ALLOWED_MEMBERS) {
            if (trimmedName.equals(member)) {
                return true;
            }
        }

        for (String firstName : ALLOWED_FIRST_NAMES) {
            if (trimmedName.equals(firstName)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidAssigneeList(String assignees) {
        if (assignees == null || assignees.trim().isEmpty()) {
            return true;
        }

        String[] names = assignees.split(",");
        for (String name : names) {
            if (!isValidAssignee(name)) {
                return false;
            }
        }

        return true;
    }

    public static String[] getAllowedMembersArray() {
        return ALLOWED_MEMBERS.toArray(new String[0]);
    }
}
