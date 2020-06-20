package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);

        findViewById(R.id.login_signup).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.login_success).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.sign_logout).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.sign_find).setOnClickListener((View.OnClickListener) this);
    }





    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_find:  //resetpassword
                Toast.makeText(MainActivity.this, "찾기으로 이동",
                        Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                startActivity(intent2);

                break;

            case R.id.sign_logout:   //logout
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "로그아웃 성공",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.login_signup:   //Member
                Toast.makeText(MainActivity.this, "회원가입으로 이동",
                        Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), Member.class);
                startActivity(intent1);
                break;

            case R.id.login_success: //로그인 성공
                mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user!=null) {
                                        Toast.makeText(MainActivity.this, "로그인 성공",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ExhibitionMenu.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "로그인 실패",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;


        }
    }

    private void startActivities(Intent intent) {
    }
}