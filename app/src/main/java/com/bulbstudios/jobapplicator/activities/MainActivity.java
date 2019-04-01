package com.bulbstudios.jobapplicator.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bulbstudios.jobapplicator.R;
import com.bulbstudios.jobapplicator.classes.JobApplication;
import com.bulbstudios.jobapplicator.interfaces.FindViews;
import com.bulbstudios.jobapplicator.viewmodels.MainViewModel;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements FindViews {

    private MainViewModel viewModel;

    private Button submitButton;
    private EditText nameText;
    private EditText emailText;
    private EditText aboutText;
    private EditText teamText;
    private EditText urlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        findViews();
        setupButtonObserver();
        setupValidationObserver();
        validateForm();
    }

    @Override
    public void findViews() {

        submitButton = findViewById(R.id.submitButton);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        aboutText = findViewById(R.id.aboutText);
        teamText = findViewById(R.id.teamText);
        urlText = findViewById(R.id.urlText);
    }

    private void setupButtonObserver() {

        submitButton.setOnClickListener((View v) -> {

            JobApplication application = viewModel.createApplication(nameText.getText().toString(),
                    emailText.getText().toString(),
                    teamText.getText().toString(),
                    aboutText.getText().toString(),
                    urlText.getText().toString());

            new Thread(() -> {

                JobApplication response = viewModel.performApplyRequest(application);

                runOnUiThread(() -> handleApplicationResponse(response));

            }).start();
        });
    }

    private void setupValidationObserver() {

        TextWatcher watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { validateForm(); }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };

        nameText.addTextChangedListener(watcher);
        emailText.addTextChangedListener(watcher);
        aboutText.addTextChangedListener(watcher);
        teamText.addTextChangedListener(watcher);
        urlText.addTextChangedListener(watcher);
    }

    private void validateForm() {

        boolean isValid = viewModel.validateApplication(nameText.getText().toString(),
                emailText.getText().toString(),
                teamText.getText().toString(),
                aboutText.getText().toString(),
                urlText.getText().toString());

        submitButton.setEnabled(isValid);
    }

    private void handleApplicationResponse(@Nullable JobApplication application) {

        @StringRes int response = R.string.application_received;

        if (application == null) {

            response = R.string.application_error;
        }

        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }
}
