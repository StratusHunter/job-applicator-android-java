package com.bulbstudios.jobapplicator.classes;

import android.util.JsonReader;
import android.util.Log;

import com.bulbstudios.jobapplicator.BuildConfig;
import com.bulbstudios.jobapplicator.interfaces.CreateFromJSON;
import com.bulbstudios.jobapplicator.interfaces.ToJSON;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public class RequestHandler {

    public @NonNull
    Pair<JobApplication, HttpURLConnection> performApplyRequest(@NonNull JobApplication application) {

        HttpURLConnection connection = createApplyRequest();
        JobApplication response = null;

        if (connection != null) {

            writeJSONToConnection(connection, application);
            response = getJobApplicationResponse(connection, JobApplication :: fromJSON);
            connection.disconnect();
        }

        return new Pair<>(response, connection);
    }

    private @Nullable
    HttpURLConnection createApplyRequest() {

        try {

            URL applyURL = new URL(String.format("%sapply", BuildConfig.baseURL));

            HttpURLConnection connection = (HttpURLConnection) applyURL.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            return connection;
        }
        catch (IOException ioe) {

            Log.e(getClass().getSimpleName(), "Error creating request");
        }

        return null;
    }

    private void writeJSONToConnection(@NonNull HttpURLConnection connection, @NonNull ToJSON jsonObject) {

        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {

            writer.write(jsonObject.toJSON().toString());
        }
        catch (JSONException jse) {

            Log.e(this.getClass().getSimpleName(), "Error writing JSON");
        }
        catch (IOException ioe) {

            Log.e(this.getClass().getSimpleName(), "Error writing JSON");
        }
    }

    private @Nullable
    <T> T getJobApplicationResponse(@NonNull HttpURLConnection connection, CreateFromJSON<T> fromJSON) {

        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream());
             JsonReader jsonReader = new JsonReader(reader)) {

            return fromJSON.createFromJSON(jsonReader);
        }
        catch (IOException ioe) {

            Log.e(this.getClass().getSimpleName(), "Error reading JSON");
        }

        return null;
    }
}
