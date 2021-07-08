package vacunasuy.componentemovil.second;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vacunasuy.componentemovil.PlanVacunacion;
import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.bd.Mensaje;
import vacunasuy.componentemovil.constant.ConnConstant;
import vacunasuy.componentemovil.obj.DtAgenda;
import vacunasuy.componentemovil.obj.DtDosis;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.obj.DtVacuna;
import vacunasuy.componentemovil.obj.DtVacunatorio;

import static vacunasuy.componentemovil.constant.ConnConstant.API_QR_URL;

public class CertificadoVacuna extends AppCompatActivity {
    private static final String TAG = "CertificadoVacuna";
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    Integer idPlan;
    String nombrePlan;
    DtUsuario dtUsuario = DtUsuario.getInstance();

    TextView cnombre;
    TextView cdocumento;
    TextView cvacuna;
    ImageView qrCodeIV;
    TableLayout tableview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        idPlan = bundle.getInt("IDPlan");
        nombrePlan = bundle.getString("nombrePlan");
        buscarCertificado();
        String title = getString(R.string.app_name) + " - " + getString(R.string.title_certificado);
        setTitle(title);
        setContentView(R.layout.activity_certificado_vacuna);

        cnombre = findViewById(R.id.certificado_iNombre);
        cdocumento = findViewById(R.id.certificado_iDocumento);
        cvacuna = findViewById(R.id.certificado_SubTitle);
        qrCodeIV = findViewById(R.id.idIVQrcode);
        tableview = findViewById(R.id.certificado_tableInfo);

        String nombre = dtUsuario.getNombre()+ " " + dtUsuario.getApellido();
        String vacuna = getString(R.string.certificado_tVacuna) + " " + nombrePlan;


        cnombre.setText(nombre);
        cdocumento.setText(dtUsuario.getDocumento());
        cvacuna.setText(vacuna);

        try {
            //Bitmap bitmap = encodeAsBitmap(qrToJSON().trim());
            Bitmap bitmap = encodeAsBitmap(qrToURL().trim());
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    private void addDosis(List<DtDosis> dosis){
        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
        Integer nrodosis = dosis.size();

        for(DtDosis dtd: dosis){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));

            String texto = getString(R.string.certificado_tDosis) +" " +
                    (nrodosis<10?"0":"") + nrodosis.toString() + "\n" +
                    getString(R.string.certificado_tFecha) + ":" + sdf.format(dtd.getFecha()) + "\n" +
                    getString(R.string.certificado_tVacuna) + ":" + dtd.getVacuna();

            TextView textViewD = new TextView(this);
            textViewD.setText(texto);
            textViewD.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewD.setPadding(5,5,5,0);
            textViewD.setGravity(Gravity.LEFT);
            textViewD.setBackground(getDrawable(R.drawable.tb_info_cel));

            tableRow.addView(textViewD);

            tableview.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f ));
            nrodosis -= 1;

        }



    }

    private void buscarCertificado() {
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        String stringUrl = ConnConstant.API_GETENFERMEDADVACUNAS_URL;

        stringUrl = stringUrl.replace("{idUsuario}", dtUsuario.getId().toString()).replace("{idEnfermedad}", idPlan.toString());

        Log.i(TAG, stringUrl);

        if (networkInfo != null && networkInfo.isConnected()) {
            new CertificadoVacuna.DownloadCertificadoTask().execute(stringUrl);
        }
    }

    private class DownloadCertificadoTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return CertificadoInfoGralUrl(urls[0]);
            } catch (IOException e) {
                return getString(R.string.err_recuperarpag);
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {

            if (result instanceof ArrayList) {
                List<DtDosis> dosis = (List<DtDosis>) result;
                addDosis(dosis);

            }else{
                AlertDialog dialog = new AlertDialog.Builder(CertificadoVacuna.this).create();
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
                        Intent iplan = new Intent(CertificadoVacuna.this, PlanVacunacion.class);
                        startActivity(iplan);
                    }
                });
                dialog.show();
            }
        }

    }

    private List<DtDosis> CertificadoInfoGralUrl(String myurl) throws IOException {
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            String authorization ="Bearer  " + dtUsuario.getToken();

            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", ConnConstant.USER_AGENT);
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

    public List<DtDosis> readInfoGralJsonStream(InputStream in) throws IOException {
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

    public List<DtDosis> readRESTMessage(JsonReader reader) throws IOException {
        Boolean ok = false;
        String mensaje = null;
        List<DtDosis> res = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ok")) {
                ok = reader.nextBoolean();
            }else if (name.equals("mensaje")) {
                mensaje = reader.nextString();
            } else if (name.equals("cuerpo") && reader.peek() != JsonToken.NULL) {
                res = readInfoGralDosisArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return res;
    }

    public List<DtDosis> readInfoGralDosisArray(JsonReader reader) throws IOException {
        List<DtDosis> dosis = new ArrayList<DtDosis>();
        reader.beginArray();
        while (reader.hasNext()) {
            dosis.add(readDosis(reader));
        }
        reader.endArray();
        return dosis;
    }

    public DtDosis readDosis(JsonReader reader) throws IOException {
        String nombre = null;
        Date fecha = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("nombre") && reader.peek() != JsonToken.NULL) {
                nombre = reader.nextString();
            } else if (name.equals("fecha") && reader.peek() != JsonToken.NULL) {
                String sfecha = reader.nextString();
                if(!sfecha.equalsIgnoreCase("")){
                    try {
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(sfecha);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DtDosis(null, null, fecha, nombre);
    }



    private String qrToJSON(){
        String res = "";

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("usuario", dtUsuario.getId());
            jsonObject.put("documento", dtUsuario.getDocumento());
            jsonObject.put("vacuna", idPlan);
            res = jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            res = "";
        }

        return res;
    }

    private String qrToURL(){
        String res = ConnConstant.API_QR_URL;
        res = res.replace("{idUsuario}", dtUsuario.getId().toString()).replace("{idEnfermedad}", idPlan.toString());

        return res;
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
        hintMap.put(EncodeHintType.MARGIN, 0);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);


        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, hintMap);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);

            return bitmap;
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
    }

}