package com.bulbstudios.jobapplicator.interfaces;

import android.util.JsonReader;

/**
 * Created by Terence Baker on 01/04/2019.
 */
public interface CreateFromJSON<T> {

    T createFromJSON(JsonReader reader);
}
