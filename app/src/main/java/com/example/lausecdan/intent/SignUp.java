package com.example.lausecdan.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends Fragment {
    private EditText medtEmail,medtPassword,medtName;
    private Button mbtnSignUp;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.sign_up,container,false);
        medtEmail = view.findViewById(R.id.edtemail);
        medtPassword = view.findViewById(R.id.edtpassword);
        medtName = view.findViewById(R.id.edtname);
        progressBar = view.findViewById(R.id.progressBar);
        mbtnSignUp=view.findViewById(R.id.btnsignup);
        auth = FirebaseAuth.getInstance();
        mbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = medtName.getText().toString().trim();
                String email = medtEmail.getText().toString().trim();
                String password = medtPassword.getText().toString().trim();
                register_user(name,email,password);
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "Nhập họ tên!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Nhập Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getActivity(), "Mật khẩu quá ngắn, giới hạn từ 6 kí tự trở lên!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


            }
        });
        return view;
    }

    private void register_user(final String name, String email, String password) {
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Lỗi đăng ký. Xin vui lòng kiểm tra lại Email" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            String mId=firebaseUser.getUid();
                            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(mId);
                            HashMap<String, String> userMap=new HashMap<>();
                            userMap.put("name",name);
                            userMap.put("status","Hi all, My name is Dan");
                            userMap.put("image","default");
                            userMap.put("thump_image","default");
                            mDatabaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                    Intent register=new Intent(getActivity(),MainActivity.class);
                                    register.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(register);
                                    }
                                }
                            });



                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Sign Up");
    }


}
