package ch.bzs_surselva.schoolplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ch.bzs_surselva.schoolplanner.R;
import ch.bzs_surselva.schoolplanner.dto.RoomLookupDto;

/**
 * Created by conrad on 04.03.2016.
 */
public class RoomOverviewAdapter extends ArrayAdapter<RoomLookupDto>
{
    public RoomOverviewAdapter(Context context, ArrayList <RoomLookupDto> data)
    {
        super ( context, 0, data);
    }

    @Override

    public View getView (int position, View convertView, ViewGroup parent)
    {
       RoomLookupDto dataItem = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_overview_item, parent, false);
        }

        TextView textViewLarge = (TextView) convertView.findViewById(R.id.TextViewLarge);
        textViewLarge.setText(dataItem.getCode());
        TextView textViewSmall = (TextView) convertView.findViewById(R.id.TextViewSmall);
        textViewSmall.setText(dataItem.getCaption());


         return convertView;
    }
}
