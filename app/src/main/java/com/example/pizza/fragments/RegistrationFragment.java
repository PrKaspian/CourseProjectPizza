package com.example.pizza.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pizza.PersonalAccountActivity;
import com.example.pizza.R;
import com.example.pizza.entity.User;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment{
    public interface OnRegistration{
        void registration(boolean val);
    }
    OnRegistration onRegistration;
    private EditText name;
    private EditText phone;
    private EditText password;
    private EditText repeatPassword;
    private EditText date;
    private Button btnSaveUser;

    public RegistrationFragment() {
      super(R.layout.fragment_registration);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.etName);
        phone = view.findViewById(R.id.etPhone);
        password = view.findViewById(R.id.etPassword);
        repeatPassword = view.findViewById(R.id.etRepeatPassword);
        date = view.findViewById(R.id.etDate);
        btnSaveUser = view.findViewById(R.id.btnSaveUser);

        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty() &&
                        !phone.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty() &&
                        !repeatPassword.getText().toString().isEmpty()){
                    if (password.getText().toString().equals(repeatPassword.getText().toString())){
                        User user = new User(name.getText().toString(),
                                phone.getText().toString(),
                                password.getText().toString(),
                                false);
                        UserApi userApi = NetworkService.getInstance().getUserApi();
                        userApi.saveUser(user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                onRegistration.registration(true);
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onRegistration = (OnRegistration) context;
    }
}