package com.madXdesign.superofertas.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.madXdesign.superofertas.R;
import com.madXdesign.superofertas.xml.SelectosCategoriaTask;
import com.madXdesign.superofertas.xml.SelectosParserXML;

public class CategoriaFragment extends ListFragment {
	
	private SelectosCategoriaTask parser;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// creamos el adaptador
		parser = new SelectosCategoriaTask(getResources().getString(R.string.urlSS), this);
		setListAdapter(new CategoriaAdapter(getActivity()));
		setListShownNoAnimation(false);
		parser.execute();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SelectosParserXML.Categoria o = 
				((CategoriaAdapter)getListAdapter()).getItem(position);
		OfertaFragment f = (OfertaFragment) 
						getFragmentManager().findFragmentById(R.id.ofertaFrm);
		f.refreshList(o.getNombre());
	}
	
	class CategoriaAdapter extends ArrayAdapter<SelectosParserXML.Categoria> {
		public CategoriaAdapter(Context context) {
			super(context
					, android.R.layout.simple_list_item_1);
		}
		
	}
}
