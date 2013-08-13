package com.madXdesign.superofertas.xml;

import java.io.Serializable;
import java.util.List;

import com.madXdesign.superofertas.xml.SelectosParserXML.Categoria;


import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;


public class SelectosCategoriaTask extends AsyncTask<String, Void, List<SelectosParserXML.Categoria>> 
            implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ListFragment listener;
    
    private SelectosParserXML parser;
    
    private ArrayAdapter<SelectosParserXML.Categoria> adapter;
    
    public SelectosCategoriaTask(String url, ArrayAdapter<SelectosParserXML.Categoria> adapter) {
        super();
        this.adapter = adapter;
        this.parser = new SelectosParserXML(url);
    }

    public SelectosCategoriaTask(String url, ListFragment listener)
    {
        super();
        this.listener = listener;
        this.parser = new SelectosParserXML(url);
    }

    @Override
    protected List<SelectosParserXML.Categoria> doInBackground(String... params) {
        List<SelectosParserXML.Categoria> c = this.parser.findAllCategorias();
        int contador = 0;
        for (Categoria categoria : c) {
            contador += categoria.getCantidad();
        }
        c.add(0, parser.new Categoria("Todos", contador, null));
        return c;
    }
    
    @Override
    protected void onPostExecute(List<SelectosParserXML.Categoria> result) {
        super.onPostExecute(result);
        if(listener != null) {
            @SuppressWarnings("unchecked")
            ArrayAdapter<Object> a 
                                = (ArrayAdapter<Object>) listener.getListAdapter();
            listener.setListShownNoAnimation(true);
            a.clear();
            for (Categoria categoria : result) {
                a.add(categoria);
            }
            a.notifyDataSetChanged();
        } else if(adapter != null) {
            adapter.clear();
            for (Categoria categoria : result) {
                adapter.add(categoria);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
