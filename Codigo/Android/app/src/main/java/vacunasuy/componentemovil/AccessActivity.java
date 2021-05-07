package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtMessage;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class AccessActivity extends AppCompatActivity {
    private static final int PERMISOS_REQUERIDOS = 1;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    ProgressBar pb1;
    TextView msgprogress;
    Button breload;
    Button bgotomain;
    List<DtMessage> infoGral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        pb1 = findViewById(R.id.progressBar);
        msgprogress = findViewById(R.id.textViewpb);
        breload = findViewById(R.id.b_reload);
        bgotomain = findViewById(R.id.b_gotoMain);

        pb1.setVisibility(View.INVISIBLE);
        msgprogress.setVisibility(View.INVISIBLE);
        breload.setVisibility(View.INVISIBLE);
        bgotomain.setVisibility(View.INVISIBLE);


        breload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarConexion();
            }
        });

        bgotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, MainActivity.class);
                intent.putExtra("gralInfo", (Parcelable) infoGral);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permisos = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permisos, PERMISOS_REQUERIDOS);
            }else{
                realizarConexion();
            }
        }



    }

    private void realizarConexion() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_INFOGRAL_URL;

        pb1.setVisibility(View.VISIBLE);
        msgprogress.setVisibility(View.VISIBLE);
        msgprogress.setText(R.string.text_load);
        breload.setVisibility(View.INVISIBLE);

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadInfoGralTask().execute(stringUrl);
        } else {
            msgprogress.setText(getString(R.string.err_conectividad));
            pb1.setVisibility(View.INVISIBLE);

        }
    }

    private class DownloadInfoGralTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);

            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {
            String texto = "";
            if (result instanceof ArrayList) {
                infoGral = (List<DtMessage>) result;

            } else if (result instanceof String) {
                texto = (String) result; //getString(R.string.registros_err);
            }
            pb1.setVisibility(View.INVISIBLE);
            //msgprogress.setVisibility(View.INVISIBLE);
            msgprogress.setText(texto);
            breload.setVisibility(View.VISIBLE);
            bgotomain.setVisibility((View.VISIBLE));


        }

    }

    private List<DtMessage> downloadInfoGralUrl(String myurl) throws IOException {
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

    public List<DtMessage> readInfoGralJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readInfoGralMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<DtMessage> readInfoGralMessagesArray(JsonReader reader) throws IOException {
        List<DtMessage> messages = new ArrayList<DtMessage>();
        reader.beginArray();
        while (reader.hasNext()) {

            messages.add((DtMessage) readRESTMessage(reader));
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
                res = readDtMessage(reader);
            } else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("ok")) {
                ok = reader.nextBoolean();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public DtMessage readDtMessage(JsonReader reader) throws IOException {
        Double enfermedad = -1.0;
        Double vacuna = -1.0;
        Double plan = -1.0;
        Double agenda = -1.0;
        Double proximas = -1.0;
        Double vacunatorio = -1.0;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("enfermedad")) {
                enfermedad = reader.nextDouble();
            } else if (name.equals("vacuna")) {
                vacuna = reader.nextDouble();
            } else if (name.equals("plan")) {
                plan = reader.nextDouble();
            } else if (name.equals("agenda")) {
                agenda = reader.nextDouble();
            } else if (name.equals("proximas")) {
                proximas = reader.nextDouble();
            } else if (name.equals("vacunatorio")) {
                vacunatorio = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtMessage(enfermedad, vacuna, plan, agenda, proximas, vacunatorio);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISOS_REQUERIDOS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    realizarConexion();
                }else{
                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setTitle(R.string.access_title_err);
                    //dialog.setIcon(R.drawable.icon);
                    dialog.setMessage(getString(R.string.access_msg_err));
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener()
                        {
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissions, PERMISOS_REQUERIDOS);
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= JELLY_BEAN){
                                finishAffinity();
                            } else{
                                finish();
                            }
                        }
                    });
                    dialog.show();

                }

            }
        }
    }
}