package com.example.grievancecell;

import static com.example.grievancecell.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.grievancecell.Fragments.ComplaintsFragment;
import com.example.grievancecell.Fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

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
        navigationView= findViewById(id.navView);
        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.close,R.string.open);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name= getIntent().getStringExtra("name");
        mobNum=getIntent().getStringExtra("mobNum");

        setDefaultFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Fragment currentFragment=null;
                switch (item.getItemId()){
                    case R.id.home:
                        currentFragment=new HomeFragment();
                    case R.id.complaints:
                        currentFragment=new ComplaintsFragment();
                    case id.logout:
                        FirebaseAuth.getInstance().signOut();
                }
                if(currentFragment!=null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(id.drawerLayout, currentFragment);
                    transaction.commit();
                }
                return true;
            }
        });
    }

    private void setDefaultFragment() {
        HomeFragment defaultFragment=new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        bundle.putString("mobNum",mobNum);
        defaultFragment.setArguments(bundle);
        fragmentTransaction.add(id.drawerLayout,defaultFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

}