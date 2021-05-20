package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.constant.MapConstant;
import vacunasuy.componentemovil.obj.DtAgenda;
import vacunasuy.componentemovil.obj.DtPuesto;
import vacunasuy.componentemovil.obj.DtUbicacion;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.obj.DtVacunatorio;
import vacunasuy.componentemovil.second.AddFechaNacimiento;
import vacunasuy.componentemovil.second.MapDepto;
import vacunasuy.componentemovil.second.MapDeptoLoc;

public class VacunMapActivity extends AppCompatActivity implements  LocationListener{
    private static final String TAG = "VacunasUY";
    private static final int DEPARTAMENTO = 1;
    private static final int LOCALIDAD = 2;
    private static final int DISTANCIA = 3;
    private static final int TODOS = 4;
    final static Integer CODE = 2021;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    MapView map;
    BottomNavigationView bottomNavigationView;
    ImageButton imlocation;
    private MapController mc;
    private LocationManager locationManager;
    private Location location;
    private GeoPoint center;
    private GeoPoint findCenter = null;
    ProgressBar progressBar;
    String[] opcionesfilter;
    Spinner filtermap;
    Boolean filtradodeptoLoc = false;
    EditText distancia;



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
        filtermap = findViewById(R.id.spinnerFilterMap);


        progressBar.setVisibility(View.INVISIBLE);
        opcionesfilter = getResources().getStringArray(R.array.mapopciones);
        ArrayAdapter<String> aaop = new ArrayAdapter<String>(this, R.layout.map_spinner, opcionesfilter);
        filtermap.setAdapter(aaop);

        filtermap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent select_filter;
                Log.i(TAG, "onItemSelected position: " + position);
                switch (position) {
                    case DEPARTAMENTO:
                        select_filter = new Intent(VacunMapActivity.this, MapDepto.class);
                        startActivityForResult(select_filter, CODE+DEPARTAMENTO);
                        break;
                    case LOCALIDAD:
                        select_filter = new Intent(VacunMapActivity.this, MapDeptoLoc.class);
                        startActivityForResult(select_filter, CODE+LOCALIDAD);
                        break;
                    case DISTANCIA:
                        AlertDialog dialog = new AlertDialog.Builder(VacunMapActivity.this).create();
                        dialog.setTitle(R.string.map_text_distancia_title);

                        // set the custom layout
                        final View customLayout = getLayoutInflater().inflate(R.layout.map_distancia,null);
                        dialog.setView(customLayout);
                        distancia = customLayout.findViewById(R.id.map_distancia);

                        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.map_text_btn), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(VacunMapActivity.this, distancia.getText().toString(), Toast.LENGTH_LONG).show();
                                if (distancia.getText().toString().equalsIgnoreCase("")){
                                    buscarVacunatoriosDistancia(0.0);
                                } else{
                                    buscarVacunatoriosDistancia(Double.valueOf(distancia.getText().toString()));
                                }

                            }
                        });
                        dialog.show();
                        break;
                    case TODOS:
                        buscarVacunatorios();
                        break;

                }

                filtermap.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        map = findViewById(R.id.map);

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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MapConstant.MAP_TIME_MS, MapConstant.MAP_DISTANCE_M, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                center = new GeoPoint(location.getLatitude(), location.getLongitude());
                mc.setCenter(center);
                mc.animateTo(center);

                Marker marker = new Marker(map);
                marker.setPosition(center);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(getString(R.string.map_text_loc));
                addMarker(marker);
                buscarVacunatorios();
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
                        Intent iagenda = new Intent(VacunMapActivity.this, PlanVacunacion.class);
                        startActivity(iagenda);

                        return true;
                    case R.id.menu_notificacion:
                        Toast.makeText(VacunMapActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_vacunatorio:
                        return true;
                    case R.id.menu_usuario:
                        DtUsuario usuario = DtUsuario.getInstance();
                        if(usuario.getRegistrado()){
                            if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                                Intent fnintent = new Intent(VacunMapActivity.this, AddFechaNacimiento.class);
                                startActivity(fnintent);
                            }else {
                                Intent userinfo = new Intent(VacunMapActivity.this, UserInfoActivity.class);
                                startActivity(userinfo);
                            }
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void addMarker (Marker marker){
        map.getOverlays().add(marker);
    }

    private void addVacunatorios(List<DtVacunatorio> vacunatorios){
        for(final DtVacunatorio dtv : vacunatorios){
            GeoPoint vpoint = new GeoPoint(dtv.getLatitud(), dtv.getLongitud());
            Marker vmarker = new Marker(map);
            vmarker.setPosition(vpoint);
            vmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            vmarker.setIcon(ResourcesCompat.getDrawable( getResources(), R.drawable.ic_building_map, null));
            String title = "Nombre: " + dtv.getNombre() + "\n";
            title = title + "Departamento: " + dtv.getUbicacion().getNombre_departamento() + "\n";
            title = title + "Localidad: " + dtv.getUbicacion().getNombre_localidad() + "\n";
            title = title + "Dirección: " + dtv.getUbicacion().getDireccion() + "\n";
            title = title + "Cantidad de puestos: " + (dtv.getPuestos()==null?0:dtv.getPuestos().size()) + "\n";
            vmarker.setTitle(title);
            addMarker(vmarker);

            findCenter = new GeoPoint(dtv.getLatitud(), dtv.getLongitud());
        }
    }

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

    private void buscarVacunatorios() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        filtradodeptoLoc = false;

        String stringUrl = ConnConstant.API_VACUNATORIOS_URL;

        if (networkInfo != null && networkInfo.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            new VacunMapActivity.DownloadVacunasTask().execute(stringUrl);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void buscarVacunatoriosDistancia(Double distancia) {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        filtradodeptoLoc = false;

        String stringUrl = ConnConstant.API_VACUNATORIOS_CERCANOS_URL;
        Double latitud = center.getLatitude();
        Double longitud = center.getLongitude();

        stringUrl = stringUrl.replace("{latitud}", latitud.toString());
        stringUrl = stringUrl.replace("{longitud}", longitud.toString());
        stringUrl = stringUrl.replace("{distancia}", distancia.toString());

        if (networkInfo != null && networkInfo.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

            map.getOverlays().clear();

            Marker marker = new Marker(map);
            marker.setPosition(center);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(getString(R.string.map_text_loc));
            addMarker(marker);

            new VacunMapActivity.DownloadVacunasTask().execute(stringUrl);

        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void buscarVacunatoriosLocDepto(String departamento, String localidad) {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        filtradodeptoLoc = true;

        String stringUrl = ConnConstant.API_VACUNATORIOS_FILTERLOCALIDAD_URL;

        stringUrl = stringUrl.replace("{departamento}", departamento);
        stringUrl = stringUrl.replace("{localidad}", localidad);

        if (networkInfo != null && networkInfo.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

            map.getOverlays().clear();

            Marker marker = new Marker(map);
            marker.setPosition(center);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(getString(R.string.map_text_loc));
            addMarker(marker);

            new VacunMapActivity.DownloadVacunasTask().execute(stringUrl);

        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void buscarVacunatoriosDepto(String departamento) {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        filtradodeptoLoc = true;

        String stringUrl = ConnConstant.API_VACUNATORIOS_FILTERDEPTO_URL;

        stringUrl = stringUrl.replace("{departamento}", departamento);

        if (networkInfo != null && networkInfo.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

            map.getOverlays().clear();

            Marker marker = new Marker(map);
            marker.setPosition(center);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(getString(R.string.map_text_loc));
            addMarker(marker);

            new VacunMapActivity.DownloadVacunasTask().execute(stringUrl);

        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    private class DownloadVacunasTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return VacunatoriosInfoGralUrl(urls[0]);
            } catch (IOException e) {
                Log.i(TAG, "DownloadVacunasTask: " + e.getMessage());
                return getString(R.string.err_recuperarpag);
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {

            if (result instanceof ArrayList) {
                addVacunatorios((List<DtVacunatorio>) result);

                if(filtradodeptoLoc && findCenter != null){
                    mc.animateTo(findCenter);
                }

            }else if(result instanceof String){
                Log.i("onPostExecute", "response: " + ((String) result));

            }
            progressBar.setVisibility(View.INVISIBLE);

        }

    }

    private List<DtVacunatorio> VacunatoriosInfoGralUrl(String myurl) throws IOException {
        InputStream is = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
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
                conn.disconnect();
            }
        }
    }

    public List<DtVacunatorio> readInfoGralJsonStream(InputStream in) throws IOException {
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
        try {
            return readRESTMessage(reader);
        } finally {
            reader.close();
        }
    }

    public List<DtVacunatorio> readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtVacunatorio> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralVacunatorioArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtVacunatorio> readInfoGralVacunatorioArray(JsonReader reader) throws IOException {
        List<DtVacunatorio> vacunatorios = new ArrayList<DtVacunatorio>();
        reader.beginArray();
        while (reader.hasNext()) {

            vacunatorios.add((DtVacunatorio) readVacunatorio(reader));
        }
        reader.endArray();
        return vacunatorios;
    }

    public DtVacunatorio readVacunatorio(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;
        Double latitud = null;
        Double longitud = null;
        DtUbicacion ubicacion = new DtUbicacion();
        List<DtPuesto> puestos = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            } else if (name.equals("latitud") && reader.peek() != JsonToken.NULL) {
                latitud = reader.nextDouble();
            }  else if (name.equals("longitud") && reader.peek() != JsonToken.NULL) {
                longitud = reader.nextDouble();
            } else if (name.equals("direccion") && reader.peek() != JsonToken.NULL) {
                ubicacion.setDireccion(reader.nextString());
            } else if (name.equals("departamento") && reader.peek() != JsonToken.NULL) {
                ubicacion = readDepartamento(reader, ubicacion);
            } else if (name.equals("localidad") && reader.peek() != JsonToken.NULL) {
                ubicacion = readLocalidad(reader, ubicacion);
            }  else if (name.equals("puestos") && reader.peek() != JsonToken.NULL) {
                puestos = readPuestosArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtVacunatorio(id, nombre, latitud, longitud, ubicacion, puestos);
    }

    public DtUbicacion readDepartamento(JsonReader reader, DtUbicacion ubicacion) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                ubicacion.setId_departamento(reader.nextInt());
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                ubicacion.setNombre_departamento(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return ubicacion;
    }

    public DtUbicacion readLocalidad(JsonReader reader, DtUbicacion ubicacion) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                ubicacion.setId_localidad(reader.nextInt());
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                ubicacion.setNombre_localidad(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return ubicacion;
    }
    public List<DtPuesto> readPuestosArray(JsonReader reader) throws IOException {
        List<DtPuesto> puestos = new ArrayList<DtPuesto>();
        reader.beginArray();
        while (reader.hasNext()) {
            puestos.add(readPuesto(reader));
        }
        reader.endArray();
        return puestos;
    }

    public DtPuesto readPuesto(JsonReader reader) throws IOException {
        Integer numero = null;
        Integer id = null;
        List<DtAgenda> agenda = null;



        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("numero") && reader.peek() != JsonToken.NULL) {
                numero = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtPuesto(id, numero, agenda);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE+DEPARTAMENTO && resultCode == RESULT_OK) {
            String sel_depto =  data.getStringExtra("departamento");

            buscarVacunatoriosDepto(sel_depto);

        } else if(requestCode == CODE+LOCALIDAD && resultCode == RESULT_OK){
            String sel_depto =  data.getStringExtra("departamento");
            String sel_loc = data.getStringExtra("localidad");

            buscarVacunatoriosLocDepto(sel_depto, sel_loc);
        }

    }
}