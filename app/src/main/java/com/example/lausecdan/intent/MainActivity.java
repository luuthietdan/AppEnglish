package com.example.lausecdan.intent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mbottomNav;
    private FrameLayout mframe;
    private  Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbottomNav=findViewById(R.id.layout_nav);

        mframe=findViewById(R.id.frame_layout);
        mToolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("English");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.learning:
                        HomeFragment homeFragment=new HomeFragment();
                        setFragment(homeFragment);
                        return true;
                    case R.id.requests:
                        RequestsFragment requestsFragment=new RequestsFragment();
                        setFragment(requestsFragment);
                        return true;
                    case R.id.chats:
                        ChatsFragment chatsFragment=new ChatsFragment();
                        setFragment(chatsFragment);
                        return true;
                    case R.id.friends:
                        FriendsFragment friendsFragment=new FriendsFragment();
                        setFragment(friendsFragment);
                        return true;
                        default:
                            return false;
                }

            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_english, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.signin)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new SignIn()).commit();
        }
        if(id==R.id.signup){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new SignUp()).commit();
        }
        if (id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
        }
        if(id==R.id.accountsetting){
            Intent intent= new Intent(MainActivity.this,SettingAcount.class);
            startActivity(intent);
        }
        if(id==R.id.allusers){
            Intent intent= new Intent(MainActivity.this,UsersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
