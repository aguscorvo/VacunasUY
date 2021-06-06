package vacunasuy.componentemovil.second;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vacunasuy.componentemovil.GubUyActivity;
import vacunasuy.componentemovil.MainActivity;
import vacunasuy.componentemovil.NotificacionActivity;
import vacunasuy.componentemovil.PlanVacunacion;
import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.UserInfoActivity;
import vacunasuy.componentemovil.VacunMapActivity;
import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtAgenda;
import vacunasuy.componentemovil.obj.DtPuesto;
import vacunasuy.componentemovil.obj.DtUbicacion;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.obj.DtVacuna;
import vacunasuy.componentemovil.obj.DtVacunatorio;

public class MiAgenda extends AppCompatActivity {
    private static final String TAG = "MiAgenda";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    BottomNavigationView bottomNavigationView;
    DtUsuario dtUsuario = DtUsuario.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buscarMiAgenda();
        setContentView(R.layout.activity_mi_agenda);

        bottomNavigationView = findViewById(R.id.bottomNavigationMiAgenda);
        bottomNavigationView.setSelectedItemId(R.id.menu_agenda);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.menu_home:
                    Intent intent2 = new Intent(MiAgenda.this, MainActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.menu_agenda:
                    Intent iagenda = new Intent(MiAgenda.this, PlanVacunacion.class);
                    startActivity(iagenda);
                    return true;
                case R.id.menu_notificacion:
                    if(dtUsuario.getRegistrado()){
                        if(dtUsuario.getFechanacimiento()==null || dtUsuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(MiAgenda.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            Intent notificacioninfo = new Intent(MiAgenda.this, NotificacionActivity.class);
                            startActivity(notificacioninfo);
                        }
                    }else{
                        AlertDialog dialog = new AlertDialog.Builder(MiAgenda.this).create();
                        dialog.setTitle(R.string.info_title);
                        dialog.setMessage(getString(R.string.menu_LoginNotificacion));

                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_ingresar), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent userlogin = new Intent(MiAgenda.this, GubUyActivity.class);
                                startActivity(userlogin);
                            }
                        });
                        dialog.show();
                    }
                    return true;
                case R.id.menu_vacunatorio:
                    Intent ivacunatroio = new Intent(MiAgenda.this, VacunMapActivity.class);
                    startActivity(ivacunatroio);
                    return true;
                case R.id.menu_usuario:

                    if(dtUsuario.getRegistrado()){
                        if(dtUsuario.getFechanacimiento()==null || dtUsuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(MiAgenda.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            Intent userinfo = new Intent(MiAgenda.this, UserInfoActivity.class);
                            startActivity(userinfo);
                        }
                    }else{
                        Intent userlogin = new Intent(MiAgenda.this, GubUyActivity.class);
                        startActivity(userlogin);
                    }
                    return true;
            }
            return false;
        });
    }

    private void addMiAgenda(List<DtAgenda> planes){
        if(planes.size()!=0){
            expandableListView = findViewById(R.id.miAgendaExpandable);
            expandableListAdapter = new CustomExpandableAgendaListAdapter(MiAgenda.this, planes);
            expandableListView.setAdapter(expandableListAdapter);

            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                }
            });



        }else {
            finish();
        }

    }
    private void buscarMiAgenda() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_GETUSUARIOAGENDA_URL;
        stringUrl = stringUrl.replace("{idUsuario}", dtUsuario.getId().toString());

        if (networkInfo != null && networkInfo.isConnected()) {
            new MiAgenda.DownloadMiAgendaTask().execute(stringUrl);
        } 
    }

    private class DownloadMiAgendaTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return MiAgendaInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {

            if (result instanceof ArrayList) {
                List<DtAgenda> agendas = (List<DtAgenda>) result;
                addMiAgenda(agendas);

            }else{
                AlertDialog dialog = new AlertDialog.Builder(MiAgenda.this).create();
                dialog.setTitle(R.string.info_title);

                if(result instanceof String){
                    dialog.setMessage((String) result);
                } else {
                    dialog.setMessage(getString(R.string.err_recuperarpag));
                    Log.i(TAG, getString(R.string.err_recuperarpag));
                }
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.alert_btn_neutral), new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent iplan = new Intent(MiAgenda.this, PlanVacunacion.class);
                        startActivity(iplan);
                    }
                });
                dialog.show();
            }
        }

    }

    private List<DtAgenda> MiAgendaInfoGralUrl(String myurl) throws IOException {
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
            //int response = conn.getResponseCode();
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

    public List<DtAgenda> readInfoGralJsonStream(InputStream in) throws IOException {
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

    public List<DtAgenda> readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtAgenda> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralMiAgendaArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtAgenda> readInfoGralMiAgendaArray(JsonReader reader) throws IOException {
        List<DtAgenda> agendas = new ArrayList<DtAgenda>();
        reader.beginArray();
        while (reader.hasNext()) {

            agendas.add((DtAgenda) readMiAgenda(reader));
        }
        reader.endArray();
        return agendas;
    }

    public DtAgenda readMiAgenda(JsonReader reader) throws IOException {
        Integer id = null;
        Date fecha = null;
        DtVacunatorio vacunatorio = null;
        DtVacuna vacuna = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("fecha") && reader.peek() != JsonToken.NULL) {
                String sfecha = reader.nextString();
                if(!sfecha.equalsIgnoreCase("")){
                    try {
                        fecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(sfecha);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } else if (name.equals("puesto") && reader.peek() != JsonToken.NULL) {
                vacunatorio = readPuesto(reader);
            } else if (name.equals("planVacunacion") && reader.peek() != JsonToken.NULL) {
                vacuna = readplanVacunacion(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtAgenda(id, fecha, vacunatorio, vacuna);
    }

    public DtVacunatorio readPuesto(JsonReader reader) throws IOException {
        Integer id = null;
        Integer numero = null;
        DtVacunatorio vacunatorio = null;
        List<DtPuesto> puestos = new ArrayList<DtPuesto>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("numero") && reader.peek() != JsonToken.NULL) {
                numero = reader.nextInt();
            } else if (name.equals("vacunatorio") && reader.peek() != JsonToken.NULL) {
                vacunatorio = readVacunatorio(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        DtPuesto dtPuesto = new DtPuesto(id, numero, null);
        puestos.add(dtPuesto);
        vacunatorio.setPuestos(puestos);

        return vacunatorio;
    }

    public DtVacunatorio readVacunatorio(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;
        DtUbicacion ubicacion = new DtUbicacion();


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            } else if (name.equals("direccion") && reader.peek() != JsonToken.NULL) {
                ubicacion.setDireccion(reader.nextString());
            } else if (name.equals("departamento") && reader.peek() != JsonToken.NULL) {
                ubicacion = readDepartamento(reader, ubicacion);
            } else if (name.equals("localidad") && reader.peek() != JsonToken.NULL) {
                ubicacion = readLocalidad(reader, ubicacion);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtVacunatorio(id, nombre, null, null, ubicacion, null);
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

    public DtVacuna readplanVacunacion(JsonReader reader) throws IOException {
        DtVacuna vacuna = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("vacuna") && reader.peek() != JsonToken.NULL) {
                vacuna = readVacuna(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return vacuna;
    }

    public DtVacuna readVacuna(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtVacuna(id, nombre, null, null, null, null);
    }


}