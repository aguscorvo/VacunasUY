package vacunasuy.componentemovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vacunasuy.componentemovil.obj.DtUsuario;

public class LoginActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_usuario);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    /*
                    case R.id.menu_home:
                        Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent2);
                        return true;

                     */
                    case R.id.menu_agenda:
                        Intent iagenda = new Intent(LoginActivity.this, PlanVacunacion.class);
                        startActivity(iagenda);
                        return true;
                    case R.id.menu_notificacion:
                        Toast.makeText(LoginActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_vacunatorio:
                        Intent ivacunatroio = new Intent(LoginActivity.this, VacunMapActivity.class);
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