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
		
		// Se ejecuta solo cuando no es una saved instance
		if(savedInstanceState == null) {
		// creamos el adaptador
		parser = new SelectosCategoriaTask(getResources().getString(R.string.urlSS), this);
		setListAdapter(new CategoriaAdapter(getActivity()));
		setListShownNoAnimation(false);
		parser.execute();
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setItemChecked(0, true);
		}
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
					, R.layout.categoria_row_layout, android.R.id.text1);
		}
		
	}
}
