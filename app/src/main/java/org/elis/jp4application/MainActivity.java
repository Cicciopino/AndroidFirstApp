package org.elis.jp4application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnCheckedChangeListener {

    private static final String TAG = "MainActivity";
    private static final int PASSWORD_LENGTH = 6;

    EditText emailET;
    EditText passwordET;

    Button loginBtn;
    Button registerBtn;

    Switch switchTheme;

    LinearLayout layout;

    SharedPreferences sharePref;
    SharedPreferences.Editor editor;

    public static final String WELCOME ="WELCOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.email_et);
        passwordET = findViewById(R.id.password_et);

        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setVisibility(View.VISIBLE);

        registerBtn.setOnClickListener(this);

        loginBtn.setOnClickListener(this);

        switchTheme = findViewById(R.id.switch1);
        switchTheme.setOnCheckedChangeListener(this);

        layout = findViewById(R.id.back);

        sharePref = getPreferences(Context.MODE_PRIVATE);
        editor = sharePref.edit();
        switchTheme.setChecked(getColorValueFromMemory());



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(i);
            }
        });
        Log.i(TAG, "activity created");

    }

    private boolean isValidEmail(){
        String email = emailET.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private boolean isValidPassword(){
        String password = passwordET.getText().toString();
        return (password.length() > PASSWORD_LENGTH);
    }


    private void showErrorMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

        Log.e(TAG,message);
    }



    private void showSuccessMessage(){

        Toast.makeText(this,getString(R.string.login_success),Toast.LENGTH_LONG)
                .show();
        Log.i(TAG,getString(R.string.login_success));
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "activity started");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "activity RESUMED");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "activity PAUSED");
        }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "activity STOPPED");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "activity DESTROYED");

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_btn){


            if(!isValidEmail()){
                showErrorMessage(getString(R.string.email_error));
                return;
            }
            if(!isValidPassword()){
                showErrorMessage(getString(R.string.password_error));
                return;
            }

            showSuccessMessage();
            Intent i = new Intent(this,WelcomeActivity.class);
            String mail = emailET.getText().toString();
            i.putExtra(WELCOME,mail);
            startActivity(i);


        }else if(view.getId() == R.id.register_btn){

            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        }

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    changeColor(isChecked);
    }
    private void changeColor(boolean isChecked){
        layout.setBackgroundColor(getResources().getColor(isChecked ? R.color.colorPrimaryDark : R.color.white));
        setColorValueInMemory(isChecked);
    }
    private void setColorValueInMemory(boolean value){
        editor.putBoolean("Background_color",value);
        editor.commit();
    }
    private boolean getColorValueFromMemory(){
      return sharePref.getBoolean("BGcolor",false);
    }
}

