package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtAgenda;
import vacunasuy.componentemovil.obj.DtDepartamento;
import vacunasuy.componentemovil.obj.DtPlan;
import vacunasuy.componentemovil.obj.DtPuesto;
import vacunasuy.componentemovil.obj.DtUbicacion;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.obj.DtVacunatorio;
import vacunasuy.componentemovil.second.AddFechaNacimiento;
import vacunasuy.componentemovil.second.CustomExpandableListAdapter;
import vacunasuy.componentemovil.second.CustomExpandableVacunatorioListAdapter;
import vacunasuy.componentemovil.second.DetallePlan;
import vacunasuy.componentemovil.second.MapDeptoLoc;

public class AgendarActivity extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    DtUsuario usuario = DtUsuario.getInstance();
    Integer idPlan;
    Integer idVacunatorio;
    Integer idPuesto;
    String fechaSolicitada;
    String nombrePlan;
    String fechaFin;
    Integer mYear;
    Integer mMonth;
    Integer mDay;


    BottomNavigationView bottomNavigationView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    TextView plantitle;
    ImageButton imageCalendar;
    Button agendar;
    EditText dateText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        Bundle bundle = this.getIntent().getExtras();
        idPlan = bundle.getInt("IDPlan");
        nombrePlan = bundle.getString("nombrePlan");
        fechaFin = bundle.getString("FechaFin");

        bottomNavigationView = findViewById(R.id.agendaBottomNavigationView);
        plantitle = findViewById(R.id.agendaNombrePlan);
        imageCalendar = findViewById(R.id.agendaImageButton);
        dateText = findViewById(R.id.agendaFecha);
        agendar = findViewById(R.id.agendaAgendar);
        plantitle.setText(nombrePlan);
        dateText.setEnabled(false);

        buscarVacunatorios();

        Calendar mcurrentDate=Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth=mcurrentDate.get(Calendar.MONTH);
        mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        imageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker=new DatePickerDialog(AgendarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        fechaSolicitada = selectedyear + "-" +
                                (selectedmonth<10?"0":"") + selectedmonth + "-" +
                                (selectedday<10?"0":"") + selectedday;
                        dateText.setText(fechaSolicitada);

                    }


                },mYear, mMonth, mDay);
                mDatePicker.setTitle(getString(R.string.agenda_DatePickeTitle));
                try {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 1);

                    mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    mDatePicker.getDatePicker().setMaxDate((new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin)).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                mDatePicker.show();
            }
        });





        bottomNavigationView.setSelectedItemId(R.id.menu_agenda);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent ihome = new Intent(AgendarActivity.this, MainActivity.class);
                        startActivity(ihome);
                        return true;
                    case R.id.menu_agenda:
                        Intent iagenda = new Intent(AgendarActivity.this, PlanVacunacion.class);
                        startActivity(iagenda);
                        return true;
                    case R.id.menu_notificacion:
                        Toast.makeText(AgendarActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_vacunatorio:
                        Intent ivacunatroio = new Intent(AgendarActivity.this, VacunMapActivity.class);
                        startActivity(ivacunatroio);
                        return true;
                    case R.id.menu_usuario:
                        DtUsuario usuario = DtUsuario.getInstance();
                        if(usuario.getRegistrado()){
                            if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                                Intent fnintent = new Intent(AgendarActivity.this, AddFechaNacimiento.class);
                                startActivity(fnintent);
                            }else {
                                Intent userinfo = new Intent(AgendarActivity.this, UserInfoActivity.class);
                                startActivity(userinfo);
                            }
                        }else{
                            Intent userlogin = new Intent(AgendarActivity.this, GubUyActivity.class);
                            startActivity(userlogin);
                        }

                        return true;
                }
                return false;
            }
        });
    }


    private void addVacunatorios(List<DtVacunatorio> vacunatorios){
        Log.i(TAG, "addDepartamentos List<DtDepartamento>: " + vacunatorios.size());

        if(vacunatorios.size()!=0){
            expandableListView = findViewById(R.id.agendaexpandableListView);
            expandableListAdapter = new CustomExpandableVacunatorioListAdapter(AgendarActivity.this, vacunatorios);
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
                    idVacunatorio = vacunatorios.get(groupPosition).getId();
                    idPuesto = vacunatorios.get(groupPosition).getPuestos().get(childPosition).getId();



                    return false;
                }
            });

        }else {
            AlertDialog dialog = new AlertDialog.Builder(AgendarActivity.this).create();
            dialog.setTitle(R.string.info_title);
            dialog.setMessage(getString(R.string.agenda_errorVacunatorios));

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.alert_btn_neutral), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    Intent iplan = new Intent(AgendarActivity.this, PlanVacunacion.class);
                    startActivity(iplan);
                }
            });
            dialog.show();
        }
    }

    private void buscarVacunatorios() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_VACUNATORIOSPLAN_URL;
        stringUrl = stringUrl.replace("{id}", idPlan.toString());

        if (networkInfo != null && networkInfo.isConnected()) {
            new AgendarActivity.DownloadVacunatorioTask().execute(stringUrl);
        }
    }

    private class DownloadVacunatorioTask extends AsyncTask<String, Void, Object> {
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
            }else{
                AlertDialog dialog = new AlertDialog.Builder(AgendarActivity.this).create();
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
                        Intent iplan = new Intent(AgendarActivity.this, MainActivity.class);
                        startActivity(iplan);
                    }
                });
                dialog.show();
            }

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
            } else if (name.equals("agendas") && reader.peek() != JsonToken.NULL) {
                agenda = readAgendaArray(reader);;
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtPuesto(id, numero, agenda);
    }

    public List<DtAgenda> readAgendaArray(JsonReader reader) throws IOException {
        List<DtAgenda> agenda = new ArrayList<DtAgenda>();
        reader.beginArray();
        while (reader.hasNext()) {
            agenda.add(readAgenda(reader));
        }
        reader.endArray();
        return agenda;
    }

    public DtAgenda readAgenda(JsonReader reader) throws IOException {
        Integer id = null;
        Date fecha = null;

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
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtAgenda(id, fecha);
    }

}