package com.example.linj.jlinchart.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linj.jlinchart.R;

/**
 * @author JLin
 * @date 2019/1/31
 */
public class LineChart extends LinearLayout {
    /**
     * x轴 横坐标据线条
     */
    private Paint lineXPaint = new Paint();

    /**
     * y轴 指标数据线条
     */
    private Paint lineYPaint = new Paint();

    /**
     * y轴 指标数据线条
     */
    private Paint linePaint = new Paint();

    /**
     * title iconId
     */
    private Drawable icon;

    /**
     * 图表的标题
     */
    private String title;

    /**
     * 图表的单位
     */
    private String unit;

    /**
     * y轴 纵坐标 字体画笔
     */
    private Paint yAxisTextPaint = new Paint();

    /**
     * 条状图 value 字体颜色
     */
    private Paint valueTextPaint = new Paint();

    /**
     * y轴条状图颜色
     */
    private int lineColor;

    /**
     * 条状图 范围高度
     */
    private int mHeight;

    /**
     * 控件宽度
     */
    private int mWidth;

    /**
     * y轴间距
     */
    private float spacingY;

    /**
     * x轴处在的高度
     */
    private float xAxisHeight;

    /**
     * y轴值的最大值（传入的值的最大值）
     */
    private int maxValue = 30;

    /**
     * 横坐标的描述
     */
    private String[] abscissa;

    private Path path = new Path();

    /**
     * 条状图的值
     */
    private int[] value;

    int linePadding = (int) getContext().getResources().getDimension(R.dimen.px32);

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineChartStyle);
        if (ta != null) {
            title = ta.getString(R.styleable.LineChartStyle_line_title);
            icon = ta.getDrawable(R.styleable.LineChartStyle_line_icon);
            unit = ta.getString(R.styleable.LineChartStyle_line_unit);
            lineColor = ta.getInteger(R.styleable.LineChartStyle_line_color, Color.WHITE);
            ta.recycle();
        }

        lineXPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        lineXPaint.setColor(Color.parseColor("#d9e4f2"));
        lineXPaint.setStrokeWidth(2);

        lineYPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        lineYPaint.setColor(Color.parseColor("#66d9e4f2"));
        lineYPaint.setStrokeWidth(2);

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(2);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(200);
        linePaint.setPathEffect(cornerPathEffect);
        linePaint.setAntiAlias(true);

        valueTextPaint.setTextSize(dipToPx(getContext(), 13));
        valueTextPaint.setTextAlign(Paint.Align.CENTER);
        valueTextPaint.setColor(lineColor);
        valueTextPaint.setAntiAlias(true);

        yAxisTextPaint.setTextSize(dipToPx(getContext(), 13));
        yAxisTextPaint.setTextAlign(Paint.Align.CENTER);
        yAxisTextPaint.setColor(Color.parseColor("#999999"));
        yAxisTextPaint.setAntiAlias(true);

        addView(initTitle(context));
    }

    @SuppressLint("SetTextI18n")
    private View initTitle(Context context) {
        View view = inflate(context, R.layout.layout_chart_title, null);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.px80));
        view.setLayoutParams(layoutParams);

        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvUnit = view.findViewById(R.id.tv_unit);

        if (icon != null) {
            ivIcon.setVisibility(VISIBLE);
            ivIcon.setBackground(icon);
        } else {
            ivIcon.setVisibility(GONE);
        }
        tvTitle.setText(title);
        tvUnit.setText("（" + unit + "）");

        return view;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight() - (int) getContext().getResources().getDimension(R.dimen.px186);
        mWidth = getMeasuredWidth();
        xAxisHeight = getMeasuredHeight() - (int) getContext().getResources().getDimension(R.dimen.px74);
        spacingY = mHeight / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawYAxis(canvas);
        drawXAxis(canvas);
    }

    /**
     * 画Y轴 y轴的坐标值 和 坐标值对应的横线
     *
     * @param canvas canvas
     */
    private void drawYAxis(Canvas canvas) {
        int textStart = (int) getContext().getResources().getDimension(R.dimen.px44);
        int lineStart = (int) getContext().getResources().getDimension(R.dimen.px66);

        Paint.FontMetrics fontMetrics = yAxisTextPaint.getFontMetrics();
        canvas.drawText("0", textStart, xAxisHeight + fontMetrics.bottom, yAxisTextPaint);
        canvas.drawText(String.valueOf(maxValue), textStart, xAxisHeight - (3 * spacingY) + fontMetrics.bottom, yAxisTextPaint);
        canvas.drawText(String.valueOf((maxValue / 3) * 2), textStart, xAxisHeight - (2 * spacingY) + fontMetrics.bottom, yAxisTextPaint);
        canvas.drawText(String.valueOf(maxValue / 3), textStart, xAxisHeight - spacingY + fontMetrics.bottom, yAxisTextPaint);


        canvas.drawLine(lineStart, xAxisHeight, mWidth - (int) getContext().getResources().getDimension(R.dimen.px32), xAxisHeight, lineXPaint);

        canvas.drawLine(lineStart, xAxisHeight - (3 * spacingY),
                mWidth - linePadding, xAxisHeight - (3 * spacingY), lineYPaint);
        canvas.drawLine(lineStart, xAxisHeight - (2 * spacingY),
                mWidth - linePadding, xAxisHeight - (2 * spacingY), lineYPaint);
        canvas.drawLine(lineStart, xAxisHeight - spacingY,
                mWidth - linePadding, xAxisHeight - spacingY, lineYPaint);
    }

    /**
     * 画X轴的横线 以及 条状图
     *
     * @param canvas canvas
     */
    private void drawXAxis(Canvas canvas) {
        int startX = (int) getContext().getResources().getDimension(R.dimen.px66);
        // 条状图单位高度
        float perHeight = (float) mHeight / maxValue;
        // 视图宽度
        float columViewWidth = mWidth - (int) getContext().getResources().getDimension(R.dimen.px98);

        // 计算 点之间的间距
        float spacingX = columViewWidth / (abscissa.length - 1);

        for (int i = 0; i < abscissa.length; i++) {
            canvas.drawText(abscissa[i], startX + i * spacingX,
                    xAxisHeight + linePadding,
                    yAxisTextPaint);
            if (i == 0) {
                path.moveTo(startX, xAxisHeight - perHeight * value[i]);
            } else {
                path.lineTo(startX + i * spacingX, xAxisHeight - perHeight * value[i]);
            }
        }
        canvas.drawPath(path, linePaint);
    }

    /**
     * 传入横坐标的值
     *
     * @param abscissa 横坐标
     */
    public void setXDescription(String[] abscissa, int[] value) {
        int temp = value[0];
        for (int i = 1; i < value.length; i++) {
            if (temp < value[i]) {
                temp = value[i];
            }
        }
        if (temp % 3 != 0) {
            temp += (3 - temp % 3);
        }

        this.maxValue = temp;
        this.abscissa = abscissa;
        this.value = value;
        invalidate();
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
