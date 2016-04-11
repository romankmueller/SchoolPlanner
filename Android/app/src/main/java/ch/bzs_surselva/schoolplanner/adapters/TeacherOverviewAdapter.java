package ch.bzs_surselva.schoolplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import ch.bzs_surselva.schoolplanner.R;
import ch.bzs_surselva.schoolplanner.dto.TeacherDto;
import ch.bzs_surselva.schoolplanner.dto.TeacherLookupDto;

public class TeacherOverviewAdapter extends ArrayAdapter<TeacherLookupDto>
{
    public TeacherOverviewAdapter(Context context, ArrayList<TeacherLookupDto> data)
    {
        super(context, 0, data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TeacherLookupDto dataItem = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.teacher_overview_item, parent, false);
             }

        TextView textViewLarge = (TextView) convertView.findViewById(R.id.textViewLarge);
        textViewLarge.setText(dataItem.getCaption());
        TextView textViewSmall = (TextView)convertView.findViewById(R.id.textViewSmall);
        textViewSmall.setText(dataItem.getCode());

        return convertView;
    }
}
