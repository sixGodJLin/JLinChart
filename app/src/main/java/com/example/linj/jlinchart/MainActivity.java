package com.example.linj.jlinchart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author JLin
 * @date 2019/1/31
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bar_chart)
    TextView barChart;
    @BindView(R.id.line_chart)
    TextView lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bar_chart, R.id.line_chart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_chart:
                startActivity(new Intent(this, BarChartActivity.class));
                break;
            case R.id.line_chart:
                startActivity(new Intent(this, LineChartActivity.class));
                break;
            default:
                break;
        }
    }
}
