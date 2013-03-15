package org.h_naka.earthquakes;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

public class InfoAdapter extends ArrayAdapter<EarthQuakesInfo> {

    private LayoutInflater m_layoutInflater;
    private MainActivity m_activity;
    
	public InfoAdapter(Context context, int textViewResourceId,List<EarthQuakesInfo> objects) {
		super(context, textViewResourceId, objects);
        m_layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_activity = (MainActivity)context;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = m_layoutInflater.inflate(R.layout.info,null);
        }

        EarthQuakesInfo item = (EarthQuakesInfo)getItem(position);
        int color = getBackgroundColor(item.getQuake());

        TextView dateTextView = (TextView)convertView.findViewById(R.id.dateTextView);
        dateTextView.setText(item.getDate());

        TextView placeTextView = (TextView)convertView.findViewById(R.id.placeTextView);
        placeTextView.setText(item.getFocus());

        TextView otherTextView = (TextView)convertView.findViewById(R.id.otherTextView);
        StringBuilder builder = new StringBuilder();
        builder.append("震度:" + item.getQuake() + " ");
        builder.append("津波:" + item.getTsunami() + " ");
        builder.append("深さ:" + item.getDepth() + " ");
        builder.append("マグニチュード:" + item.getMagnitude());
        otherTextView.setText(builder.toString());

        if (color != -1) {
            dateTextView.setBackgroundColor(color);
            placeTextView.setBackgroundColor(color);
            otherTextView.setBackgroundColor(color);
        }
        
        return convertView;
    }

    private int getBackgroundColor(String quake) {
        int num = Integer.parseInt(quake.substring(0,1));
        int color = -1;
        switch(num) {
        case 1:
        case 2:
            color = m_activity.getResources().getColor(R.color.green);
            break;
        case 3:
        case 4:
            color = m_activity.getResources().getColor(R.color.yellow);
            break;
        case 5:
        case 6:
        case 7:
            color = m_activity.getResources().getColor(R.color.red);
            break;
        }

        return color;
    }
}
