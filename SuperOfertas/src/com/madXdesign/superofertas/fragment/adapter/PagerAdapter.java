package com.madXdesign.superofertas.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.madXdesign.superofertas.fragment.CarritoCompraFragment;
import com.madXdesign.superofertas.fragment.OfertaFragment;
import com.madXdesign.superofertas.fragment.ProductoFragment;
import com.madXdesign.superofertas.fragment.TwoColumnsFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private boolean twoColumns;
    public PagerAdapter(FragmentManager f, boolean twoColumns) {
        super(f);
        this.twoColumns = twoColumns;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment r = null;
        
        if(position == 0){
            /*r = Fragment.instantiate(f
                    , twoColumns?TwoColumnsFragment.class.getName()
                            :OfertaFragment.class.getName());*/
            if(twoColumns) {
                r = new TwoColumnsFragment();
            } else {
                r = new OfertaFragment();
                //Fragment.instantiate(f,OfertaFragment.class.getName());
            }
        } else if(position == 1) {
            r = new CarritoCompraFragment();/*Fragment.instantiate(f
                    , CarritoCompraFragment.class.getName());*/
        } else {
            r = new ProductoFragment();
            /*Fragment.instantiate(f, ProductoFragment.class.getName());*/
        }
        
        return r;
    }
    
    

    @Override
    public int getCount() {
        return 3;
    }

}
