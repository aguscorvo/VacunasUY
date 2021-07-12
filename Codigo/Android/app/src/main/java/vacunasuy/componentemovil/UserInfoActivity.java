package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;

import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.second.AddFechaNacimiento;

public class UserInfoActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView documento;
    TextView nombre;
    TextView apellido;
    TextView correo;
    TextView nacimiento;
    TextView sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getString(R.string.app_name) + " - " + getString(R.string.title_perfil);
        setTitle(title);
        setContentView(R.layout.activity_user_info);

        documento = findViewById(R.id.userinfo_idocumento);
        nombre = findViewById(R.id.userinfo_inombre);
        apellido = findViewById(R.id.userinfo_iapellido);
        correo = findViewById(R.id.userinfo_icorreo);
        nacimiento = findViewById(R.id.userinfo_ifnacimiento);
        sector = findViewById(R.id.userinfo_isectorlaboral);

        DtUsuario usuario = DtUsuario.getInstance();

        documento.setText(usuario.getDocumento());
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellido());
        correo.setText(usuario.getCorreo());
        sector.setText(usuario.getSectorlaboral().getNombre());

        if(usuario.getFechanacimiento()!=null){
            SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
            nacimiento.setText(sdf.format(usuario.getFechanacimiento()));
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationViewUserInfo);
        bottomNavigationView.setSelectedItemId(R.id.menu_usuario);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    /*
                    case R.id.menu_home:
                        Intent intent2 = new Intent(UserInfoActivity.this, MainActivity.class);
                        startActivity(intent2);
                        return true;

                     */
                    case R.id.menu_agenda:
                        Intent iagenda = new Intent(UserInfoActivity.this, PlanVacunacion.class);
                        startActivity(iagenda);
                        return true;
                    case R.id.menu_notificacion:
                        if(usuario.getRegistrado()){
                            Intent notificacioninfo = new Intent(UserInfoActivity.this, NotificacionActivity.class);
                            startActivity(notificacioninfo);
                        }else{
                            AlertDialog dialog = new AlertDialog.Builder(UserInfoActivity.this).create();
                            dialog.setTitle(R.string.info_title);
                            dialog.setMessage(getString(R.string.menu_LoginNotificacion));

                            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.plan_ingresar), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent userlogin = new Intent(UserInfoActivity.this, GubUyActivity.class);
                                    startActivity(userlogin);
                                }
                            });
                            dialog.show();
                        }
                        return true;
                    case R.id.menu_vacunatorio:
                        Intent ivacunatroio = new Intent(UserInfoActivity.this, VacunMapActivity.class);
                        startActivity(ivacunatroio);
                        return true;
                    case R.id.menu_usuario:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}