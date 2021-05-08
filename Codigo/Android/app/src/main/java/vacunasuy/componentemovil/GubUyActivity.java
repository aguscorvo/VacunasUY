package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtMessage;
import vacunasuy.componentemovil.obj.DtResponse;
import vacunasuy.componentemovil.obj.DtRol;
import vacunasuy.componentemovil.obj.DtUsuario;

public class GubUyActivity extends AppCompatActivity {

    WebView navegador;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gub_uy);

        navegador = (WebView) findViewById(R.id.webView);
        navegador.loadUrl(ConnConstant.API_GUBUY_URL);

        WebSettings wstts = navegador.getSettings();
        wstts.setJavaScriptEnabled(true);
        wstts.setDomStorageEnabled(true);
        wstts.setSupportZoom(true);
        wstts.setUserAgentString(ConnConstant.USER_AGENT);

        navegador.setWebViewClient(new MyWebViewClient());

        bottomNavigationView = findViewById(R.id.bottomNavigationViewLoging);
        bottomNavigationView.setSelectedItemId(R.id.menu_usuario);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intent2 = new Intent(GubUyActivity.this, MainActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.menu_agenda:
                        Toast.makeText(GubUyActivity.this, "Opción Agenda", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_notificacion:
                        Toast.makeText(GubUyActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_vacunatorio:
                        Intent ivacunatroio = new Intent(GubUyActivity.this, VacunMapActivity.class);
                        startActivity(ivacunatroio);
                        return true;
                    case R.id.menu_usuario:
                        return true;
                }
                return false;
            }
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (ConnConstant.API_GUBUY_URL.equals(url)) {
                return false;
            } else if( Uri.parse(url).getQuery().contains("code")
                    && Uri.parse(url).getQuery().contains("state")
                    && !(Uri.parse(url).getQuery().contains("client_id"))){
                String access = Uri.parse(url).getQuery();
                Log.i("GubUyActivity", access);
                realizarLogin(access);
                return false;
            }
            return false;

        }
    }

    private void realizarLogin(String token) {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_USRLOGIN_URL + token;

        if (networkInfo != null && networkInfo.isConnected()) {
            new LoginUserTask().execute(stringUrl);
            GubUyActivity.super.onBackPressed();
        }
    }

    private class LoginUserTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return LoginInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);

            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {
            //String texto = "";
            if (result instanceof ArrayList) {
                //infoGral = (List<DtMessage>) result;
                List<DtResponse> infoGral = (List<DtResponse>) result;
            }

        }

    }

    private List<DtResponse> LoginInfoGralUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            }
        }
    }

    public List<DtResponse> readInfoGralJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readInfoGralMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<DtResponse> readInfoGralMessagesArray(JsonReader reader) throws IOException {
        List<DtResponse> messages = new ArrayList<DtResponse>();
        reader.beginArray();
        while (reader.hasNext()) {

            messages.add((DtResponse) readRESTMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public Object readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        Object res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("cuerpo")) {
                setDtUsuario(reader);
            } else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("ok")) {
                ok = reader.nextBoolean();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtResponse(ok, mensaje);
    }

    public void setDtUsuario(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;
        String apellido = null;
        String token = null;
        Date fechanacimiento = null;
        String documento = null;
        String correo = null;
        Boolean registrado = false;
        List<DtRol> roles = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("nombre")) {
                nombre = reader.nextString();
            } else if (name.equals("apellido")) {
                apellido = reader.nextString();
            } else if (name.equals("documento")) {
                documento = reader.nextString();
            } else if (name.equals("token")) {
                token = reader.nextString();
            } else if (name.equals("roles")) {
                roles = readDtRolArray(reader);
            } else if (name.equals("fechaNacimiento")) {
                Long jfecha = (reader.nextLong());
                if (jfecha != null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    try {
                        fechanacimiento = new Date(jfecha);
                    } catch (Exception e) {
                        Log.e("P02E05Enf", "Error fecha: " + e.getMessage().toString());
                    }
                }

            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        DtUsuario dtUsuario = DtUsuario.getInstance();
        dtUsuario.setId(id);
        dtUsuario.setNombre(nombre);
        dtUsuario.setApellido(apellido);
        dtUsuario.setCorreo(correo);
        dtUsuario.setDocumento(documento);
        dtUsuario.setFechanacimiento(fechanacimiento);
        dtUsuario.setToken(token);
        dtUsuario.setRoles(roles);


    }

    public List<DtRol> readDtRolArray(JsonReader reader) throws IOException {
        List<DtRol> roles = new ArrayList<DtRol>();

        reader.beginArray();
        while (reader.hasNext()) {
            roles.add(readDtRol(reader));
        }
        reader.endArray();
        return roles;
    }

    public DtRol readDtRol(JsonReader reader) throws IOException {
        Integer id = null;
        String nombre = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("nombre")) {
                nombre = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtRol(id, nombre);
    }

}