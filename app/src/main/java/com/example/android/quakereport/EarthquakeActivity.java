/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=20";
    private EarthquakeAdapter adapter;
    private TextView emptyView;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        setEarthQuakeList(new ArrayList<>());

        NetworkInfo networkInfo = isInternetAvailable();
        emptyView = (TextView) findViewById(R.id.empty_view);

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
            spinner = (ProgressBar) findViewById(R.id.spinner);
            spinner.setVisibility(View.VISIBLE);
        } else {
            emptyView.setText(R.string.no_internet_connection);
        }
    }

    private void setEarthQuakeList(List<Earthquake> earthquakes) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(emptyView);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener((adapterView, view, position, l) -> {

            Earthquake earthquake = earthquakes.get(position);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(earthquake.getDetailsUrl()));
            startActivity(i);
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        spinner.setVisibility(View.GONE);
        if (earthquakes != null && !earthquakes.isEmpty()) {
            adapter.clear();
            adapter.addAll(earthquakes);
        } else {
            emptyView.setText(R.string.no_earthquake_found);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }

    private NetworkInfo isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.d("IsInternetAvailable", connectivityManager.toString());
        return connectivityManager.getActiveNetworkInfo();
    }

}
