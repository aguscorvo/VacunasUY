package vacunasuy.componentemovil.second;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import vacunasuy.componentemovil.R;
import java.util.List;

import vacunasuy.componentemovil.obj.DtDepartamento;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DtDepartamento> expandableList;

    public CustomExpandableListAdapter(Context context, List<DtDepartamento> expandableList) {
        this.context = context;
        this.expandableList = expandableList;
    }

    @Override
    public int getGroupCount() {
        return this.expandableList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableList.get(groupPosition).getLocalidades().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableList.get(groupPosition).getLocalidades().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.expandableList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.expandableList.get(groupPosition).getLocalidades().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = this.expandableList.get(groupPosition).getNombre();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.depto_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.departamento_group_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final  String expandedListText = this.expandableList.get(groupPosition).getLocalidades().get(childPosition).getNombre();

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.depto_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.localidad_name);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
