package com.example.minhtam.sellticketoopv2.analyze;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhtam.sellticketoopv2.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaleAnalyzeFragment extends Fragment {


    public SaleAnalyzeFragment() {
        // Required empty public constructor
    }

    LineChart lineChartSaleAnalyze;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sale_analyze, container, false);

        lineChartSaleAnalyze = (LineChart) view.findViewById(R.id.lineChartSaleAnalyze);

        lineChartSaleAnalyze.setDragEnabled(true);
        lineChartSaleAnalyze.setScaleEnabled(false);

        ArrayList<Entry> yValue = new ArrayList<>();

        yValue.add(new Entry(20,60f));
        yValue.add(new Entry(21,73f));
        yValue.add(new Entry(22,641f));
        yValue.add(new Entry(23,52f));
        yValue.add(new Entry(24,42f));
        yValue.add(new Entry(25,64f));

        LineDataSet set1 = new LineDataSet(yValue,"Dữ liệu bán vé");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        lineChartSaleAnalyze.setData(data);

        return view;
    }

}
