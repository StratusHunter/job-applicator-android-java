package com.bulbstudios.jobapplicator.activities;

import android.os.Bundle;

import com.bulbstudios.jobapplicator.R;
import com.bulbstudios.jobapplicator.viewmodels.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setupButtonObserver() {

        submitButton.setOnClickListener {

        }
    }
}
