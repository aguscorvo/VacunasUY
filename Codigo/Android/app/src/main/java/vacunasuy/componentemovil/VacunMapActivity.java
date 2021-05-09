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
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vacunasuy.componentemovil.constant.MapConstant;
import vacunasuy.componentemovil.obj.DtResponse;
import vacunasuy.componentemovil.obj.DtUsuario;

public class VacunMapActivity extends AppCompatActivity implements  LocationListener{

    MapView map;
    BottomNavigationView bottomNavigationView;
    ImageButton imlocation;
    private MapController mc;
    private LocationManager locationManager;
    private Location location;
    private GeoPoint center;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_vacun_map);

        imlocation = findViewById(R.id.imageButtonLocation);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewMap);
        progressBar = findViewById(R.id.progressBarMapView);
        map = findViewById(R.id.map);

        progressBar.setVisibility(View.INVISIBLE);

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.setMaxZoomLevel(MapConstant.MAP_ZOOM_MAX);
        map.setMinZoomLevel(MapConstant.MAP_ZOOM_MIN);

        mc = (MapController) map.getController();
        mc.setZoom(MapConstant.MAP_ZOOM);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(gpsEnabled){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MapConstant.MAP_TIME_MS, MapConstant.MAP_DISTANCE_M, (LocationListener) this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                center = new GeoPoint(location.getLatitude(), location.getLongitude());
                mc.setCenter(center);
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
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.alert_btn_positive), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(settingsIntent);
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.alert_btn_negative), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    VacunMapActivity.super.onBackPressed();
                }
            });
            dialog.show();

        }

        imlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mc.setZoom(MapConstant.MAP_ZOOM);
                mc.animateTo(center);
            }
        });

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
                        return true;
                    case R.id.menu_usuario:
                        DtUsuario usuario = DtUsuario.getInstance();
                        if(usuario.getRegistrado()){
                            Intent userinfo = new Intent(VacunMapActivity.this, UserInfoActivity.class);
                            startActivity(userinfo);

                        }else{
                            Intent userlogin = new Intent(VacunMapActivity.this, GubUyActivity.class);
                            startActivity(userlogin);
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        center = new GeoPoint(location.getLatitude(), location.getLongitude());
        mc.animateTo(center);

        Marker marker = new Marker(map);
        marker.setPosition(center);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(getString(R.string.map_text_loc));
        addMarker(marker);
    }

    private class LoginUserTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return VacunatoriosInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);

            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {

            if (result instanceof ArrayList) {
                DtResponse res = (DtResponse) result;
            }else if(result instanceof String){
                Log.i("onPostExecute", "response: " + ((String) result));

            }
        }

    }

    private DtResponse VacunatoriosInfoGralUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            return readInfoGralJsonStream(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public DtResponse readInfoGralJsonStream(InputStream in) throws IOException {
        //creating an InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(in);
        //Creating a BufferedReader object
        BufferedReader breader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = breader.readLine())!= null){
            sb.append(str);
        }
        JsonReader reader = new JsonReader(new StringReader(sb.toString()));
        List<DtResponse> res = null;
        try {
            return readRESTMessage(reader);
        } finally {
            reader.close();
        }
    }

    public DtResponse readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        Object res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                //setDtUsuario(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtResponse(ok, mensaje);
    }



}