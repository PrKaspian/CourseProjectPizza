package com.example.pizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.pizza.fragments.RegistrationFragment;
import com.example.pizza.fragments.SigInFragment;
import com.example.pizza.fragments.UserCabinetFragment;


public class PersonalAccountActivity extends AppCompatActivity implements SigInFragment.OnFragmentClickButtonListener, RegistrationFragment.OnRegistration {

    private SigInFragment sigInFragment;
    private RegistrationFragment registrationFragment;
    private UserCabinetFragment userCabinetFragment;
    private FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);
        sigInFragment = new SigInFragment();
        registrationFragment = new RegistrationFragment();
        userCabinetFragment = new UserCabinetFragment();
        flContainer = findViewById(R.id.flContainer);
        if (MainActivity.USER == null) {
           showFragmentSigIn();
        } else {
            showFragmentUserCabinet();
        }


    }

    public void showFragmentRegistration() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, registrationFragment)
                .commit();
    }

    private void showFragmentSigIn() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, sigInFragment)
                .commit();
    }

    private void showFragmentUserCabinet() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, userCabinetFragment)
                .commit();
    }


    @Override
    public void onClickListener(Button button) {
        if (button.getId() == R.id.btnSigIn){
            showFragmentUserCabinet();
        }else if (button.getId() == R.id.btnRegistration) {
            showFragmentRegistration();
        }
    }

    @Override
    public void registration(boolean val) {
        if (val) {
            showFragmentSigIn();
            Toast.makeText(this, "Спасибо за регистрацию", Toast.LENGTH_SHORT).show();
        }
    }
}