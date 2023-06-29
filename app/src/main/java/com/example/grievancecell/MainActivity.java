package com.example.grievancecell;

import static com.example.grievancecell.R.*;
import static com.example.grievancecell.R.id.navView;
import static com.example.grievancecell.R.id.signIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.grievancecell.Fragments.ComplaintsFragment;
import com.example.grievancecell.Fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    String name,mobNum;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        drawerLayout=findViewById(id.drawerLayout);
        navigationView=findViewById(navView);
        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name= getIntent().getStringExtra("name");
        mobNum=getIntent().getStringExtra("mobNum");

        setDefaultFragment();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setDefaultFragment() {
        HomeFragment defaultFragment=new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        bundle.putString("mobNum",mobNum);
        defaultFragment.setArguments(bundle);
        fragmentTransaction.add(id.linearLayout,defaultFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Fragment currentFragment=null;
        switch (item.getItemId()){
            case id.complaints:
                currentFragment=new ComplaintsFragment();
                break;
            case id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                finish();
                break;
            default:
                currentFragment=new HomeFragment();
        }
        if(currentFragment!=null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(id.linearLayout, currentFragment);
            transaction.commit();
        }
        return true;
    }

}