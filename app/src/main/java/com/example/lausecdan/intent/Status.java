package com.example.lausecdan.intent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Status extends AppCompatActivity {
    private Toolbar mToolbar;

    private EditText mTextInput;
    private Button mSaveStatus;
    private DatabaseReference mDbReference;

    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
       mToolbar = findViewById(R.id.status_toolbar);
        mTextInput = findViewById(R.id.status_input);
        mSaveStatus = findViewById(R.id.btnSaveStatus);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tâm Trạng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=mCurrentUser.getUid();
        mDbReference= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        String status_value=getIntent().getStringExtra("status_value");
        mTextInput.setText(status_value);
        mSaveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressD=new ProgressDialog(Status.this);
                mProgressD.setTitle("Lưu thay đổi");
                mProgressD.setMessage("Xin vui lòng chờ...");
                mProgressD.show();
                String status=mTextInput.getText().toString();
                mDbReference.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgressD.dismiss();
                            Intent setting=new Intent(Status.this,SettingAcount.class);
                            startActivity(setting);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Status.this,"Đã xảy ra lỗi khi lưu thay đổi, xin vui lòng kiểm tra lại.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


}
