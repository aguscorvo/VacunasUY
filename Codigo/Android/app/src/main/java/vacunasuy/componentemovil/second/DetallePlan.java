package vacunasuy.componentemovil.second;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import vacunasuy.componentemovil.AgendarActivity;
import vacunasuy.componentemovil.GubUyActivity;
import vacunasuy.componentemovil.MainActivity;
import vacunasuy.componentemovil.PlanVacunacion;
import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtEnfermedad;
import vacunasuy.componentemovil.obj.DtPlan;
import vacunasuy.componentemovil.obj.DtSectorLaboral;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.obj.DtVacuna;

public class DetallePlan extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    DtPlan planvacunas = null;
    DtUsuario usuario = DtUsuario.getInstance();

    TextView edadmin;
    TextView edadmax;
    TextView fechaini;
    TextView fechafin;
    TextView sectores;
    TextView vacunanombre;
    TextView vacunadosis;
    TextView vacunaperiodo;
    TextView vacunainmunidad;
    TextView enfermedad;
    TextView nologin;
    TextView nombreplan;
    Button ingreso;
    Button agendar;
    Integer idPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        idPlan = bundle.getInt("IDPlan");

        buscarPlan();

        setContentView(R.layout.activity_detalle_plan);
    }

    @SuppressLint("SetTextI18n")
    private void addPlan(DtPlan dtplan){
        edadmin = findViewById(R.id.plan_detalle_edadmin);
        edadmax = findViewById(R.id.plan_detalle_edadmax);
        fechaini = findViewById(R.id.plan_detalle_fechaini);
        fechafin = findViewById(R.id.plan_detalle_fechafin);
        sectores = findViewById(R.id.plan_detalle_sectores);
        vacunanombre = findViewById(R.id.plan_detalle_vacuna_nombre);
        vacunadosis = findViewById(R.id.plan_detalle_vacuna_dosis);
        vacunaperiodo = findViewById(R.id.plan_detalle_vacuna_periodo);
        vacunainmunidad = findViewById(R.id.plan_detalle_vacuna_inmunidad);
        enfermedad = findViewById(R.id.plan_detalle_vacuna_enfermedad);
        nombreplan = findViewById(R.id.plan_detalle_nombre);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");

        String strname = getString(R.string.plan_group_title) + dtplan.getVacuna().getNombre();
        nombreplan.setText(strname);

        edadmin.setText(getString(R.string.plan_detalle_edadmin) + "\n" + dtplan.getEdadMinima());
        edadmax.setText(getString(R.string.plan_detalle_edadmax) + "\n" + dtplan.getEdadMaxima());
        fechaini.setText(getString(R.string.plan_detalle_fechaini) + "\n" + sdf.format(dtplan.getFechaInicio()));
        fechafin.setText(getString(R.string.plan_detalle_fechafin) + "\n" + sdf.format(dtplan.getFechaFin()));

        sectores.setText("");
        for(DtSectorLaboral sec: dtplan.getSectores()){
            if (sectores.getText().equals("")){
                sectores.setText(sec.getNombre());
            }else{
                sectores.setText( sectores.getText() + "\n" + sec.getNombre());
            }
        }

        vacunanombre.setText(getString(R.string.plan_detalle_vacuna_nombre) + "\n" + dtplan.getVacuna().getNombre());
        vacunadosis.setText(getString(R.string.plan_detalle_vacuna_dosis) + "\n" + dtplan.getVacuna().getCant_dosis());
        vacunaperiodo.setText(getString(R.string.plan_detalle_vacuna_periodo) + "\n" + dtplan.getVacuna().getPeriodo());
        vacunainmunidad.setText(getString(R.string.plan_detalle_vacuna_inmunidad) + "\n" + dtplan.getVacuna().getInmunidad());
        enfermedad.setText(dtplan.getVacuna().getEnfermedad().getNombre());

        ingreso = findViewById(R.id.detalleplan_ingresar);
        agendar = findViewById(R.id.detalleplan_agendar);
        nologin = findViewById(R.id.plan_detalle_nologed);

        if(usuario.getRegistrado()){
            ingreso.setVisibility(View.INVISIBLE);
            nologin.setVisibility(View.INVISIBLE);

            if( validarPlan(dtplan, usuario)){
                agendar.setVisibility(View.VISIBLE);
            } else{
                agendar.setVisibility(View.INVISIBLE);
            }

        }else{
            ingreso.setVisibility(View.VISIBLE);
            nologin.setVisibility(View.VISIBLE);
            agendar.setVisibility(View.INVISIBLE);
        }

        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userlogin = new Intent(DetallePlan.this, GubUyActivity.class);
                startActivity(userlogin);
            }
        });

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagenda = new Intent(DetallePlan.this, AgendarActivity.class);
                iagenda.putExtra("IDPlan", dtplan.getId());
                iagenda.putExtra("nombrePlan", getString(R.string.plan_group_title) + dtplan.getVacuna().getNombre());
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
                iagenda.putExtra("FechaFin", sdf.format(dtplan.getFechaFin()));

                startActivity(iagenda);

            }
        });

    }

    private Boolean validarPlan(DtPlan dtPlan, DtUsuario usuario){
        Date ahora = new Date();
        int estadofi = ahora.compareTo(dtPlan.getFechaInicio());
        int estadoff = ahora.compareTo(dtPlan.getFechaFin());
        boolean vigente = true;

        if(estadofi > 0){
            if(estadoff > 0){
                vigente = false;
            }
        } else if(estadofi < 0){
            vigente = false;
        }

        long edadEnDias = (ahora.getTime()-usuario.getFechanacimiento().getTime())/(1000 * 60 * 60 * 24);
        int anos = Double.valueOf(edadEnDias / 365.25).intValue();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");

        if(!(anos >= dtPlan.getEdadMinima() && anos <= dtPlan.getEdadMaxima()))
            vigente = false;

        boolean essector = false;
        for(DtSectorLaboral dts : dtPlan.getSectores()){
            if (dts.getId().equals(usuario.getSectorlaboral().getId())) {
                essector = true;
                break;
            }
        }

        if(!essector)
            vigente = false;


        return vigente;
    }

    private void buscarPlan() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_PLANESVACUNACION_URL;

        if (networkInfo != null && networkInfo.isConnected()) {
            new DetallePlan.DownloadPlanesTask().execute(stringUrl);
        }
    }


    @SuppressLint("StaticFieldLeak")
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
                for(DtPlan dtp: planes){
                    if(dtp.getId().equals(idPlan)){
                        addPlan(dtp);
                    }
                }
            }else {
                AlertDialog dialog = new AlertDialog.Builder(DetallePlan.this).create();
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
                        Intent iplan = new Intent(DetallePlan.this, MainActivity.class);
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
        boolean ok = false;
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

            planes.add(readPlan(reader));
        }
        reader.endArray();
        return planes;
    }

    @SuppressLint("SimpleDateFormat")
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