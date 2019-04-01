package com.bulbstudios.jobapplicator.classes;

import android.util.JsonReader;
import android.util.Log;

import com.bulbstudios.jobapplicator.interfaces.ToJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public class JobApplication implements ToJSON {

    private enum JSONKey {

        name("name"),
        email("email"),
        about("about"),
        urls("urls"),
        teams("teams");

        private final @NonNull
        String rawValue;

        JSONKey(@NonNull String rawValue) {

            this.rawValue = rawValue;
        }
    }

    public @NonNull
    final String name;
    public @NonNull
    final String email;
    public @NonNull
    final String about;
    public @NonNull
    final List<String> urls;
    public @NonNull
    final List<String> teams;

    public JobApplication(@NonNull String name, @NonNull String email, @NonNull String about, @NonNull List<String> urls, @NonNull List<String> teams) {

        this.name = name;
        this.email = email;
        this.about = about;
        this.urls = urls;
        this.teams = teams;
    }

    static @Nullable
    JobApplication fromJSON(@NonNull JsonReader reader) {

        try {

            String name = "";
            String email = "";
            String about = "";
            ArrayList<String> urls = new ArrayList<>();
            ArrayList<String> teams = new ArrayList<>();

            reader.beginObject();
            while (reader.hasNext()) {

                String nextName = reader.nextName();

                if (nextName.equals(JSONKey.name.rawValue)) {

                    name = reader.nextString();
                }
                else if (nextName.equals(JSONKey.email.rawValue)) {

                    email = reader.nextString();
                }
                else if (nextName.equals(JSONKey.about.rawValue)) {

                    about = reader.nextString();
                }
                else if (nextName.equals(JSONKey.teams.rawValue)) {

                    reader.beginArray();
                    while (reader.hasNext()) { teams.add(reader.nextString()); }
                    reader.endArray();
                }
                else if (nextName.equals(JSONKey.urls.rawValue)) {

                    reader.beginArray();
                    while (reader.hasNext()) { urls.add(reader.nextString()); }
                    reader.endArray();
                }
            }
            reader.endObject();

            return new JobApplication(name, email, about, urls, teams);
        }
        catch (IOException ioe) {

            Log.e(JobApplication.class.getSimpleName(), "Error parsing JSON");
            return null;
        }
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSONKey.name.rawValue, name);
        jsonObject.put(JSONKey.email.rawValue, email);
        jsonObject.put(JSONKey.about.rawValue, about);
        jsonObject.put(JSONKey.teams.rawValue, new JSONArray(teams));
        jsonObject.put(JSONKey.urls.rawValue, new JSONArray(urls));

        return jsonObject;
    }
}
