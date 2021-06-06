package vacunasuy.componentemovil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.List;

import vacunasuy.componentemovil.bd.AccesoBD;
import vacunasuy.componentemovil.bd.Mensaje;
import vacunasuy.componentemovil.obj.DtUsuario;
import vacunasuy.componentemovil.second.AddFechaNacimiento;

public class NotificacionActivity extends AppCompatActivity {
    TableLayout tableview;
    AccesoBD bd;
    DtUsuario dtUsuario = DtUsuario.getInstance();

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);
        tableview = findViewById(R.id.notificacionTableView);

        bd = bd = AccesoBD.getInstance(this);

        crearTitulos();
        agregarDatos(bd.getMensajesByUsuarioID(dtUsuario.getId()));

        bottomNavigationView = findViewById(R.id.notificacionBottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_notificacion);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            DtUsuario usuario = DtUsuario.getInstance();
            switch (item.getItemId()){
                case R.id.menu_home:
                    Intent ihome = new Intent(NotificacionActivity.this, MainActivity.class);
                    startActivity(ihome);
                    return true;
                case R.id.menu_agenda:
                    Intent iagenda = new Intent(NotificacionActivity.this, PlanVacunacion.class);
                    startActivity(iagenda);
                    return true;
                case R.id.menu_notificacion:
                    return true;
                case R.id.menu_vacunatorio:
                    Intent ivacunatroio = new Intent(NotificacionActivity.this, VacunMapActivity.class);
                    startActivity(ivacunatroio);
                    return true;
                case R.id.menu_usuario:
                    if(usuario.getRegistrado()){
                        if(usuario.getFechanacimiento()==null || usuario.getSectorlaboral() == null){
                            Intent fnintent = new Intent(NotificacionActivity.this, AddFechaNacimiento.class);
                            startActivity(fnintent);
                        }else {
                            Intent userinfo = new Intent(NotificacionActivity.this, UserInfoActivity.class);
                            startActivity(userinfo);
                        }
                    }else{
                        Intent userlogin = new Intent(NotificacionActivity.this, GubUyActivity.class);
                        startActivity(userlogin);
                    }
                    return true;
            }
            return false;
        });


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void crearTitulos(){
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

        //ID
        TextView textViewId = new TextView(this);
        textViewId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

        textViewId.setText(R.string.notificacion_tId);
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5,5,5,0);
        textViewId.setBackground(getDrawable(R.drawable.notificiaciones_tabla_titulo));
        tableRow.addView(textViewId);

        //Fecha
        TextView textViewFecha = new TextView(this);
        textViewFecha.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

        textViewFecha.setText(R.string.notificacion_tFecha);
        textViewFecha.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewFecha.setPadding(5,5,5,0);
        textViewFecha.setBackground(getDrawable(R.drawable.notificiaciones_tabla_titulo));
        tableRow.addView(textViewFecha);

        //Mensaje
        TextView textViewNombre = new TextView(this);
        textViewNombre.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

        textViewNombre.setText(R.string.notificacion_tTexto);
        textViewNombre.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewNombre.setPadding(5,5,5,0);
        textViewNombre.setBackground(getDrawable(R.drawable.notificiaciones_tabla_titulo));
        tableRow.addView(textViewNombre);
        //Ver
        TextView textViewVer = new TextView(this);
        textViewVer.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

        textViewVer.setText(R.string.notificacion_tVer);
        textViewVer.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewVer.setPadding(5,5,5,0);
        textViewVer.setBackground(getDrawable(R.drawable.notificiaciones_tabla_titulo));
        tableRow.addView(textViewVer);

        //Eliminar
        TextView textViewDel = new TextView(this);
        textViewDel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

        textViewDel.setText(R.string.notificacion_tEliminar);
        textViewDel.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewDel.setPadding(5,5,5,0);
        textViewDel.setBackground(getDrawable(R.drawable.notificiaciones_tabla_titulo));
        tableRow.addView(textViewDel);

        tableview.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f ));
    }

    private void agregarDatos(List<Mensaje> mensajes){

        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");

        for(Mensaje msg: mensajes){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));

            //ID
            TextView textViewId = new TextView(this);
            textViewId.setText(msg.getId().toString());
            textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewId.setPadding(5,5,5,0);
            tableRow.addView(textViewId);

            //Fecha
            TextView textViewFecha = new TextView(this);
            textViewFecha.setText(sdf.format(msg.getFecha()));
            textViewFecha.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            textViewFecha.setPadding(5,5,5,0);
            tableRow.addView(textViewFecha);

            //Mensaje
            TextView textViewNombre = new TextView(this);
            String texto = (msg.getTexto().length()<=15?msg.getTexto():msg.getTexto().substring(0, 15)+"...");
            textViewNombre.setText(texto);
            textViewNombre.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            textViewNombre.setPadding(5,5,5,0);
            tableRow.addView(textViewNombre);

            //VER
            ImageButton ver = new ImageButton(this);
            ver.setImageDrawable(getDrawable(android.R.drawable.ic_menu_view));
            ver.setBackgroundColor(getColor(R.color.design_default_color_background));

            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow currentRow = (TableRow) v.getParent();
                    TextView tvid = (TextView) currentRow.getChildAt(0);
                    Integer id = Integer.parseInt(tvid.getText().toString());

                    try {
                        Mensaje objmensaje = bd.getMensajeById(id);

                        AlertDialog dialog = new AlertDialog.Builder(NotificacionActivity.this).create();
                        dialog.setTitle(R.string.info_title);
                        dialog.setMessage(objmensaje.getTexto());

                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.alert_btn_neutral), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();

                    }catch (Exception e){
                        Toast.makeText(NotificacionActivity.this, getString(R.string.visualizacion_failed) + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            tableRow.addView(ver, new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
            ));

            //Eliminar
            ImageButton eliminar = new ImageButton(this);
            eliminar.setImageDrawable(getDrawable(android.R.drawable.ic_delete));
            eliminar.setBackgroundColor(getColor(R.color.design_default_color_background));

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow currentRow = (TableRow) v.getParent();
                    TextView tvid = (TextView) currentRow.getChildAt(0);
                    Integer id = Integer.parseInt(tvid.getText().toString());
                    try {
                        bd.deleteMensaje(bd.getMensajeById(id));
                        currentRow.removeAllViews();
                        Toast.makeText(NotificacionActivity.this, getString(R.string.successsful_deletion), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(NotificacionActivity.this, getString(R.string.deletion_failed) + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tableRow.addView(eliminar, new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
            ));

            tableview.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f));
        }

    }
}