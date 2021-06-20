package com.azhar.rajaongkir.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.azhar.rajaongkir.R;
import com.azhar.rajaongkir.data.cost.DataType;
import com.azhar.rajaongkir.ui.ongkir.MainContract;

import java.util.List;

public class ProfileFragment extends Fragment implements MainContract.View {

    public ProfileFragment() {

    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onLoadingCost(boolean loadng, int progress) {

    }

    @Override
    public void onResultCost(List<DataType> data, List<String> courier) {

    }

    @Override
    public void onErrorCost() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public String getOrigin() {
        return null;
    }

    @Override
    public String getDestination() {
        return null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
