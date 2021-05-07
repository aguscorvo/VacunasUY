package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;

import vacunasuy.componentemovil.obj.Usuario;

public class UserInfoActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView documento;
    TextView nombre;
    TextView apellido;
    TextView correo;
    TextView nacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        documento = findViewById(R.id.userinfo_idocumento);
        nombre = findViewById(R.id.userinfo_inombre);
        apellido = findViewById(R.id.userinfo_iapellido);
        correo = findViewById(R.id.userinfo_icorreo);
        nacimiento = findViewById(R.id.userinfo_ifnacimiento);

        Usuario usuario = Usuario.getInstance();

        documento.setText(usuario.getDocumento());
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellido());
        correo.setText(usuario.getCorreo());

        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
        nacimiento.setText(sdf.format(usuario.getFechanacimiento()));

        bottomNavigationView = findViewById(R.id.bottomNavigationViewUserInfo);
        bottomNavigationView.setSelectedItemId(R.id.menu_usuario);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intent2 = new Intent(UserInfoActivity.this, MainActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.menu_agenda:
                        Toast.makeText(UserInfoActivity.this, "Opción Agenda", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_notificacion:
                        Toast.makeText(UserInfoActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
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
}