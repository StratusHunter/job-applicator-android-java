package com.bulbstudios.jobapplicator;

import com.bulbstudios.jobapplicator.classes.JobApplication;
import com.bulbstudios.jobapplicator.classes.RequestHandler;
import com.bulbstudios.jobapplicator.enums.TeamType;
import com.bulbstudios.jobapplicator.viewmodels.MainViewModel;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.concurrent.FutureTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ViewModelTest {

    private MainViewModel urlSuccessViewModel = new MainViewModel((url) -> true, new RequestHandler());
    private MainViewModel urlFailViewModel = new MainViewModel((url) -> false, new RequestHandler());

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

    @Test
    public void createApplication_validatePropertyMapping_assertEqual() {

        JobApplication application = urlSuccessViewModel.createApplication(name, email, team, about, url);

        assertEquals("Name property not mapped correctly", application.name, name);
        assertEquals("Email property not mapped correctly", application.email, email);
        assertEquals("About property not mapped correctly", application.about, about);
        assertEquals("Team property not mapped correctly", application.teams.get(0), team);
        assertEquals("URL property not mapped correctly", application.urls.get(0), url);
    }

    @Test
    public void createApplication_validateArrayPopulation_assertEqual() {

        JobApplication application = urlSuccessViewModel.createApplication(name,
                email,
                String.format("%s, %s", team, TeamType.Team.ios.getRawValue()),
                about,
                String.format("%s\n%s", url, url));

        assertEquals("Unexpected team array size", application.teams.size(), 2);
        assertEquals("Unexpected URL array size", application.urls.size(), 2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void performApplyRequest_withValidData_assertEqual() throws Exception {

        JobApplication application = new JobApplication(name, email, about, Collections.singletonList(url), Collections.singletonList(team));

        FutureTask<JobApplication> mockRequest = (FutureTask<JobApplication>)Mockito.mock(FutureTask.class);
        Mockito.when(mockRequest.get()).thenReturn(application);

        MainViewModel mockViewModel = Mockito.mock(MainViewModel.class);
        Mockito.when(mockViewModel.createApplyRequestFuture(application)).thenReturn(mockRequest);

        FutureTask<JobApplication> requestFuture = mockViewModel.createApplyRequestFuture(application);
        assertEquals(application, requestFuture.get());
    }
}