package com.madXdesign.superofertas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.madXdesign.superofertas.R;
import com.madXdesign.superofertas.utils.Formatter;
import com.madXdesign.superofertas.xml.SelectosCategoriaTask;
import com.madXdesign.superofertas.xml.SelectosOfertasTask;
import com.madXdesign.superofertas.xml.SelectosParserXML;
import com.madXdesign.superofertas.xml.SelectosParserXML.Categoria;

public class OfertaFragment extends ListFragment {
	
	private SelectosOfertasTask parser;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// creamos el adaptador
		//parser = new SelectosOfertasTask("http://168.243.106.118/deals/index.php/api/deals?version=0", this);
		setListAdapter(new OfertaAdapter(getActivity()));
		setListShownNoAnimation(false);
		//parser.execute();
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu, inflater);
	    getActivity().getMenuInflater().inflate(R.menu.main, menu);
	    MenuItem spinnerItem = null;
	    spinnerItem = menu.findItem( R.id.action_filter);
	    View view = spinnerItem.getActionView();
	    if (view instanceof Spinner) {
            final Spinner spinner = (Spinner) view;
            ArrayAdapter<Categoria> adapter = 
                    new ArrayAdapter<SelectosParserXML.Categoria>(getActivity()
                            , android.R.layout.simple_list_item_1);
            SelectosCategoriaTask task = new SelectosCategoriaTask("http://168.243.106.118/deals/index.php/api/deals?version=0", adapter);
            task.execute();
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                        int position, long id) {
                    Categoria a = (Categoria) spinner.getAdapter().getItem(position);
                    setListShownNoAnimation(false);
                    if(a.getNombre().equalsIgnoreCase("todos")) {
                        parser = new SelectosOfertasTask("http://168.243.106.118/deals/index.php/api/deals?version=0", OfertaFragment.this);
                        parser.execute();
                    } else {
                        parser = new SelectosOfertasTask("http://168.243.106.118/deals/index.php/api/deals?version=0", OfertaFragment.this);
                        parser.execute(a.getNombre());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    
                }
            });
        }
	}
	
	class OfertaAdapter extends ArrayAdapter<SelectosParserXML.Oferta> {
		public OfertaAdapter(Context context) {
			super(context
					, R.layout.oferta_row_layout);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View item = null;
		    OfertaViewHolder holder = null;
		    SelectosParserXML.Oferta o = getItem(position);
		    if(convertView == null) {
    		    LayoutInflater inflater = getActivity().getLayoutInflater();
                item = inflater.inflate(R.layout.oferta_row_layout, null);
                holder = new OfertaViewHolder();
                holder.titulo = (TextView) item.findViewById(R.id.tituloTxt);
                holder.precioRegular = 
                        (TextView) item.findViewById(R.id.precioRegularTxt);
                holder.ahorro = (TextView) item.findViewById(R.id.ahorroTxt);
                holder.precioOferta = 
                        (TextView) item.findViewById(R.id.precioOfertaTxt);
                item.setTag(holder);
		    } else {
		        item = convertView;
		        holder = (OfertaViewHolder) item.getTag();
		    }
		    holder.titulo.setText(firstCap(o.getTitulo()));
		    if(o.getPrecionNormal() == 0) {
		        holder.precioRegular.setText(" - ");
		    } else {
		        holder.precioRegular.setText(Formatter.money(o.getPrecionNormal()));
		    }
		    if(o.getAhorro() == 0) {
		        holder.ahorro.setText(" - ");
		    } else {
		        holder.ahorro.setText(Formatter.money(o.getAhorro()));
		    }
		    if(o.getPrecioOferta() == 0) {
		        holder.precioOferta.setText(((int)o.getDescuento())+"%");
		    } else {
		        holder.precioOferta.setText(Formatter.money(o.getPrecioOferta()));
		    }
		    return item;
		}
		
		public String firstCap(String text) {
		    String res = text.trim();
		    res = res.substring(0,1)
		            .toUpperCase(getResources().getConfiguration().locale)
		            + res.substring(1)
		                .toLowerCase(getResources().getConfiguration().locale);
		    return res;
		}
	}
	
	static class OfertaViewHolder {
	    TextView titulo;
	    TextView precioRegular;
	    TextView precioOferta;
	    TextView ahorro;
	}
}