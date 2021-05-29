package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtDepartamento;
import vacunasuy.componentemovil.obj.DtEnfermedad;
import vacunasuy.componentemovil.obj.DtLocalidad;
import vacunasuy.componentemovil.obj.DtPlan;
import vacunasuy.componentemovil.obj.DtSectorLaboral;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.obj.DtVacuna;
import vacunasuy.componentemovil.second.AddFechaNacimiento;
import vacunasuy.componentemovil.second.CustomExpandableListAdapter;
import vacunasuy.componentemovil.second.CustomExpandablePlanListAdapter;
import vacunasuy.componentemovil.second.CustomListAdapter;
import vacunasuy.componentemovil.second.DetallePlan;
import vacunasuy.componentemovil.second.MapDepto;
import vacunasuy.componentemovil.second.MapDeptoLoc;

public class PlanVacunacion extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buscarPlanes();
        setContentView(R.layout.activity_plan_vacunacion);

        bottomNavigationView = findViewById(R.id.bottomNavigationPlan);
        bottomNavigationView.setSelectedItemId(R.id.menu_agenda);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            DtUsuario usuario = DtUsuario.getInstance();
            switch (item.getItemId()){
                case R.id.menu_home:
                    Intent intent2 = new Intent(PlanVacunacion.this, MainActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.menu_agenda:

                    return true;
                case R.id.menu_notificacion:
                    if(usuario.getRegistrado()){
                        if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(PlanVacunacion.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            Intent notificacioninfo = new Intent(PlanVacunacion.this, NotificacionActivity.class);
                            startActivity(notificacioninfo);
                        }
                    }else{
                        AlertDialog dialog = new AlertDialog.Builder(PlanVacunacion.this).create();
                        dialog.setTitle(R.string.info_title);
                        dialog.setMessage(getString(R.string.menu_LoginNotificacion));

                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_ingresar), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent userlogin = new Intent(PlanVacunacion.this, GubUyActivity.class);
                                startActivity(userlogin);
                            }
                        });
                        dialog.show();
                    }
                    return true;
                case R.id.menu_vacunatorio:
                    Intent ivacunatroio = new Intent(PlanVacunacion.this, VacunMapActivity.class);
                    startActivity(ivacunatroio);
                    return true;
                case R.id.menu_usuario:

                    if(usuario.getRegistrado()){
                        if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(PlanVacunacion.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            Intent userinfo = new Intent(PlanVacunacion.this, UserInfoActivity.class);
                            startActivity(userinfo);
                        }
                    }else{
                        Intent userlogin = new Intent(PlanVacunacion.this, GubUyActivity.class);
                        startActivity(userlogin);
                    }
                    return true;
            }
            return false;
        });



    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private Boolean validarPlan(DtPlan dtPlan, DtUsuario usuario){
        Date ahora = new Date();
        Integer estadofi = ahora.compareTo(dtPlan.getFechaInicio());
        Integer estadoff = ahora.compareTo(dtPlan.getFechaFin());
        Boolean vigente = true;

        if(estadofi > 0){
            if(estadoff > 0){
                vigente = false;
            }
        } else if(estadofi < 0){
            vigente = false;
        }

        long edadEnDias = (ahora.getTime()-usuario.getFechanacimiento().getTime())/(1000 * 60 * 60 * 24);
        int anos = Double.valueOf(edadEnDias / 365.25).intValue();
        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");

        if(!(anos >= dtPlan.getEdadMinima() && anos <= dtPlan.getEdadMaxima()))
            vigente = false;

        Boolean essector = false;
        for(DtSectorLaboral dts : dtPlan.getSectores()){
            if(dts.getId()==usuario.getSectorlaboral().getId())
                essector = true;
        }

        if(!essector)
            vigente = false;


        return vigente;
    }


    private void addPlanes(List<DtPlan> planes){
        Log.i(TAG, "addPlanes List<DtPlan>: " + planes.size());

        if(planes.size()!=0){
            expandableListView = findViewById(R.id.expandablePlan);
            expandableListAdapter = new CustomExpandablePlanListAdapter(PlanVacunacion.this, planes);
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

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {

                    AlertDialog dialog = new AlertDialog.Builder(PlanVacunacion.this).create();
                    dialog.setTitle(R.string.plan_title);
                    DtUsuario usuario = DtUsuario.getInstance();


                    if(usuario.getRegistrado()){
                        if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(PlanVacunacion.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            dialog.setMessage(getString(R.string.plan_mensaje));

                            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.plan_detealle), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(PlanVacunacion.this, getString(R.string.plan_detealle), Toast.LENGTH_LONG).show();
                                    verDetalle(planes.get(groupPosition));

                                }
                            });
                            if(validarPlan(planes.get(groupPosition), usuario)){
                                dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_agendar), new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent iagenda = new Intent(PlanVacunacion.this, AgendarActivity.class);
                                        iagenda.putExtra("IDPlan", planes.get(groupPosition).getId());
                                        iagenda.putExtra("nombrePlan", getString(R.string.plan_group_title) + planes.get(groupPosition).getVacuna().getNombre());

                                        @SuppressLint("SimpleDateFormat")
                                        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
                                        iagenda.putExtra("FechaFin", sdf.format(planes.get(groupPosition).getFechaFin()));

                                        startActivity(iagenda);
                                    }
                                });
                            }
                        }
                    }else{
                        dialog.setMessage(getString(R.string.plan_mensaje_nologin));

                        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.plan_detealle), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                verDetalle(planes.get(groupPosition));

                            }
                        });
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_ingresar), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(PlanVacunacion.this, getString(R.string.plan_ingresar), Toast.LENGTH_LONG).show();
                                Intent userlogin = new Intent(PlanVacunacion.this, GubUyActivity.class);
                                startActivity(userlogin);
                            }
                        });


                    }
                    dialog.show();
                    return false;
                }
            });

        }else {
            finish();
        }

    }

    private void verDetalle(DtPlan dtPlan){
        Intent detalle = new Intent(PlanVacunacion.this, DetallePlan.class);
        detalle.putExtra("IDPlan", dtPlan.getId());
        startActivity(detalle);
    }


    private void buscarPlanes() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_PLANESVACUNACION_URL;

        if (networkInfo != null && networkInfo.isConnected()) {
            new PlanVacunacion.DownloadPlanesTask().execute(stringUrl);
        } else {
        }
    }


    private class DownloadPlanesTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return PlanesInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {

            if (result instanceof ArrayList) {
                List<DtPlan> planes = (List<DtPlan>) result;
                addPlanes(planes);

            }else{
                AlertDialog dialog = new AlertDialog.Builder(PlanVacunacion.this).create();
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
                        Intent iplan = new Intent(PlanVacunacion.this, MainActivity.class);
                        startActivity(iplan);
                    }
                });
                dialog.show();
            }
        }

    }

    private List<DtPlan> PlanesInfoGralUrl(String myurl) throws IOException {
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

    public List<DtPlan> readInfoGralJsonStream(InputStream in) throws IOException {
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

    public List<DtPlan> readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtPlan> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralPlanArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtPlan> readInfoGralPlanArray(JsonReader reader) throws IOException {
        List<DtPlan> planes = new ArrayList<DtPlan>();
        reader.beginArray();
        while (reader.hasNext()) {

            planes.add((DtPlan) readPlan(reader));
        }
        reader.endArray();
        return planes;
    }

    public DtPlan readPlan(JsonReader reader) throws IOException {
        Integer id = null;
        Integer edadMinima = null;
        Integer edadMaxima = null;
        Date fechaInicio = null;
        Date fechaFin = null;
        List<DtSectorLaboral> sectores = null;
        DtVacuna vacuna = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("edadMinima") && reader.peek() != JsonToken.NULL) {
                edadMinima = reader.nextInt();
            } else if (name.equals("edadMaxima") && reader.peek() != JsonToken.NULL) {
                edadMaxima = reader.nextInt();
            } else if (name.equals("fechaInicio") && reader.peek() != JsonToken.NULL) {
                String sfecha = reader.nextString();
                if(!sfecha.equalsIgnoreCase("")){
                    try {
                        fechaInicio = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(sfecha);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } else if (name.equals("fechaFin") && reader.peek() != JsonToken.NULL) {
                String sfecha = reader.nextString();
                if(!sfecha.equalsIgnoreCase("")){
                    try {
                        fechaFin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(sfecha);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } else if (name.equals("sectores") && reader.peek() != JsonToken.NULL) {
                sectores = readSectoresArray(reader);
            } else if (name.equals("vacuna") && reader.peek() != JsonToken.NULL) {
                vacuna = readVacuna(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtPlan(id, edadMinima, edadMaxima, fechaInicio, fechaFin, sectores, vacuna);
    }

    public List<DtSectorLaboral> readSectoresArray(JsonReader reader) throws IOException {
        List<DtSectorLaboral> sectores = new ArrayList<DtSectorLaboral>();
        reader.beginArray();
        while (reader.hasNext()) {
            sectores.add(readSector(reader));
        }
        reader.endArray();
        return sectores;
    }

    public DtSectorLaboral readSector(JsonReader reader) throws IOException {
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
        return new DtSectorLaboral(id, nombre);
    }

    public DtVacuna readVacuna(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;
        Integer cant_dosis = null;
        Integer periodo = null;
        Integer inmunidad = null;
        DtEnfermedad enfermedad = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            } else if (name.equals("cant_dosis") && reader.peek() != JsonToken.NULL) {
                cant_dosis = reader.nextInt();
            } else if (name.equals("periodo") && reader.peek() != JsonToken.NULL) {
                periodo = reader.nextInt();
            } else if (name.equals("inmunidad") && reader.peek() != JsonToken.NULL) {
                inmunidad = reader.nextInt();
            } else if (name.equals("enfermedad") && reader.peek() != JsonToken.NULL) {
                enfermedad = readEnfermedad(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtVacuna(id, nombre, cant_dosis, periodo, inmunidad, enfermedad);
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
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtEnfermedad(id, nombre);
    }


}