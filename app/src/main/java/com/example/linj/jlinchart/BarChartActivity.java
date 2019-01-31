package com.example.linj.jlinchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.linj.jlinchart.chart.BarChart;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author JLin
 * @date 2019/1/31
 */
public class BarChartActivity extends AppCompatActivity {

    @BindView(R.id.bar_chart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        ButterKnife.bind(this);
        String[] x = {"第一周", "第二周", "第三周"};
        int[] y = {11, 16, 24};

        barChart.setXDescription(x, y);
    }
}
