package org.techtown.androidteamproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Member extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private Button mBtn;
    private EditText mEmailText, mPasswordText;
    private int ival;
    public String glob="";
    static SharedPreferences sPref;
    private SharedPreferences.Editor sE;
    RadioGroup rbMain;
    RadioButton rb1,rb2,rb3,rb4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_register);
        mBtn = findViewById(R.id.member_btn);
        mEmailText = findViewById(R.id.Email);
        mPasswordText = findViewById(R.id.Password);
        rbMain = (RadioGroup) findViewById(R.id.radiogr);
        rb1 = (RadioButton)findViewById(R.id.radiobtn1);
        rb2 = (RadioButton)findViewById(R.id.radiobtn2);
        rb3 = (RadioButton)findViewById(R.id.radiobtn3);
        rb4 = (RadioButton)findViewById(R.id.radiobtn4);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(mEmailText.getText().toString(),
                        mPasswordText.getText().toString())
                        .addOnCompleteListener(Member.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.i(this.getClass().getName(),"onComplete실행");
                                if (task.isSuccessful()) {//User정보를 넣어줘야하니까
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(getApplicationContext(), ExhibitionMenu.class);
                                    startActivity(intent);
                                    if (user != null) {
                                        Map<String, Object> userMap = new HashMap<>();
                                        userMap.put(FirebaseID.documentId, user.getUid()); //유저아이디
                                        userMap.put(FirebaseID.email, mEmailText.getText().toString());  //유저이메일
                                        userMap.put(FirebaseID.password, mPasswordText.getText().toString()); //유저패스워드
                                        userMap.put(FirebaseID.genre,glob); //유저장르genre
                                        mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(Member.this, "Sign Up Error",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ExhibitionMenu.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        sPref = getSharedPreferences("Pref",0);
        sE = sPref.edit();
        ival = sPref.getInt("Pref", 0);

        if(ival == R.id.radiobtn1){
            rb1.setChecked(true);
            glob="설치";
        }else if(ival == R.id.radiobtn2){
            rb2.setChecked(true);
            glob="회화";
        }else if(ival == R.id.radiobtn3){
            rb3.setChecked(true);
            glob="사진";
        }else if(ival == R.id.radiobtn4){
            rb4.setChecked(true);
            glob="현대";
        }

        rbMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            int state = 0;
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radiobtn1){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radiobtn2){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radiobtn3){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radiobtn4){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }

            }
        });

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        sPref = getSharedPreferences("Pref",0);
        sE = sPref.edit();
        ival = sPref.getInt("Pref", 0);

        if(ival == R.id.radiobtn1){
            rb1.setChecked(true);
            glob="설치";
        }else if(ival == R.id.radiobtn2){
            rb2.setChecked(true);
            glob="회화";
        }else if(ival == R.id.radiobtn3){
            rb3.setChecked(true);
            glob="사진";
        }else if(ival == R.id.radiobtn4){
            rb4.setChecked(true);
            glob="현대";
        }

    }
}
