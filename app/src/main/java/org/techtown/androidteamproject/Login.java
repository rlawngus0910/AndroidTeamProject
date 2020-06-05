package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);

        findViewById(R.id.login_signup).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.login_success).setOnClickListener((View.OnClickListener) this);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_success: //로그인 성공
                Intent intent = new Intent(getApplicationContext(), ExhibitionMenu.class);
                startActivity(intent);
                break;
            case R.id.login_signup:   //회원가입
                startActivities(new Intent(this,Member.class));
                break;
        }

    }

    private void startActivities(Intent intent) {
    }
}