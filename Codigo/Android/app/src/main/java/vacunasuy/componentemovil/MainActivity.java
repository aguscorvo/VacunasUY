package vacunasuy.componentemovil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.second.AddFechaNacimiento;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            DtUsuario usuario = DtUsuario.getInstance();
            switch (item.getItemId()){
                case R.id.menu_home:
                    return true;
                case R.id.menu_agenda:
                    Intent iagenda = new Intent(MainActivity.this, PlanVacunacion.class);
                    startActivity(iagenda);
                    return true;
                case R.id.menu_notificacion:
                    if(usuario.getRegistrado()){
                        Intent notificacioninfo = new Intent(MainActivity.this, NotificacionActivity.class);
                        startActivity(notificacioninfo);
                    }else{
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                        dialog.setTitle(R.string.info_title);
                        dialog.setMessage(getString(R.string.menu_LoginNotificacion));

                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_ingresar), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent userlogin = new Intent(MainActivity.this, GubUyActivity.class);
                                startActivity(userlogin);
                            }
                        });
                        dialog.show();
                    }
                    return true;
                case R.id.menu_vacunatorio:
                    Intent ivacunatroio = new Intent(MainActivity.this, VacunMapActivity.class);
                    startActivity(ivacunatroio);
                    return true;
                case R.id.menu_usuario:
                    if(usuario.getRegistrado()){
                        if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(MainActivity.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            Intent userinfo = new Intent(MainActivity.this, UserInfoActivity.class);
                            startActivity(userinfo);
                        }
                    }else{
                        Intent userlogin = new Intent(MainActivity.this, GubUyActivity.class);
                        startActivity(userlogin);
                    }
                    return true;
            }
            return false;
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}