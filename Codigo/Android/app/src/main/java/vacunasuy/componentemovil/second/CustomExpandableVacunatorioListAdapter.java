package vacunasuy.componentemovil.second;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.obj.DtDepartamento;
import vacunasuy.componentemovil.obj.DtVacunatorio;

public class CustomExpandableVacunatorioListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DtVacunatorio> expandableList;

    public CustomExpandableVacunatorioListAdapter(Context context, List<DtVacunatorio> expandableList) {
        this.context = context;
        this.expandableList = expandableList;
    }

    @Override
    public int getGroupCount() {
        return this.expandableList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableList.get(groupPosition).getPuestos().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableList.get(groupPosition).getPuestos().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.expandableList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.expandableList.get(groupPosition).getPuestos().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = this.expandableList.get(groupPosition).getNombre();
        String listLocalidad = this.expandableList.get(groupPosition).getUbicacion().getNombre_departamento() + "- " +
                this.expandableList.get(groupPosition).getUbicacion().getNombre_localidad();
        String listDireccion = this.expandableList.get(groupPosition).getUbicacion().getDireccion();

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.vacunatorio_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.vacunatorio_groupname);
        TextView localidadTitleTextView = (TextView) convertView.findViewById(R.id.vacunatorio_groupnameloc);
        TextView direccionTitleTextView = (TextView) convertView.findViewById(R.id.vacunatorio_groupnamedir);

        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        localidadTitleTextView.setText(listLocalidad);
        direccionTitleTextView.setText(listDireccion);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final  String expandedListText = context.getResources().getString(R.string.agenda_puestoText) + " " +
                this.expandableList.get(groupPosition).getPuestos().get(childPosition).getNumero().toString();

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.vacunatorio_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.agendaPuesto);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
