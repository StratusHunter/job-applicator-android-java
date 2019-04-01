package com.bulbstudios.jobapplicator.classes;

import java.util.List;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public class JobApplication {

    public String name;
    public String email;
    public String about;
    public List<String> urls;
    public List<String> teams;

    public JobApplication(String name, String email, String about, List<String> urls, List<String> teams) {

        this.name = name;
        this.email = email;
        this.about = about;
        this.urls = urls;
        this.teams = teams;
    }
}
