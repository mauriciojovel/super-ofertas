package com.madXdesign.superofertas.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madXdesign.superofertas.R;

public class CarritoCompraFragment extends ListFragment {
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View rootView = null;
        rootView = inflater
                .inflate(R.layout.carrito_layout, container, false);
        return rootView;
    }
}
