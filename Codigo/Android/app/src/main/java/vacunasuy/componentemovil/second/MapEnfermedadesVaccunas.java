package vacunasuy.componentemovil.second;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vacunasuy.componentemovil.AgendarActivity;
import vacunasuy.componentemovil.GubUyActivity;
import vacunasuy.componentemovil.MainActivity;
import vacunasuy.componentemovil.NotificacionActivity;
import vacunasuy.componentemovil.PlanVacunacion;
import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.UserInfoActivity;
import vacunasuy.componentemovil.VacunMapActivity;
import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtEnfermedad;
import vacunasuy.componentemovil.obj.DtUsuario;

public class MapEnfermedadesVaccunas extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    ListView listView;
    ListAdapter listAdapter;
    DtUsuario usuario = DtUsuario.getInstance();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buscarEnfermedadesVacunas();
        String title = getString(R.string.app_name) + " - " + getString(R.string.title_certificado);
        setTitle(title);
        setContentView(R.layout.activity_map_enfermedades_vaccunas);

        bottomNavigationView = findViewById(R.id.enfermedadBottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.menu_agenda);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent ihome = new Intent(MapEnfermedadesVaccunas.this, MainActivity.class);
                        startActivity(ihome);
                        return true;
                    case R.id.menu_agenda:
                        Intent iagenda = new Intent(MapEnfermedadesVaccunas.this, PlanVacunacion.class);
                        startActivity(iagenda);
                        return true;
                    case R.id.menu_notificacion:
                        if(usuario.getRegistrado()){
                            Intent notificacioninfo = new Intent(MapEnfermedadesVaccunas.this, NotificacionActivity.class);
                            startActivity(notificacioninfo);
                        }else{
                            AlertDialog dialog = new AlertDialog.Builder(MapEnfermedadesVaccunas.this).create();
                            dialog.setTitle(R.string.info_title);
                            dialog.setMessage(getString(R.string.menu_LoginNotificacion));

                            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_ingresar), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent userlogin = new Intent(MapEnfermedadesVaccunas.this, GubUyActivity.class);
                                    startActivity(userlogin);
                                }
                            });
                            dialog.show();
                        }
                        return true;
                    case R.id.menu_vacunatorio:
                        Intent ivacunatroio = new Intent(MapEnfermedadesVaccunas.this, VacunMapActivity.class);
                        startActivity(ivacunatroio);
                        return true;
                    case R.id.menu_usuario:
                        DtUsuario usuario = DtUsuario.getInstance();
                        if(usuario.getRegistrado()){
                            if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                                Intent fnintent = new Intent(MapEnfermedadesVaccunas.this, AddFechaNacimiento.class);
                                startActivity(fnintent);
                            }else {
                                Intent userinfo = new Intent(MapEnfermedadesVaccunas.this, UserInfoActivity.class);
                                startActivity(userinfo);
                            }
                        }else{
                            Intent userlogin = new Intent(MapEnfermedadesVaccunas.this, GubUyActivity.class);
                            startActivity(userlogin);
                        }

                        return true;
                }
                return false;
            }
        });
    }

    private void addEnfermedades(List<DtEnfermedad> enfermedades){
        Log.i(TAG, "addEnfermedades List<DtEnfermedad>: " + enfermedades.size());

        listView = findViewById(R.id.enfermedadListView);
        listAdapter = new CustomEnfermedadListAdapter(this, enfermedades);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent ienfermedad = new Intent(MapEnfermedadesVaccunas.this, CertificadoVacuna.class);
                ienfermedad.putExtra("IDPlan", enfermedades.get(position).getId());
                ienfermedad.putExtra("nombrePlan", enfermedades.get(position).getNombre());
                startActivity(ienfermedad);
            }
        });

    }


    private void buscarEnfermedadesVacunas() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_GETENFERMEDADESVACUNAS_URL;
        stringUrl = stringUrl.replace("{id}", usuario.getId().toString());

        if (networkInfo != null && networkInfo.isConnected()) {
            new MapEnfermedadesVaccunas.DownloadEnfermedadesVacunasTask().execute(stringUrl);
        }
    }

    private class DownloadEnfermedadesVacunasTask extends AsyncTask<String, Void, Object> {
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
                List<DtEnfermedad> enfermedades = (List<DtEnfermedad>) result;
                addEnfermedades(enfermedades);

            }else if(result instanceof String){
                Log.i("onPostExecute", "response: " + ((String) result));

            }
        }

    }

    private List<DtEnfermedad> VacunatoriosInfoGralUrl(String myurl) throws IOException {
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
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

    public List<DtEnfermedad> readInfoGralJsonStream(InputStream in) throws IOException {
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

    public List<DtEnfermedad> readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtEnfermedad> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralEnfermeadadArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtEnfermedad> readInfoGralEnfermeadadArray(JsonReader reader) throws IOException {
        List<DtEnfermedad> enfermedades = new ArrayList<DtEnfermedad>();
        reader.beginArray();
        while (reader.hasNext()) {

            enfermedades.add(readEnfermedad(reader));
        }
        reader.endArray();
        return enfermedades;
    }

    public DtEnfermedad readEnfermedad(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            }  else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtEnfermedad(id, nombre);
    }
}