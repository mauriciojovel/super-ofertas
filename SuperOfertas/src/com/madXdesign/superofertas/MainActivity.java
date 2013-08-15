package com.madXdesign.superofertas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.madXdesign.superofertas.fragment.OfertaFragment.OnClickOfertaListener;
import com.madXdesign.superofertas.fragment.adapter.DepthPageTransformer;
import com.madXdesign.superofertas.fragment.adapter.PagerAdapter;
import com.madXdesign.superofertas.xml.SelectosParserXML.Oferta;

public class MainActivity extends ActionBarActivity 
        implements OnClickOfertaListener{
    private ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(new PagerAdapter(
		        getSupportFragmentManager()
		        ,getResources().getBoolean(R.bool.has_two_panes)));
		pager.setPageTransformer(true, new DepthPageTransformer());
		setTitle("");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    getMenuInflater().inflate(R.menu.carrito_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    boolean r = true;
	    switch (item.getItemId()) {
        case R.id.action_carrito_compra:
            pager.setCurrentItem(1);
            break;

        default:
            r = super.onOptionsItemSelected(item);
            break;
        }
	    return r;
	}
	
    @Override
    public void onClickOferta(Oferta oferta) {
        //pager.setCurrentItem(2);
    	Intent i = new Intent();
    	i.setClass(this, ProductoActivity.class);
    	startActivity(i);
    }

}
