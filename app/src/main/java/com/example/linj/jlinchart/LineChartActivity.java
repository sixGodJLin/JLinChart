package com.example.linj.jlinchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.linj.jlinchart.chart.LineChart;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author JLin
 * @date 2019/1/31
 */
public class LineChartActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);
        String[] x = {"1", "2", "3", "4", "5", "6"};
        int[] y = {11, 16, 25, 22, 19, 5};
        lineChart.setXDescription(x, y);
    }
}
