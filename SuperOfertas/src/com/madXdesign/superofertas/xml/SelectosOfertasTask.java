package com.madXdesign.superofertas.xml;

import java.io.Serializable;
import java.util.List;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class SelectosOfertasTask extends AsyncTask<String, Void, List<Oferta>> 
            implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ListFragment listener;
    
    private SelectosParserXML parser;
    
    public SelectosOfertasTask(String url, ListFragment listener)
    {
        super();
        this.listener = listener;
        this.parser = new SelectosParserXML(url);
    }

    @Override
    protected List<Oferta> doInBackground(String... params) {
        String categoria = null;
        if(params.length > 0) {
            categoria = params[0];
        }
        return this.parser.findOfertas(categoria);
    }
    
    @Override
    protected void onPostExecute(List<Oferta> result) {
        super.onPostExecute(result);
        @SuppressWarnings("unchecked")
        ArrayAdapter<Object> a 
                            = (ArrayAdapter<Object>) listener.getListAdapter();
        listener.setListShownNoAnimation(true);
        a.clear();
        for (Oferta oferta : result) {
            a.add(oferta);
        }
        a.notifyDataSetChanged();
    }

}
