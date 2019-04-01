package com.bulbstudios.jobapplicator.enums;

import androidx.annotation.Nullable;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public class TeamType {

    public enum Team {

        android("android"),
        ios("ios"),
        backend("backend"),
        frontend("frontend"),
        design("design");

        private String rawValue;

        Team(String rawValue) {

            this.rawValue = rawValue;
        }

        String getRawValue() { return rawValue; }
    }

    public static @Nullable
    Team with(String rawValue) {

        for (Team team : Team.values()) {

            if (team.getRawValue().equals(rawValue)) {

                return team;
            }
        }

        return null;
    }
}
