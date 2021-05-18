package vacunasuy.componentemovil.second;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.VacunMapActivity;
import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtLocalidad;
import vacunasuy.componentemovil.obj.DtResponse;
import vacunasuy.componentemovil.obj.DtSectorLaboral;
import vacunasuy.componentemovil.obj.DtUsuario;


public class AddFechaNacimiento extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    TextView documento;
    DatePicker fechanacimiento;
    Button bt_actualizar;
    DtUsuario usuario;
    Spinner spinnersectores;
    List<DtSectorLaboral> gsectores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fecha_nacimiento);

        documento = findViewById(R.id.addFecha_idocumento);
        fechanacimiento = findViewById(R.id.addFecha_nacimiento);
        bt_actualizar = findViewById(R.id.addFecha_update);
        spinnersectores = findViewById(R.id.spinnerFilterSectorLaboral);
        usuario = DtUsuario.getInstance();

        documento.setText(usuario.getDocumento());

        bt_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(fechanacimiento.getYear(), fechanacimiento.getMonth(), fechanacimiento.getDayOfMonth());

                usuario.setFechanacimiento(calendar.getTime());
                cargarUsuario();

            }
        });

        buscarSectorLaboral();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void cargarUsuario() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_EDITUSR_URL;
        stringUrl = stringUrl.replace("{id}", usuario.getId().toString());

        if (networkInfo != null && networkInfo.isConnected()) {
            new AddFechaNacimiento.PutUsuarioTask().execute(stringUrl);
        }
    }


    private class PutUsuarioTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return UsuarioUpdateUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {
            if (result instanceof DtResponse) {
                Toast.makeText(AddFechaNacimiento.this, ((DtResponse) result).getMensaje(), Toast.LENGTH_LONG).show();
                Log.i("onPostExecute", "response: " + ((DtResponse) result).getMensaje());
            }
        }

    }

    private DtResponse UsuarioUpdateUrl(String myurl) throws IOException {
        InputStream is = null;
        HttpURLConnection conn = null;

        try {

            String authorization ="Bearer  " + usuario.getToken();

            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
            conn.setRequestProperty("Authorization", authorization);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            String data = usuarioToJSON();

            Log.i(TAG, data);

            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = conn.getOutputStream();
            stream.write(out);

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.i(TAG, "conn.getResponseCode: " + response +" - " + conn.getResponseMessage());
            if (response == 200){
                is = conn.getInputStream();
                return readInfoGralJsonStream(is);
            }else{
                return new DtResponse(false, response +" - " + conn.getResponseMessage());
            }

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
                conn.disconnect();
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
        Log.i(TAG, sb.toString());
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
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtResponse(ok, mensaje);
    }

    private String usuarioToJSON(){
        String res = "";

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("documento", usuario.getDocumento());
            jsonObject.put("nombre", usuario.getNombre());
            jsonObject.put("apellido", usuario.getApellido());
            jsonObject.put("correo", usuario.getCorreo());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            jsonObject.put("fechaNacimiento", format.format(usuario.getFechanacimiento()));
            jsonObject.put("password", "");
            jsonObject.put("roles", new JSONArray());
            jsonObject.put("sectorLaboral", usuario.getSectorlaboral().getId());

            res = jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            res = "";
        }

        return res;
    }


    private void addSectroresLaborales(List<DtSectorLaboral> sectores){
        gsectores = sectores;
        List<String> stringsectores = new ArrayList<String>();

        for(DtSectorLaboral dts : sectores){
            stringsectores.add(dts.getNombre());
        }

        ArrayAdapter<String> aaop = new ArrayAdapter<String>(this, R.layout.map_spinner, stringsectores);
        spinnersectores.setAdapter(aaop);

        spinnersectores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddFechaNacimiento.this, gsectores.get(position).getNombre(), Toast.LENGTH_LONG).show();
                usuario.setSectorlaboral(gsectores.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void buscarSectorLaboral() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_SECTORLABORAL_URL;

        if (networkInfo != null && networkInfo.isConnected()) {
            new AddFechaNacimiento.DownloadSecoresLaboralesTask().execute(stringUrl);
        } else {
        }
    }

    private class DownloadSecoresLaboralesTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return SectoresLaboralesInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {

            if (result instanceof ArrayList) {
                addSectroresLaborales((List<DtSectorLaboral>) result);

            }else if(result instanceof String){
                Log.i("onPostExecute", "response: " + ((String) result));

            }
        }

    }

    private List<DtSectorLaboral> SectoresLaboralesInfoGralUrl(String myurl) throws IOException {
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
            return readSectoresLaboralesJsonStream(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
                conn.disconnect();
            }
        }
    }

    public List<DtSectorLaboral> readSectoresLaboralesJsonStream(InputStream in) throws IOException {
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
            return readSLRESTMessage(reader);
        } finally {
            reader.close();
        }
    }

    public List<DtSectorLaboral> readSLRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtSectorLaboral> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralSectorLaboralArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtSectorLaboral> readInfoGralSectorLaboralArray(JsonReader reader) throws IOException {
        List<DtSectorLaboral> sectoreslab = new ArrayList<DtSectorLaboral>();
        reader.beginArray();
        while (reader.hasNext()) {

            sectoreslab.add((DtSectorLaboral) readSectorLaboral(reader));
        }
        reader.endArray();
        return sectoreslab;
    }

    public DtSectorLaboral readSectorLaboral(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;
        List<DtLocalidad> localidades = null;

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

    ///////////////////////////////////////////////////////////////

}