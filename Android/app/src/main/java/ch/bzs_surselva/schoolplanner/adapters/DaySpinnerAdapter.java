package ch.bzs_surselva.schoolplanner.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import ch.bzs_surselva.schoolplanner.R;
import ch.bzs_surselva.schoolplanner.dto.DayLookupDto;

public class DaySpinnerAdapter extends ArrayAdapter<DayLookupDto>
{
    public DaySpinnerAdapter(Context context, ArrayList<DayLookupDto> data)
    {
        super(context, R.layout.day_spinner_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DayLookupDto dataItem = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_spinner_item, parent, false);
        }

        TextView textViewText = (TextView) convertView.findViewById(R.id.textViewText);
        textViewText.setText(dataItem.getCaption());
        return convertView;
    }
}

