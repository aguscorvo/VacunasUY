package vacunasuy.componentemovil.second;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.obj.DtAgenda;
import vacunasuy.componentemovil.obj.DtPlan;

public class CustomExpandableAgendaListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CustomExpandableAgendaListAdapter";
    private Context context;
    private List<DtAgenda> expandableList;

    public CustomExpandableAgendaListAdapter(Context context, List<DtAgenda> expandableList) {
        this.context = context;
        this.expandableList = expandableList;
    }

    @Override
    public int getGroupCount() {
        return this.expandableList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.expandableList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.expandableList.get(groupPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle =    this.context.getResources().getString(R.string.certificado_tVacuna) + " - " +
                this.expandableList.get(groupPosition).getVacuna().getNombre();

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.miagenda_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.miagenda_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd HH:mm");

        final  String expandedListFecha = this.context.getResources().getString(R.string.notificacion_tFecha) + ": " +
                sdf.format(this.expandableList.get(groupPosition).getFecha()) ;
        final  String expandedListVacunatorio = this.context.getResources().getString(R.string.agenda_AlertVacunatoio) + ": " +
               this.expandableList.get(groupPosition).getVacunatorio().getNombre() ;

        final  String expandedListPuesto = this.context.getResources().getString(R.string.agenda_AlertPuesto) + ": " +
               this.expandableList.get(groupPosition).getVacunatorio().getPuestos().get(0).getNumero().toString();

        final  String expandedListLoc = this.expandableList.get(groupPosition).getVacunatorio().getUbicacion().getNombre_departamento() +
                "-" + this.expandableList.get(groupPosition).getVacunatorio().getUbicacion().getNombre_localidad();



        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.miagenda_item, null);
        }
        TextView expandedListTextfecha = (TextView) convertView.findViewById(R.id.miagenda_fecha);
        expandedListTextfecha.setText(expandedListFecha);

        TextView expandedListTextVac = (TextView) convertView.findViewById(R.id.miagenda_Vacunatorio);
        expandedListTextVac.setText(expandedListVacunatorio);

        TextView expandedListTextLoc = (TextView) convertView.findViewById(R.id.miagenda_VacunatorioLoc);
        expandedListTextLoc.setText(expandedListLoc);

        TextView expandedListTextDir = (TextView) convertView.findViewById(R.id.miagenda_VacunatorioDir);
        expandedListTextDir.setText(this.expandableList.get(groupPosition).getVacunatorio().getUbicacion().getDireccion());

        TextView expandedListTextPuesto = (TextView) convertView.findViewById(R.id.miagenda_VacunatorioPuesto);
        expandedListTextPuesto.setText(expandedListPuesto);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
