package edu.whatcom.mywcc;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import edu.whatcom.mywcc.models.StudentProfile;

public class StudentProfilePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    TextView studentName;
    TextView dinningDollars;
    TextView bookStore;
    TextView orcaCash;
    TextView freePrints;
    TextView bonusBucks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        StudentProfile profile = new StaticBackend().getStudentProfile();
        studentName = (TextView) findViewById(R.id.student_Name);
        studentName.setText("Name: " + profile.orcaStudentName);

        freePrints = (TextView) findViewById(R.id.free_Prints);
        freePrints.setText("Free Prints: $" + profile.orcaCardFreePrints/100.0);

        dinningDollars = (TextView) findViewById(R.id.Dinning_dollars);
        dinningDollars.setText("Dinning Dollars: $" + profile.orcaCardDiningDollars/100.0);

        bookStore = (TextView)findViewById(R.id.bookstore);
        bookStore.setText("Bookstore: $" + profile.orcaCardBookstore/100.0);

        orcaCash = (TextView) findViewById(R.id.Orca_cash);
        orcaCash.setText("Orca Cash: $" + profile.orcaCash/100.0);

        bonusBucks = (TextView)findViewById(R.id.Bonus_bucks);
        bonusBucks.setText("Bonus Bucks: $" + profile.orcaCardBonusBucks/100.0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_profile_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_home_page){
            Intent intent = new Intent(StudentProfilePage.this,HomePageActivity.class);

            startActivity(intent);
        } else if (id == R.id.nav_student_profile) {
            Intent intent = new Intent(StudentProfilePage.this,StudentProfilePage.class);

            startActivity(intent);
        } else if (id == R.id.nav_WCC_map) {
            Intent intent = new Intent(StudentProfilePage.this,MapCollegePage.class);

            startActivity(intent);
        }else if (id ==R.id.nav_WCC_calendar){
            Intent intent = new Intent(StudentProfilePage.this,CurrentScheduleActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
