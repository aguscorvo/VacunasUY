package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import vacunasuy.componentemovil.obj.Usuario;

public class VacunMapActivity extends AppCompatActivity {

    MapView map;
    BottomNavigationView bottomNavigationView;
    private MapController mc;
    private LocationManager locationManager;
    private Location location;
    private LocationListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_vacun_map);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        mc = (MapController) map.getController();
        mc.setZoom(20);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(gpsEnabled){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, (LocationListener) this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
                mc.animateTo(center);

                Marker marker = new Marker(map);
                marker.setPosition(center);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(getString(R.string.map_text_loc));
                addMarker(marker);

            }
        }else{
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle(R.string.map_text_loc_err);
            dialog.setMessage(getString(R.string.map_text_loc_err_msg));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(settingsIntent);
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    VacunMapActivity.super.onBackPressed();
                }
            });
            dialog.show();

        }

        bottomNavigationView = findViewById(R.id.bottomNavigationViewMap);
        bottomNavigationView.setSelectedItemId(R.id.menu_vacunatorio);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent ihome = new Intent(VacunMapActivity.this, MainActivity.class);
                        startActivity(ihome);
                        return true;
                    case R.id.menu_agenda:
                        Toast.makeText(VacunMapActivity.this, "Opción Agenda", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_notificacion:
                        Toast.makeText(VacunMapActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_vacunatorio:
                        //Toast.makeText(VacunMapActivity.this, "Opción Vacunatorio", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_usuario:
                        Toast.makeText(VacunMapActivity.this, "Opción Usuario", Toast.LENGTH_SHORT).show();
                        Usuario usuario = Usuario.getInstance();
                        if (usuario.getRegistrado()) {

                        }

                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListenerGPS);
    }

    public void addMarker (Marker marker){
        map.getOverlays().clear();
        map.getOverlays().add(marker);
        map.invalidate();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    /*
    public void addMarker (GeoPoint center){
        Marker marker = new Marker(map);
        marker.setPosition(center);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //marker.setIcon(ResourcesCompat.getDrawable( getResources(), R.drawable.ic_building, null));
        map.getOverlays().clear();
        map.getOverlays().add(marker);
        map.invalidate();
        marker.setTitle("Casa do Josileudo");
    }
    */

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location loc) {
            location = loc;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VacunMapActivity.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                    GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
                    mc.animateTo(center);

                    Marker marker = new Marker(map);
                    marker.setPosition(center);
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setTitle(getString(R.string.map_text_loc));
                    addMarker(marker);
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };
}