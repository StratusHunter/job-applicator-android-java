package com.bulbstudios.jobapplicator;

import com.bulbstudios.jobapplicator.enums.TeamType;
import com.bulbstudios.jobapplicator.viewmodels.MainViewModel;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ViewModelTest {

    private MainViewModel urlSuccessViewModel = new MainViewModel((url) -> true);
    private MainViewModel urlFailViewModel = new MainViewModel((url) -> false);

    private String name = "A Name";
    private String email = "test@test.com";
    private String team = TeamType.Team.android.getRawValue();
    private String about = "About text";
    private String url = "https://test.com";

    @Test
    public void validateApplication_withValidData_assertTrue() {

        boolean validData = urlSuccessViewModel.validateApplication(name, email, team, about, url);

        assertTrue("Valid data no longer considered valid", validData);
    }

    @Test
    public void validateApplication_withMissingData_assertFalse() {

        boolean nameMissing = urlSuccessViewModel.validateApplication("", email, team, about, url);
        boolean emailMissing = urlSuccessViewModel.validateApplication(name, "", team, about, url);
        boolean teamMissing = urlSuccessViewModel.validateApplication(name, email, "", about, url);
        boolean aboutMissing = urlSuccessViewModel.validateApplication(name, email, team, "", url);
        boolean urlMissing = urlSuccessViewModel.validateApplication(name, email, team, about, "");

        assertFalse("Name should not be empty", nameMissing);
        assertFalse("Email should not be empty", emailMissing);
        assertFalse("Team should not be empty", teamMissing);
        assertFalse("About should not be empty", aboutMissing);
        assertFalse("URLs should not be empty", urlMissing);
    }

    @Test
    public void validateApplication_withIncorrectData_assertFalse() {

        boolean emailInvalid = urlSuccessViewModel.validateApplication(name, "Not an email", team, about, url);
        boolean teamInvalid = urlSuccessViewModel.validateApplication(name, email, "Not a team", about, url);
        boolean urlInvalid = urlFailViewModel.validateApplication(name, email, team, about, "Not a url");

        assertFalse("Email validation failing", emailInvalid);
        assertFalse("Team validation failing", teamInvalid);
        assertFalse("URL validation failing", urlInvalid);
    }
}