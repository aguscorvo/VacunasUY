package vacunasuy.componentemovil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vacunasuy.componentemovil.obj.Usuario;

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
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_agenda:
                    Toast.makeText(MainActivity.this, "Opción Agenda", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_notificacion:
                    Toast.makeText(MainActivity.this, "Opción Notificación", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_vacunatorio:
                    Toast.makeText(MainActivity.this, "Opción Vacunatorio", Toast.LENGTH_SHORT).show();
                    Intent ivacunatroio = new Intent(MainActivity.this, VacunMapActivity.class);
                    startActivity(ivacunatroio);
                    return true;
                case R.id.menu_usuario:
                    Toast.makeText(MainActivity.this, "Opción Usuario", Toast.LENGTH_SHORT).show();
                    Usuario usuario = Usuario.getInstance();
                    if(usuario.getRegistrado()){
                        Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent2);

                    }else{
                        Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent3);
                    }
                    return true;
            }
            return false;
        });

    }
}