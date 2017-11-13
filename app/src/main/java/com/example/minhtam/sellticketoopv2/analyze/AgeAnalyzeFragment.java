package com.example.minhtam.sellticketoopv2.analyze;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhtam.sellticketoopv2.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgeAnalyzeFragment extends Fragment {


    public AgeAnalyzeFragment() {
        // Required empty public constructor
    }

    BarChart barChartAgeAnalyze;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_age_analyze, container, false);
        barChartAgeAnalyze = (BarChart) view.findViewById(R.id.barChartAgeAnalyze);

        barChartAgeAnalyze.setDrawBarShadow(false);
        barChartAgeAnalyze.setDrawValueAboveBar(true);
        barChartAgeAnalyze.setMaxVisibleValueCount(50);
        barChartAgeAnalyze.setPinchZoom(false);
        barChartAgeAnalyze.setDrawGridBackground(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(21,8f));
        barEntries.add(new BarEntry(22,16f));
        barEntries.add(new BarEntry(23,49f));
        barEntries.add(new BarEntry(24,28f));
        barEntries.add(new BarEntry(25,14f));
        barEntries.add(new BarEntry(26,53f));
        barEntries.add(new BarEntry(27,31f));

        BarDataSet barDataSet = new BarDataSet(barEntries,"Du lieu");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        barChartAgeAnalyze.setData(data);


        return view;
    }

}
