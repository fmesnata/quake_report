package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater
                    .from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeView = listItem.findViewById(R.id.magnitude_text);
        magnitudeView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        GradientDrawable magnitudeViewBackground = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeViewBackground.setColor(magnitudeColor);

        String location = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;

        if (location.contains(LOCATION_SEPARATOR)) {
            String[] locationInfo = location.split(LOCATION_SEPARATOR);
            locationOffset = locationInfo[0] + LOCATION_SEPARATOR;
            primaryLocation = locationInfo[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = location;
        }

        TextView primaryLocationView = listItem.findViewById(R.id.primary_location_text);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = listItem.findViewById(R.id.location_offset_text);
        locationOffsetView.setText(locationOffset);

        Date date = new Date(currentEarthquake.getDate());

        TextView dateView = listItem.findViewById(R.id.date_text);
        dateView.setText(formatDate(date));

        TextView timeView = listItem.findViewById(R.id.time_text);
        timeView.setText(formatTime(date));

        return listItem;
    }

    private int getMagnitudeColor(double magnitude) {
        int colorResource;

        int m = (int) Math.floor(magnitude);

        switch (m) {
            case 0:
            case 1:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }

        return colorResource;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");

        return decimalFormatter.format(magnitude);
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");

        return dateFormatter.format(date);
    }

    private String formatTime(Date date) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        return timeFormatter.format(date);
    }
}
