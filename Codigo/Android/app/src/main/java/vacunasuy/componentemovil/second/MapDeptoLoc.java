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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;


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

public class MapDeptoLoc extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Intent result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = new Intent();

        buscarDepartamentos();
        String title = getString(R.string.app_name) + " - " + getString(R.string.title_seleccionar)+ " " + getString(R.string.title_localidad);
        setTitle(title);

        setContentView(R.layout.activity_map_depto_loc);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void addDepartamentos(List<DtDepartamento> departamentos){
        Log.i(TAG, "addDepartamentos List<DtDepartamento>: " + departamentos.size());

        if(departamentos.size()!=0){
            expandableListView = findViewById(R.id.expandableListView);
            expandableListAdapter = new CustomExpandableListAdapter(MapDeptoLoc.this, departamentos);
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
                    result.putExtra("departamento", departamentos.get(groupPosition).getId().toString());
                    result.putExtra("localidad", departamentos.get(groupPosition).getLocalidades().get(childPosition).getId().toString());
                    MapDeptoLoc.this.setResult(RESULT_OK, result);
                    finish();
                    return false;
                }
            });

        }else {
            result.putExtra("departamento", -1);
            result.putExtra("localidad", -1);
            MapDeptoLoc.this.setResult(RESULT_CANCELED, result);
            finish();
        }

    }


    private void buscarDepartamentos() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_DEPARTAMENTOS_URL;

        if (networkInfo != null && networkInfo.isConnected()) {
            new MapDeptoLoc.DownloadDepartamentosTask().execute(stringUrl);
        } else {
        }
    }


    private class DownloadDepartamentosTask extends AsyncTask<String, Void, Object> {
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
                List<DtDepartamento> departamentos = (List<DtDepartamento>) result;
                addDepartamentos(departamentos);

            }else if(result instanceof String){
                Log.i("onPostExecute", "response: " + ((String) result));

            }
        }

    }

    private List<DtDepartamento> VacunatoriosInfoGralUrl(String myurl) throws IOException {
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