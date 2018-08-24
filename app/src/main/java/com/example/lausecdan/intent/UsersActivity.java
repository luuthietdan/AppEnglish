package com.example.lausecdan.intent;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.lausecdan.intent.Model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRVUsers;
    private DatabaseReference mUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mToolbar= findViewById(R.id.users_toolbar);
        mRVUsers=findViewById(R.id.rvUsers);
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRVUsers.setHasFixedSize(true);
        mRVUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users,UserViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UserViewHolder>(
                Users.class,
                R.layout.users_layout,
                UserViewHolder.class,
                mUserDatabase
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, Users model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getImage(),getApplicationContext());
                final String user_id=getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profile=new Intent(UsersActivity.this, ProfileActivity.class);
                        profile.putExtra("user_id",user_id);
                        startActivity(profile);
                    }
                });
            }
       };
        mRVUsers.setAdapter(firebaseRecyclerAdapter);
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setName(String name){
            TextView txtName=itemView.findViewById(R.id.txtName);
            txtName.setText(name);
        }
        public void setStatus(String status){
            TextView txtStatus=itemView.findViewById(R.id.txtDisplayStatus);
            txtStatus.setText(status);
        }
        public void setImage(String image, Context ctx){
            CircleImageView circleImageView=itemView.findViewById(R.id.civUser);
            Picasso.with(ctx).load(image).placeholder(R.drawable.defaultimage).into(circleImageView);
        }
    }
}
