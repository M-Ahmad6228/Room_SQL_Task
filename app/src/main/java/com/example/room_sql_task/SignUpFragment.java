package com.example.room_sql_task;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignUpFragment extends Fragment {

    ProgressBar progressBar;
    TextInputEditText fullName, emailAddress, age, password;
    TextInputLayout fullNameLayout, emailAddressLayout, ageLayout, passwordLayout;
    TextView textView;
    Button btn_signup;
    RadioGroup gender;
    boolean isErrorFree = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        initializeViews(view);
        editTextListeners(view);

        return view;
    }

    private void editTextListeners(View view) {
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(fullName.length() > fullNameLayout.getCounterMaxLength()) {
                    fullNameLayout.setError("Limit Exceeded");
                    isErrorFree = false;
                }
                else if(fullName.getText().toString().equals("")) {
                    fullNameLayout.setError("");
                    isErrorFree = true;
                }
                else {
                    fullNameLayout.setError("");
                    isErrorFree = true;
                }
            }
        });
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(emailAddress.length() > emailAddressLayout.getCounterMaxLength()) {
                    emailAddressLayout.setError("Limit Exceeded");
                    isErrorFree = false;
                }
                else if(!emailAddress.getText().toString().equals("")) {
                    if (!emailAddress.getText().toString().matches(getString(R.string.emailPattern))) {
                        emailAddressLayout.setError("Invalid Email Pattern");
                        isErrorFree = false;
                    }
                    else {
                        emailAddressLayout.setError("");
                        isErrorFree = true;
                    }
                }
                else {
                    emailAddressLayout.setError("");
                    isErrorFree = true;
                }
            }
        });
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(age.length() > ageLayout.getCounterMaxLength()) {
                    ageLayout.setError("Limit Exceeded");
                    isErrorFree = false;
                }
                else if(age.getText().toString().equals("")) {
                    ageLayout.setError("");
                    isErrorFree = true;
                }
                else {
                    ageLayout.setError("");
                    isErrorFree = true;
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0) {
                    passwordLayout.setError("");
                    isErrorFree = true;
                }
                else if(s.length() > passwordLayout.getCounterMaxLength()) {
                    passwordLayout.setError("Limit Exceeded");
                    isErrorFree = false;
                }
                else {
                    if (!s.toString().matches(getString(R.string.passwordPattern))) {
                        passwordLayout.setError("First Character Should be Capital Alphabet");
                        isErrorFree = false;
                    }
                    else {
                        passwordLayout.setError("");
                        isErrorFree = true;
                    }
                }
            }
        });
        textView.setOnClickListener(v -> {
            goingToLogin();
        });
        btn_signup.setOnClickListener(v -> {
            if(validateFields()){
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.GONE);
                    Employee employee = new Employee(fullName.getText().toString(), emailAddress.getText().toString(), password.getText().toString(),
                            ((RadioButton)view.findViewById(gender.getCheckedRadioButtonId())).getText().toString(),
                            Integer.parseInt(age.getText().toString()), "");
                    LoginFragment.db.employeeDAO().insertAll(employee);
                    goingToLogin();
                }, 2000);
            }
            else{
                Toast.makeText(getContext(), "Unable to Sign Up", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeViews(View view) {
        progressBar = view.findViewById(R.id.progressBar1);
        fullName = view.findViewById(R.id.txt_fullName);
        emailAddress = view.findViewById(R.id.txt_email);
        age = view.findViewById(R.id.txt_age);
        password = view.findViewById(R.id.txt_password);
        gender = view.findViewById(R.id.gender);
        fullNameLayout = view.findViewById(R.id.txtLayout1);
        emailAddressLayout = view.findViewById(R.id.txtLayout2);
        ageLayout = view.findViewById(R.id.txtLayout3);
        passwordLayout = view.findViewById(R.id.txtLayout4);
        textView = view.findViewById(R.id.text2);
        btn_signup = view.findViewById(R.id.btn_signUp);
    }

    public boolean validateFields(){
        if(isErrorFree) {
            if (fullName.length() == 0) {
                fullNameLayout.setError("Field Missing");
                return false;
            }
            if (emailAddress.length() == 0) {
                emailAddressLayout.setError("Field Missing");
                return false;
            }
            if (age.length() == 0) {
                ageLayout.setError("Field Missing");
                return false;
            }
            if (password.length() == 0) {
                passwordLayout.setError("Field Missing");
                return false;
            }
            return true;
        }
        return false;
    }

    public void goingToLogin(){
        getActivity().onBackPressed();
    }
}
