package liram.dev.cryptocurrency.ui.marketcap.currencyDetails;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.databinding.CurrencyDetailsFragmentBinding;
import liram.dev.cryptocurrency.databinding.CustomDialogBinding;
import liram.dev.cryptocurrency.model.Crypto;
import liram.dev.cryptocurrency.utility.UnixDateFormatter;
import liram.dev.cryptocurrency.utility.Util;

public class CurrencyDetailsFragment extends Fragment{

    private CurrencyDetailsViewModel mViewModel;
    private UnixDateFormatter unixDateFormatter;
    private LineDataSet lineDataSet;
    private LineData data;
    private Crypto model;
    //Components:
    private Dialog dialog;
    private LineChart lineChart;
    private ImageView ivCrypto;
    private TextView tvCryptoName;
    private TextView tvCurrentPrice;
    private TextView tvLowPrice;
    private TextView tvHighPrice;
    private TextView tvChange24h;
    private Button purchaseCryptoBtn;
    private TextInputEditText inputValue;


    public static CurrencyDetailsFragment newInstance(Crypto model) {
        CurrencyDetailsFragment fragment = new CurrencyDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("model", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        model = getArguments().getParcelable("model");
        mViewModel = new CurrencyDetailsViewModel(model);
        CurrencyDetailsFragmentBinding binding = CurrencyDetailsFragmentBinding.inflate(inflater, container, false);
        binding.setCurrencyViewModel(mViewModel);
        View root = binding.getRoot();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel.getGetInvestDialog().observe(getViewLifecycleOwner(), isUserClickOnButton->{
            if (isUserClickOnButton){
               appearAmountDialog();

            }
        });

        mViewModel.getAmountInvesment().observe(getViewLifecycleOwner(), investValue->{

        });
        initialComponents(view);
        configureUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initialComponents(View view){
        dialog = new Dialog(getContext());
        lineChart = view.findViewById(R.id.line_chart);
        ivCrypto = view.findViewById(R.id.iv_crypto);
        tvCryptoName = view.findViewById(R.id.tv_crypto_name);
        tvCurrentPrice = view.findViewById(R.id.tv_current_price);
        tvLowPrice = view.findViewById(R.id.tv_low_price);
        tvHighPrice = view.findViewById(R.id.tv_high_price);
        purchaseCryptoBtn = view.findViewById(R.id.buy_btn);
        //init for dialog components:
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configureUI(){
        lineChart.setVisibility(View.GONE);
        Glide.with(getContext()).load(model.getImage()).into(ivCrypto);
        tvCryptoName.setText(model.getName());
        String currentPrice = Util.priceFormat(model.getCurrentPrice());
        tvCurrentPrice.setText(currentPrice + "US");
        String lowPrice = Util.priceFormat(model.getLow24H());
        tvLowPrice.setText(lowPrice);
        String highPrice = Util.priceFormat(model.getHigh24H());
        tvHighPrice.setText(highPrice);
        observe();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void observe() {
        mViewModel.getHistory().observe(getViewLifecycleOwner(), observe -> {
            lineChart.setVisibility(View.VISIBLE);
            List<Entry> entries = observe;
            lineDataSet = getUpLineDataSet(entries);
            data = new LineData(lineDataSet);
            lineChart.setData(data);

        });
        mViewModel.getUnixDateFormatter().observe(getViewLifecycleOwner(), observe -> {
            unixDateFormatter = new UnixDateFormatter(observe);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatChart(lineChart, lineDataSet, observe);
            }
            setUpChart();
        });
        mViewModel.getBuyCryptoMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        });
    mViewModel.getAmountInvesment().observe(getViewLifecycleOwner(), investValue->{

    });
    }
    public void setUpChart() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setAvoidFirstLastClipping(true);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setContentDescription("");
        lineChart.setNoDataText("");
        lineChart.setNoDataTextColor(R.color.red);
       // lineChart.setOnChartValueSelectedListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public LineDataSet getUpLineDataSet(List<Entry> entries) {

        LineDataSet dataSet = new LineDataSet(entries, "Price");
        dataSet.setColor(getContext().getColor(R.color.card_color));
        dataSet.setFillColor(0);
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(true);
        dataSet.setCircleColor(0);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawValues(false);
        dataSet.setCircleRadius(1);
        dataSet.setHighlightLineWidth(2);
        dataSet.setHighlightEnabled(true);
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setHighLightColor(0); // color for highlight indicator
        dataSet.getAxisDependency();
        return dataSet;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void formatChart(LineChart chart, LineDataSet dataSet, List<Long> unixDates) {

        int backgroundColor = getResources().getColor(R.color.card_color);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        unixDateFormatter = new UnixDateFormatter(unixDates);
        xAxis.setValueFormatter(unixDateFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);

        xAxis.setAxisLineColor(backgroundColor);
        xAxis.setAxisLineWidth(0.5f);


        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setAxisLineColor(backgroundColor);
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setAxisLineWidth(0.5f);
        //yAxisLeft.setValueFormatter();
        yAxisLeft.enableGridDashedLine(2,2,0);
        yAxisLeft.setTextSize(14f);
       // yAxisLeft.setSpaceMax(5f);
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setTextColor(Color.WHITE);

        chart.setDrawGridBackground(false);

        chart.setBackgroundColor(backgroundColor);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);
        chart.getDescription().setEnabled(false);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        dataSet.setDrawCircles(true);
        dataSet.setDrawFilled(false);
        dataSet.setDrawValues(false);
        //dataSet.setFillColor(backgroundColor);
        dataSet.setColors(getContext().getColor(R.color.purple_500));
        dataSet.setLineWidth(1f);

    }



    private void appearAmountDialog() {
//
//        dialog.setContentView(R.layout.custom_dialog);
        CustomDialogBinding binding = CustomDialogBinding.inflate(LayoutInflater.from(getContext()));
        dialog.setContentView(binding.getRoot());
        binding.setCryptoPurchase(mViewModel);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        TextView tvTitle = dialog.findViewById(R.id.tv_dialog_title);
        inputValue = dialog.findViewById(R.id.et_deposit);
        tvTitle.setText(getString(R.string.dialog_purchase_title));
        dialog.show();

        mViewModel.getAmountInvesment().observe(getViewLifecycleOwner(), value->{
            System.out.println(value);
        });
       // confirm.setOnClickListener(this);
    }

}