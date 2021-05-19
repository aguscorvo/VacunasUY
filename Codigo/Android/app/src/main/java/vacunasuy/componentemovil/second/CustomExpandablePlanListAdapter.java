package vacunasuy.componentemovil.second;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.obj.DtPlan;

public class CustomExpandablePlanListAdapter  extends BaseExpandableListAdapter {
    private static final String TAG = "VacunasUY";
    private Context context;
    private List<DtPlan> expandableList;

    public CustomExpandablePlanListAdapter(Context context, List<DtPlan> expandableList) {
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
        Integer estadofi = (new Date()).compareTo(this.expandableList.get(groupPosition).getFechaInicio());
        Integer estadoff = (new Date()).compareTo(this.expandableList.get(groupPosition).getFechaFin());

        String listTitle =    this.context.getResources().getString(R.string.plan_group_title) +
                this.expandableList.get(groupPosition).getVacuna().getNombre();

        if(estadofi > 0){
            if(estadoff > 0){
                listTitle += " " + this.context.getResources().getString(R.string.plan_group_vencido);
            } else{
                listTitle += " " + this.context.getResources().getString(R.string.plan_group_vigente);
            }
        } else if(estadofi < 0){
            listTitle += " " + this.context.getResources().getString(R.string.plan_group_proximo);
        } else{
            listTitle += " " + this.context.getResources().getString(R.string.plan_group_vigente);
        }

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.plan_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.plan_group_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");

        final  String expandedListText = sdf.format(this.expandableList.get(groupPosition).getFechaInicio()) + " al " +
                sdf.format(this.expandableList.get(groupPosition).getFechaFin());

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.plan_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.plan_idperiodo);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
