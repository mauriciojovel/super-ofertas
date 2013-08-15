package com.madXdesign.superofertas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madXdesign.superofertas.R;

public class TwoColumnsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Fragment f = getFragmentManager().findFragmentById(R.id.categoriaFrm);
        if(f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }
        f = getFragmentManager().findFragmentById(R.id.ofertaFrm);
        if(f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }
        View v = inflater.inflate(R.layout.two_columns_main, container, false);
        return v;
    }
}
