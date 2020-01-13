package com.example.room_sql_task;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class LoginFragment extends Fragment {

    CallBackFragment callBackFragment;
    ProgressBar progressBar;
    TextInputEditText emailAddress, password;
    TextInputLayout emailLayout, passwordLayout;
    TextView textView;
    Button btn_login, btn_list;
    public static AppDatabse db;
    List<Employee> employees;
    public static LoginFragment _this;

    public Employee getUserEmployee() {
        return userEmployee;
    }

    public void setUserEmployee(Employee userEmployee) {
        this.userEmployee = userEmployee;
    }

    private Employee userEmployee;
    private boolean isErrorFree = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        _this = this;
        initializeViews(view);
        editTextListeners();
        checkLoginStatus();

        return view;
    }

    public void setCallBackFragment(CallBackFragment callBackFragment){
        this.callBackFragment = callBackFragment;
    }

    private void checkLoginStatus() {
        if(PreferenceData.getUserLoggedInStatus(getActivity())){
            this.userEmployee = db.employeeDAO().getEmployeeWithEmail(PreferenceData.getLoggedInEmailUser(getActivity()));
            Intent intent = new Intent(getActivity(), EmployeeActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    private void editTextListeners(){
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(emailAddress.length() > emailLayout.getCounterMaxLength()) {
                    emailLayout.setError("Limit Exceeded");
                    isErrorFree = false;
                }
                else if(!emailAddress.getText().toString().equals("")) {
                    if (!emailAddress.getText().toString().matches(getString(R.string.emailPattern))) {
                        emailLayout.setError("Invalid Email Pattern");
                        isErrorFree = false;
                    }
                    else {
                        emailLayout.setError("");
                        isErrorFree = true;
                    }
                }
                else {
                    emailLayout.setError("");
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
            if(callBackFragment != null)
                callBackFragment.changeFragment(0);
        });
        btn_login.setOnClickListener(v -> {
            if(validateFields() && processLogin()) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), EmployeeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

                }, 2000);
            }
            else
                Toast.makeText(getContext(), "Employee Not Found", Toast.LENGTH_LONG).show();
        });
        btn_list.setOnClickListener(v -> {
            if(callBackFragment != null)
                callBackFragment.changeFragment(1);
        });
    }

    private void initializeViews(View view) {
        db = Room.databaseBuilder(getContext(), AppDatabse.class, "employee")
                .allowMainThreadQueries()
                .build();
        employees = db.employeeDAO().getAllEmployees();
        progressBar = view.findViewById(R.id.progressBar);
        emailAddress = view.findViewById(R.id.txt_loginEmail);
        password = view.findViewById(R.id.txt_loginPassword);
        emailLayout = view.findViewById(R.id.email);
        passwordLayout = view.findViewById(R.id.password);
        textView = view.findViewById(R.id.text2);
        btn_login = view.findViewById(R.id.btn_logIn);
        btn_list = view.findViewById(R.id.btn_list);
    }

    private boolean validateFields() {
        if(isErrorFree){
            if(emailAddress.getText().toString().equals("")){
                emailLayout.setError("Field Missing");
                return false;
            }
            if(password.getText().toString().equals("")){
                passwordLayout.setError("Field Missing");
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean processLogin() {
        for(Employee employee: employees){
            if(employee.getEmailAddress().equals(emailAddress.getText().toString()) && employee.getPassword().equals(password.getText().toString())){
                setUserEmployee(employee);
                PreferenceData.setLoggedInUserEmail(getContext(), employee.getEmailAddress());
                PreferenceData.setUserLoggedInStatus(getContext(), true);
                return true;
            }
        }
        return false;
    }

}
