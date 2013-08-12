package com.madXdesign.superofertas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.madXdesign.superofertas.xml.SelectosParserXML;

public class CategoriaFragment extends ListFragment {
	
	private SelectosParserXML parser;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// creamos el adaptador
		//parser = new SelectosParserXML("http://168.243.106.118/deals/index.php/api/deals?version=0");
		CategoriaAdapter adpater = new CategoriaAdapter(getActivity());
		parser = new SelectosParserXML(adpater);
		parser.execute("http://168.243.106.118/deals/index.php/api/deals?version=0");
		setListAdapter(adpater);
	}
	
	class CategoriaAdapter extends ArrayAdapter<SelectosParserXML.Categoria> {

		public CategoriaAdapter(Context context) {
			super(context
					, android.R.layout.simple_list_item_1);
		}
		
	}
}
