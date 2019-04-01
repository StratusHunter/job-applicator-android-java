package com.bulbstudios.jobapplicator.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public interface ToJSON {

    JSONObject toJSON() throws JSONException;
}
