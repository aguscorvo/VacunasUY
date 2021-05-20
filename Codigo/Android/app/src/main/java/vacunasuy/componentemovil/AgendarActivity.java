package vacunasuy.componentemovil;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import vacunasuy.componentemovil.obj.DtPlan;
import vacunasuy.componentemovil.obj.DtUsuario;

public class AgendarActivity extends AppCompatActivity {
    private static final String TAG = "VacunasUY";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    DtPlan planvacunas = null;
    DtUsuario usuario = DtUsuario.getInstance();
    Integer idPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        Bundle bundle = this.getIntent().getExtras();
        idPlan = bundle.getInt("IDPlan");


    }
}