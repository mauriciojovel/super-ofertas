package com.madXdesign.superofertas.fragment;

import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.madXdesign.superofertas.R;
import com.madXdesign.superofertas.utils.Formatter;
import com.madXdesign.superofertas.xml.Oferta;

public class ProductoFragment extends Fragment {

    public static final String OFERTA_DATA = "ofertaData";

    public ProductoFragment() {
    }

    private ImageView image;
    private TextView precioOfertaTxt;
    private TextView precioRegularTxt;
    private TextView fechaVencimientoTxt;
    private TextView ahorroTxt;
    private TextView tituloTxt;

    public ProductoFragment newInstance(Oferta oferta) {
        ProductoFragment f = new ProductoFragment();
        Bundle b = new Bundle();
        b.putSerializable(OFERTA_DATA, oferta);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.producto_layout, container, false);

        initComponents(v);
        setHasOptionsMenu(true);
        return v;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.oferta_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean r = true;
        switch (item.getItemId()) {
        case R.id.action_add_cart:
            Toast.makeText(getActivity(), getResources()
                    .getString(R.string.agregarCarritoMsg)
                                            , Toast.LENGTH_SHORT).show();
            break;
        default:
            r = super.onOptionsItemSelected(item); 
            break;
        }
        return r;
    }

    private void initComponents(View v) {
        image = (ImageView) v.findViewById(R.id.imageView1);
        precioOfertaTxt = (TextView) v.findViewById(R.id.precioOfertaTxt);
        precioRegularTxt = (TextView) v.findViewById(R.id.precioRegularTxt);
        fechaVencimientoTxt = (TextView) v
                .findViewById(R.id.fechaVencimientoTxt);
        ahorroTxt = (TextView) v.findViewById(R.id.ahorroTxt);
        tituloTxt = (TextView) v.findViewById(R.id.tituloTxt);

        // Iniciamos los valores
        Oferta o = getOferta();
        if (o != null) {
            tituloTxt.setText(o.getTitulo());
            if(o.getPrecioOferta() > 0) {
                precioOfertaTxt.setText(Formatter.money(o.getPrecioOferta()));
            } else {
                precioOfertaTxt.setText(o.getPorcentajeDescuento());
            }
            if(o.getPrecionNormal() > 0) {
                precioRegularTxt.setText(Formatter.money(o.getPrecionNormal()));
            } else {
                precioRegularTxt.setText(" - ");
            }
            if(o.getFechaVencimiento() != null) {
                fechaVencimientoTxt.setText(
                        Formatter.date(o.getFechaVencimiento(), getActivity()));
            } else {
                fechaVencimientoTxt.setText(" - ");
            }
            if(o.getAhorro() > 0) {
                ahorroTxt.setText(Formatter.money(o.getAhorro()));
            } else {
                ahorroTxt.setText(" - ");
            }
            onLoadimage(o.getImagen());
        }
    }

    public Oferta getOferta() {
        Bundle b = getArguments();
        if (b != null) {
            return (Oferta) b.getSerializable(OFERTA_DATA);
        } else {
            return null;
        }
    }

    public void onLoadimage(String imageName) {
        image.setImageDrawable(getResources().getDrawable(
                R.drawable.ic_launcher));
        new DownloadImageTask()
                .execute(getResources().getString(
                                R.string.urlSSImage)+imageName);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        /**
         * The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground()
         */
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }

        private Bitmap loadImageFromNetwork(String url) {
            try {
                Bitmap bitmap = BitmapFactory
                        .decodeStream((InputStream) new URL(url).getContent());
                return getResizedBitmap(bitmap, 20, 20);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            //float scaleWidthDP = convertPixelsToDp(width, getActivity());
            //float scaleHeightDP = convertPixelsToDp(height, getActivity());
            float scaleWidth = scaledImage((((float) newWidth)/100.0f)*width,getActivity());
            float scaleHeight = scaledImage((((float) newHeight)/100.0f)*height, getActivity());
            Log.i("t", "width: " + width +"px, "+scaleWidth+"px");
            Log.i("t", "height: " + height +"px, "+scaleHeight+"px");
            // CREATE A MATRIX FOR THE MANIPULATION
            //Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            //matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            //Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0
            //                                , width, height, matrix, false);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, (int)scaleWidth
                                        , (int)scaleHeight, false);
            return resizedBitmap;
        }
        
        /**
         * This method converts dp unit to equivalent pixels, depending on device density. 
         * 
         * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
         * @param context Context to get resources and device specific display metrics
         * @return A float value to represent px equivalent to dp depending on device density
         */
//        public float convertDpToPixel(float dp, Context context){
//            Resources resources = context.getResources();
//            DisplayMetrics metrics = resources.getDisplayMetrics();
//            float px = dp * (metrics.densityDpi / 160f);
//            return px;
//        }

        /**
         * This method converts device specific pixels to density independent pixels.
         * 
         * @param px A value in px (pixels) unit. Which we need to convert into db
         * @param context Context to get resources and device specific display metrics
         * @return A float value to represent dp equivalent to px value
         */
//        public float convertPixelsToDp(float px, Context context){
//            Resources resources = context.getResources();
//            DisplayMetrics metrics = resources.getDisplayMetrics();
//            float dp = px / (metrics.densityDpi / 160f);
//            return dp;
//        }
        
        public float scaledImage(float px, Context context) {
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px2 = px*metrics.density+0.5f;
            return px2;
        }
    }
}
