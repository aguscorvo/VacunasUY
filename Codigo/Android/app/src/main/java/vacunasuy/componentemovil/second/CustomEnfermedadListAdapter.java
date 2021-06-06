package vacunasuy.componentemovil.second;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.obj.DtDepartamento;
import vacunasuy.componentemovil.obj.DtEnfermedad;

public class CustomEnfermedadListAdapter extends BaseAdapter {
    private Context context;
    private List<DtEnfermedad> list;


    public CustomEnfermedadListAdapter(Context context, List<DtEnfermedad> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String listTitle = this.list.get(position).getNombre();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.enfermedad_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.enfermedad_group_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }
}
