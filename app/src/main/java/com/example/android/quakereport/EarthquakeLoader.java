package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;


class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        return QueryUtils.fetchEarthquakes(url);
    }
}
