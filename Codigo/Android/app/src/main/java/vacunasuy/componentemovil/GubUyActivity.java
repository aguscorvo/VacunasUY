package vacunasuy.componentemovil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
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
import java.util.Date;
import java.util.List;

import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtResponse;
import vacunasuy.componentemovil.obj.DtRol;
import vacunasuy.componentemovil.obj.DtSectorLaboral;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.second.AddFechaNacimiento;

public class GubUyActivity extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    WebView navegador;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    //DtResponse infoGral;

    @SuppressLint({"SetJavaScriptEnabled", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gub_uy);

        navegador = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBarWebView);

        navegador.loadUrl(ConnConstant.API_GUBUY_URL);

        WebSettings wstts = navegador.getSettings();
        wstts.setJavaScriptEnabled(true);
        wstts.setDomStorageEnabled(true);
        wstts.setSupportZoom(true);
        wstts.setUserAgentString(ConnConstant.USER_AGENT);

        navegador.setWebViewClient(new MyWebViewClient());

        bottomNavigationView = findViewById(R.id.bottomNavigationViewLoging);
        bottomNavigationView.setSelectedItemId(R.id.menu_usuario);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    Intent intent2 = new Intent(GubUyActivity.this, MainActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.menu_agenda:
                    Intent iagenda = new Intent(GubUyActivity.this, PlanVacunacion.class);
                    startActivity(iagenda);

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
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //Log.i("GubUyActivity URL", url);
            if (ConnConstant.API_GUBUY_URL.equals(url)) {
                return false;
            } else if( Uri.parse(url).getQuery().contains("code")
                    && Uri.parse(url).getQuery().contains("state")
                    && !(Uri.parse(url).getQuery().contains("client_id"))){
                String access = Uri.parse(url).getQuery();
                Log.i("GubUyActivity", ConnConstant.API_USRLOGIN_URL + access);
                new LoginUserTask().execute(ConnConstant.API_USRLOGIN_URL + access);
                //GubUyActivity.super.onBackPressed();
                //Intent intent = new Intent(GubUyActivity.this, MainActivity.class);
                //startActivity(intent);

                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            navegador.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
            navegador.setVisibility(View.VISIBLE);
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

            AlertDialog dialog = new AlertDialog.Builder(GubUyActivity.this).create();
            dialog.setTitle(R.string.info_title);

            if (result instanceof DtResponse) {
                dialog.setMessage(((DtResponse) result).getMensaje());

                Log.i("onPostExecute", "response: " + ((DtResponse) result).getMensaje());
            }else if (result instanceof String){
                dialog.setMessage((String) result);
            } else {
                dialog.setMessage(getString(R.string.err_recuperarpag));
                Log.i(TAG, getString(R.string.err_recuperarpag));
            }
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.alert_btn_neutral), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    Intent iplan = new Intent(GubUyActivity.this, MainActivity.class);
                    startActivity(iplan);
                }
            });
            dialog.show();
        }

    }

    private DtResponse LoginInfoGralUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
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
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                setDtUsuario(reader);
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
        List<DtRol> roles = null;
        DtSectorLaboral sectorLaboral = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                id = reader.nextInt();
            } else if (name.equals("documento") && reader.peek() != JsonToken.NULL) {
                documento = reader.nextString();
            } else if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            } else if (name.equals("apellido") && reader.peek() != JsonToken.NULL) {
                apellido = reader.nextString();
            }  else if (name.equals("fechaNacimiento")&& reader.peek() != JsonToken.NULL) {
                String sfecha = reader.nextString();
                if(!sfecha.equalsIgnoreCase("")){
                    try {
                        fechanacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(sfecha);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } else if (name.equals("roles") && reader.peek() != JsonToken.NULL) {
                roles = readDtRolArray(reader);
            }  else if (name.equals("correo") && reader.peek() != JsonToken.NULL) {
                correo = reader.nextString();
            } else if (name.equals("sectorLaboral") && reader.peek() != JsonToken.NULL) {
                sectorLaboral = readDtSectorLaboral(reader);
            }else if (name.equals("token") && reader.peek() != JsonToken.NULL) {
                token = reader.nextString();
                Log.i(TAG, token);
            } else {
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
        dtUsuario.setSectorlaboral(sectorLaboral);
        dtUsuario.setRegistrado(true);


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

    public DtSectorLaboral readDtSectorLaboral(JsonReader reader) throws IOException {
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
        return new DtSectorLaboral(id, nombre);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}