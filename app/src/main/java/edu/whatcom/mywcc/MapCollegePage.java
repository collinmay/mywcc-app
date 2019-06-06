package edu.whatcom.mywcc;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions;
import com.mapbox.mapboxsdk.plugins.annotation.LineManager;
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions;
import com.mapbox.mapboxsdk.utils.ColorUtils;

import androidx.drawerlayout.widget.DrawerLayout;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.whatcom.mywcc.models.Building;
import edu.whatcom.mywcc.models.path.CampusMap;
import edu.whatcom.mywcc.models.path.PathNode;

import android.view.Menu;

import java.util.Arrays;

public class MapCollegePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CampusMap mapModel = CampusMap.createWCCCampusMap();
    private MapView mapView;
    private NavigationDialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_college_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(m -> m.setStyle(Style.SATELLITE_STREETS, style -> {
            LineManager edgeManager = new LineManager(mapView, m, style);
            CircleManager nodeManager = new CircleManager(mapView, m, style);

            for(PathNode n : mapModel.nodes) {
                CircleOptions opt = new CircleOptions()
                        .withLatLng(n)
                        .withCircleColor(ColorUtils.colorToRgbaString(Color.BLUE))
                        .withCircleRadius(5f);
                nodeManager.create(opt);

                for(PathNode a : n.outgoing) {
                    LineOptions lopt = new LineOptions()
                            .withLatLngs(Arrays.asList(n, a));
                    edgeManager.create(lopt);
                }
            }
        }));

        FloatingActionButton fab = findViewById(R.id.nav_fab);
        fab.setOnClickListener((view) -> {
            dialog = new NavigationDialogFragment(this::findPath, mapModel);
            dialog.show(getSupportFragmentManager(), "navigation");

        });
    }

    private void findPath(Building from, Building to) {
        dialog.dismiss();
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
        getMenuInflater().inflate(R.menu.map_college_page, menu);
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
            Intent intent = new Intent(MapCollegePage.this, HomePageActivity.class);

            startActivity(intent);
        } else if (id == R.id.nav_student_profile) {
            Intent intent = new Intent(MapCollegePage.this,StudentProfilePage.class);

            startActivity(intent);
        } else if (id == R.id.nav_WCC_map) {
            Intent intent = new Intent(MapCollegePage.this,MapCollegePage.class);

            startActivity(intent);
        }else if (id ==R.id.nav_WCC_calendar){
            Intent intent = new Intent(MapCollegePage.this,CalendarPage.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // mapbox needs these
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
