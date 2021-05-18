package vacunasuy.componentemovil.second;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtDepartamento;
import vacunasuy.componentemovil.obj.DtLocalidad;
import vacunasuy.componentemovil.obj.DtUsuario;


public class AddFechaNacimiento extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    TextView documento;
    DatePicker fechanacimiento;
    Button bt_actualizar;
    DtUsuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fecha_nacimiento);

        documento = findViewById(R.id.addFecha_idocumento);
        fechanacimiento = findViewById(R.id.addFecha_nacimiento);
        bt_actualizar = findViewById(R.id.addFecha_update);

        usuario = DtUsuario.getInstance();

        documento.setText(usuario.getDocumento());
        Log.i(TAG, usuario.getApellido());

        bt_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFechaNacimiento.this, fechanacimiento.getDayOfMonth()+"/"+ (fechanacimiento.getMonth() + 1)+"/"+fechanacimiento.getYear(), Toast.LENGTH_LONG).show();
            }
        });
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
            new AddFechaNacimiento().PutUsuarioTask().execute(stringUrl);
        } else {
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

            if (result instanceof ArrayList) {
                List<DtDepartamento> departamentos = (List<DtDepartamento>) result;
                //addDepartamentos(departamentos);

            }else if(result instanceof String){
                Log.i("onPostExecute", "response: " + ((String) result));

            }
        }

    }

    private List<DtDepartamento> UsuarioUpdateUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);

            String data = "client_id=" + client_id + "&client_secret="+ client_secret +"&grant_type=client_credentials";

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            if (response == 200){
                is = conn.getInputStream();
                return readInfoGralJsonStream(is);
            }else{
                return null;
            }

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public List<DtDepartamento> readInfoGralJsonStream(InputStream in) throws IOException {
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

    public List<DtDepartamento> readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtDepartamento> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralDepartamentoArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtDepartamento> readInfoGralDepartamentoArray(JsonReader reader) throws IOException {
        List<DtDepartamento> departamentos = new ArrayList<DtDepartamento>();
        reader.beginArray();
        while (reader.hasNext()) {

            departamentos.add((DtDepartamento) readDepartamento(reader));
        }
        reader.endArray();
        return departamentos;
    }

    public DtDepartamento readDepartamento(JsonReader reader) throws IOException {
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
            } else if (name.equals("localidades") && reader.peek() != JsonToken.NULL) {
                localidades = readLocalidadArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtDepartamento(id, nombre, localidades);
    }

    public List<DtLocalidad> readLocalidadArray(JsonReader reader) throws IOException {
        List<DtLocalidad> localidades = new ArrayList<DtLocalidad>();
        reader.beginArray();
        while (reader.hasNext()) {
            localidades.add(readLocalidad(reader));
        }
        reader.endArray();
        return localidades;
    }


    public DtLocalidad readLocalidad(JsonReader reader) throws IOException {
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
        return new DtLocalidad(id, nombre);
    }


}