package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

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


    }
    //자동로그인
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Toast.makeText(MainActivity.this, "자동로그인"+user.getUid(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_find:  //비번초기화
                break;

            case R.id.sign_logout:   //로그아웃
                FirebaseAuth.getInstance().signOut();
                break;

            case R.id.login_signup:   //회원가입
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

                                // ...
                            }
                        });


                break;
        }
    }

    private void startActivities(Intent intent) {
    }
}