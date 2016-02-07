package ch.bzs_surselva.schoolplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ch.bzs_surselva.schoolplanner.R;
import ch.bzs_surselva.schoolplanner.dto.LessonDisplayDto;

public class LessonOverviewAdapter extends ArrayAdapter<LessonDisplayDto>
{
    public LessonOverviewAdapter(Context context, ArrayList<LessonDisplayDto> data)
    {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LessonDisplayDto dataItem = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lesson_display_item, parent, false);
        }

        TextView textViewDay = (TextView) convertView.findViewById(R.id.textViewDay);
        //textViewDay.setText(dataItem.getSubject());

        return convertView;
    }
}
