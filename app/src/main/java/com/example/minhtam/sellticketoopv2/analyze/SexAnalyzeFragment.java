package com.example.minhtam.sellticketoopv2.analyze;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhtam.sellticketoopv2.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SexAnalyzeFragment extends Fragment {


    public SexAnalyzeFragment() {
        // Required empty public constructor
    }

    PieChart pieChartSexAnalyze;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sex_analyze, container, false);
        pieChartSexAnalyze = (PieChart) view.findViewById(R.id.pieChartSexAnalyze);

        pieChartSexAnalyze.setUsePercentValues(true);
        pieChartSexAnalyze.getDescription().setEnabled(false);
        pieChartSexAnalyze.setExtraOffsets(5,10,5,5);
        pieChartSexAnalyze.setDragDecelerationFrictionCoef(0.7f);
        pieChartSexAnalyze.setDrawHoleEnabled(false);
        pieChartSexAnalyze.setHoleColor(Color.WHITE);
        pieChartSexAnalyze.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValue = new ArrayList<>();
        yValue.add(new PieEntry(72,"Nam"));
        yValue.add(new PieEntry(56,"Nữ"));

        Description description = new Description();
        description.setText("Thống kê giới tính ");
        description.setTextSize(15);

        pieChartSexAnalyze.setDescription(description);
        PieDataSet dataSet = new PieDataSet(yValue,"sex analyze");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChartSexAnalyze.setData(data);

        return view;
    }

}
