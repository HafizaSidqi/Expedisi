package com.azhar.rajaongkir.ui.ongkir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.rajaongkir.R;
import com.azhar.rajaongkir.data.cost.DataType;
import com.azhar.rajaongkir.ui.search.SearchCityActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class OngkirFragment extends Fragment implements MainContract.View {

    private static final int REQUEST_SOURCE = 1;
    private static final int REQUEST_DESTINATION = 2;

    private String source_id = "";
    private String destination_id = "";

    private MainPresenter presenter;
    private OngkirAdapter adapter;

    private List<DataType> data = new ArrayList<>();
    private List<String> courier = new ArrayList<>();

    TextInputEditText inputKotaAsal, inputKotaTujuan;
    Button btnSubmit;
    LinearLayout llMain;
    RecyclerView rvMain;
    ProgressBar progressBar;



    @Override
    public void onViewCreate(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        inputKotaAsal = v.findViewById(R.id.inputKotaAsal);
        inputKotaTujuan = v.findViewById(R.id.inputKotaTujuan);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        llMain = v.findViewById(R.id.llMain);
        rvMain = v.findViewById(R.id.rvMain);
        progressBar = v.findViewById(R.id.progressBar);

        presenter = new MainPresenter(this);

        adapter = new OngkirAdapter(getContext(), data, courier);
        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(new LinearLayoutManager(v.getContext()));

        inputKotaAsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), SearchCityActivity.class);
                intent.putExtra("requestCode", REQUEST_SOURCE);
                startActivityForResult(intent, REQUEST_SOURCE);
            }
        });

        inputKotaTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), SearchCityActivity.class);
                intent.putExtra("requestCode", REQUEST_DESTINATION);
                startActivityForResult(intent, REQUEST_DESTINATION);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                courier.clear();
                presenter.setupENV(getOrigin(), getDestination(), 1000);
            }
        });
    }

    @Override
    public void onLoadingCost(boolean loadng, int progress) {
        if (loadng) {
            llMain.setVisibility(View.VISIBLE);
            rvMain.setVisibility(View.GONE);
            progressBar.setProgress(progress);
        } else {
            progressBar.setProgress(progress);
            llMain.setVisibility(View.GONE);
            rvMain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResultCost(List<DataType> data, List<String> courier) {
        this.data.addAll(data);
        this.courier.addAll(courier);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorCost() {
        rvMain.setVisibility(View.GONE);
        llMain.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getOrigin() {
        return source_id;
    }

    @Override
    public String getDestination() {
        return destination_id;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SOURCE && resultCode == RESULT_OK) {
            inputKotaAsal.setText(data.getStringExtra("city"));
            source_id = data.getStringExtra("city_id");
        } else if (requestCode == REQUEST_DESTINATION && resultCode == RESULT_OK) {
            inputKotaTujuan.setText(data.getStringExtra("city"));
            destination_id = data.getStringExtra("city_id");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongkir, container, false);
    }

}
