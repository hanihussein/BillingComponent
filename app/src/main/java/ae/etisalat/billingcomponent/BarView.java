package ae.etisalat.billingcomponent;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

public class BarView extends ConstraintLayout {

    public interface onBarClickListener {

        void onBarClick(BarChartEntry barChartEntry);
    }

    private VerticalProgressBar progressBar;

    private AppCompatTextView title;

    private onBarClickListener onBarClickListener;


    public BarView(Context context) {
        super(context);

        initView(context);
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);

    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.item_progressbar, this, true);

        progressBar = rootView.findViewById(R.id.progressBar);

        title = rootView.findViewById(R.id.title);

    }

    public void drawBar(final BarChartEntry barChartEntry, int biggestBarsValue) {

        LayoutParams layoutParams = (LayoutParams) progressBar.getLayoutParams();

        layoutParams.height = (layoutParams.height * barChartEntry.getMaxValue()) / biggestBarsValue;

        progressBar.setLayoutParams(layoutParams);

        title.setText(barChartEntry.getTitle());

        ShapeDrawable shape =
                new ShapeDrawable(new RoundRectShape(new float[]{0, 0, 20, 20, 20, 20, 0, 0}, null, null));

        shape.getPaint().setStyle(Paint.Style.FILL);
        shape.getPaint().setColor(barChartEntry.getMaxValueColor());

        ShapeDrawable progressDrawable =
                new ShapeDrawable(new RoundRectShape(new float[]{0, 0, 20, 20, 20, 20, 0, 0}, null, null));

        progressDrawable.getPaint().setColor(barChartEntry.getProgressValueColor());
        progressDrawable.getPaint().setStyle(Paint.Style.FILL);
        ClipDrawable progress = new ClipDrawable(progressDrawable, Gravity.START, ClipDrawable.HORIZONTAL);

        LayerDrawable layerDrawable = null;

        if (barChartEntry.getBillingModeType() == BillingModeType.PAYMENT_MODE_ACTIVE) {

            layerDrawable = new LayerDrawable(new Drawable[]{
                    shape, progress});

        } else if (barChartEntry.getBillingModeType() == BillingModeType.PAYMENT_MODE_INACTIVE) {

            layerDrawable = new LayerDrawable(new Drawable[]{
                    progress});
        }

        progressBar.setProgressDrawable(layerDrawable);
        progressBar.setEnabled(false);
        progressBar.setMax(barChartEntry.getMaxValue());
        progressBar.setProgress(barChartEntry.getProgressValue());

        findViewById(R.id.pro_component_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onBarClickListener != null)
                    onBarClickListener.onBarClick(barChartEntry);

//                GradientDrawable gradientDrawable = new GradientDrawable();
//                gradientDrawable.setCornerRadii(new float[]{15, 15, 15, 15, 15, 15, 15, 15});
//                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
//                gradientDrawable.setColor(Color.WHITE);
//                gradientDrawable.setStroke(3, Color.LTGRAY);
//
//                findViewById(R.id.pro_component_view).setBackgroundDrawable(gradientDrawable);

            }
        });


    }

}
