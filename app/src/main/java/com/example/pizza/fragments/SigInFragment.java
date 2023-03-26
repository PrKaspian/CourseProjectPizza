package com.example.pizza.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizza.MainActivity;
import com.example.pizza.PersonalAccountActivity;
import com.example.pizza.R;
import com.example.pizza.entity.User;
import com.example.pizza.network.NetworkService;
import com.example.pizza.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigInFragment extends Fragment{
    public interface OnFragmentClickButtonListener{
        void onClickListener(Button button);
    }
    private OnFragmentClickButtonListener fragmentClickButtonListener;
    private UserApi userApi;
    private User user;
    private EditText etLogin;
    private EditText etPassword;
    private Button btnSigIn;
    public Button btnRegistration;

    public SigInFragment() {
       super(R.layout.fragment_sig_in);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etLogin = view.findViewById(R.id.etLogin);
        etPassword = view.findViewById(R.id.etPassword);
        btnSigIn = view.findViewById(R.id.btnSigIn);
        btnRegistration = view.findViewById((R.id.btnRegistration));
        userApi = NetworkService.getInstance().getUserApi();

        btnSigIn.setOnClickListener(view1 -> {
            String login = etLogin.getText().toString();
            String password = etPassword.getText().toString();
            if (!login.isEmpty() && !password.isEmpty()){
                userApi.getUserByPhone(login).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        if (user.getPassword().equals(password)){
                            MainActivity.USER = user;
                            fragmentClickButtonListener.onClickListener(btnSigIn);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(getContext(), "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistration.setOnClickListener(view12 -> {
            fragmentClickButtonListener.onClickListener(btnRegistration);
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentClickButtonListener = (OnFragmentClickButtonListener) context;
    }
}