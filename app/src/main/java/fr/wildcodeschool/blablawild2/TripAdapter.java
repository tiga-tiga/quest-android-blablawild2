package fr.wildcodeschool.blablawild2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bastienwcs on 15/04/18.
 */

public class TripAdapter extends ArrayAdapter<TripModel> {

    public TripAdapter(@NonNull Context context, @NonNull List<TripModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TripModel tripModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trip, parent, false);
        }

        // Lookup view for data population
        TextView tvDeparture = convertView.findViewById(R.id.tv_departure);
        TextView tvDestination = convertView.findViewById(R.id.tv_destination);
        TextView tvDate = convertView.findViewById(R.id.tv_date);

        // Populate the data into the template view using the data object
        tvDeparture.setText(tripModel.getDeparture());
        tvDestination.setText(tripModel.getDestination());
        tvDate.setText(tripModel.getDate());

        // Return the completed view to render on screen
        return convertView;
    }
}
