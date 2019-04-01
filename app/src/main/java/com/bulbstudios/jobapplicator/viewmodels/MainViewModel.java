package com.bulbstudios.jobapplicator.viewmodels;

import android.webkit.URLUtil;

import com.bulbstudios.jobapplicator.enums.TeamType;
import com.bulbstudios.jobapplicator.interfaces.URLValidator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;
import androidx.lifecycle.ViewModel;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public class MainViewModel extends ViewModel {

    private URLValidator urlValidator;

    public MainViewModel() {

        this(URLUtil::isValidUrl);
    }

    public MainViewModel(URLValidator urlValidator) {

        this.urlValidator = urlValidator;
    }

    private @NonNull List<TeamType.Team> createTeamList(@NonNull String team) {

        String[] teamArray = team.split(",");
        ArrayList<TeamType.Team> teamList = new ArrayList<>();

        for (String teamString : teamArray) {

            String noSpacesTeamString = teamString.replace(" ", "");
            TeamType.Team teamEnum = TeamType.with(noSpacesTeamString);

            if (teamEnum != null) {

                teamList.add(teamEnum);
            }
        }

        return teamList;
    }

    private @NonNull List<String> createURLList(@NonNull String url) {

        String[] urlArray = url.split("\n");
        ArrayList<String> urlList = new ArrayList<>();

        for (String urlString : urlArray) {

            if (!urlString.isEmpty() && urlValidator.validateURL(urlString)) {

                urlList.add(urlString);
            }
        }

        return urlList;
    }

    public boolean validateApplication(@NonNull String name, @NonNull String email, @NonNull String teams, @NonNull String about, @NonNull String urls) {

        boolean emailValid = !email.isEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
        boolean teamsValid = !(createTeamList(teams).isEmpty());
        boolean urlsValid = !(createURLList(urls).isEmpty());

        return !name.isEmpty() && emailValid && teamsValid && !about.isEmpty() && urlsValid;
    }
}
