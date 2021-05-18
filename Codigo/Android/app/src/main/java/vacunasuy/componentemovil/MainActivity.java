package vacunasuy.componentemovil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
            switch (item.getItemId()){
                case R.id.menu_home:
                    return true;
                case R.id.menu_agenda:
                    Toast.makeText(MainActivity.this, "Opción Agenda", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_notificacion:
                    Toast.makeText(MainActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_vacunatorio:
                    Intent ivacunatroio = new Intent(MainActivity.this, VacunMapActivity.class);
                    startActivity(ivacunatroio);
                    return true;
                case R.id.menu_usuario:
                    DtUsuario usuario = DtUsuario.getInstance();
                    if(usuario.getRegistrado()){
                        if(usuario.getFechanacimiento()==null){
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