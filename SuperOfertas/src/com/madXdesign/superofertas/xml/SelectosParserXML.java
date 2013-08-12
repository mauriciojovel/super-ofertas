package com.madXdesign.superofertas.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.madXdesign.superofertas.constant.SuperSelectoXML;
import com.madXdesign.superofertas.constant.TAG;

public class SelectosParserXML 
		extends AsyncTask<String, Void, List<SelectosParserXML.Categoria>> 
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private URL urlXML;
	private Document document;
	private ArrayAdapter<Categoria> adapter;
	
	@Override
	protected List<Categoria> doInBackground(String... params) {
		SelectosParserXML p = new SelectosParserXML(params[0]);
		return p.findAllCategorias();
	}
	
	public SelectosParserXML(ArrayAdapter<Categoria> a) {
		this.adapter = a;
	}
	
	public SelectosParserXML(String urlXML) {
		super();
		try {
			this.urlXML = new URL(urlXML);
			DocumentBuilderFactory factory = 
										DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			document = documentBuilder.parse(getInputStream());
		} catch (MalformedURLException e) {
			Log.e(TAG.XML, "Error al generar la clase con la url " + urlXML, e);
		} catch (ParserConfigurationException e) {
			Log.e(TAG.XML, "Error de mal formado xml " + urlXML, e);
		} catch (SAXException e) {
			Log.e(TAG.XML, "Error de mal formado xml " + urlXML, e);
		} catch (IOException e) {
			Log.e(TAG.XML, "No se pudo leer el xml " + urlXML, e);
		}
	}
	
	@Override
	protected void onPostExecute(List<Categoria> result) {
		super.onPostExecute(result);
		adapter.clear();
		adapter.addAll(result);
		adapter.notifyDataSetChanged();
	}
	
	private InputStream getInputStream() {
		try {
			return urlXML.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Devuelve todas las categorias del archivo xml
	 * @return
	 */
	public List<Categoria> findAllCategorias() {
		ArrayList<Categoria> categorias = 
				new ArrayList<SelectosParserXML.Categoria>();
		Element root = document.getDocumentElement();
		NodeList categoriasNodes = root.getElementsByTagName(
								SuperSelectoXML.CATEGORIAS_NODE);
		if(categoriasNodes.getLength() > 0) {
			//Lista de categorias.
			NodeList categoriasNode = categoriasNodes.item(0).getChildNodes();
			for (int i = 0; i < categoriasNode.getLength(); i++) {
				Node categoria = categoriasNode.item(i);
				Categoria cat = new Categoria();
				NodeList datosCategoria = categoria.getChildNodes();
				for (int j = 0; j < datosCategoria.getLength(); j++) {
					Node dat = datosCategoria.item(j);
					if(dat.getNodeName().equals(SuperSelectoXML.NOMBRE_NODE)) {
						cat.setNombre(dat.getFirstChild().getNodeValue());
					} else if(dat.getNodeName()
								.equals(SuperSelectoXML.CANTIDAD_NODE)) {
						cat.setCantidad(Integer.parseInt(dat.getFirstChild().getNodeValue()));
					} else if(dat.getNodeName()
								.equals(SuperSelectoXML.ICONO_NODE)) {
						cat.setIcono(dat.getFirstChild().getNodeValue());
					}
				}
				if(datosCategoria.getLength() > 0) {
					categorias.add(cat);
				}
			}			
		}
		return categorias;
	}
	
	public List<Oferta> findOfertas(String categoria) {
		ArrayList<Oferta> ofertasList = 
				new ArrayList<SelectosParserXML.Oferta>();
		
		Element root = document.getDocumentElement();
		NodeList categoriasNodes = root.getElementsByTagName(
				SuperSelectoXML.CATEGORIAS_NODE);
		boolean searchCategoria = categoria != null 
								&& !categoria.trim().equals("");
		
		if(categoriasNodes.getLength() > 0) {
			NodeList catNodes = categoriasNodes.item(0).getChildNodes();
			for (int i = 0; i < catNodes.getLength(); i++) {
				Node catNode = catNodes.item(i);
				NodeList datCatNodes = catNode.getChildNodes();
				for (int j = 0; j < datCatNodes.getLength(); j++) {
					Node dat = datCatNodes.item(j);
					if(dat.getNodeName().equals(SuperSelectoXML.NOMBRE_NODE)) {
						if(searchCategoria 
								&& !dat.getNodeValue().equals(categoria)) {
							break;
						}
					} else if(dat.getNodeName().equals(
											SuperSelectoXML.OFERTAS_NODE)) {
						NodeList ofertasNodeList = dat.getChildNodes();
						for (int k = 0; k < ofertasNodeList.getLength(); k++) {
							Oferta oferta = new Oferta();
							Node ofertaData = ofertasNodeList.item(k)
															.getFirstChild();
							while(ofertaData != null) {
								if(ofertaData.getNodeValue()
										.equals(SuperSelectoXML.TITULO_NODE)) {
									oferta.setTitulo(ofertaData.getNodeValue());
								}
							}
							ofertasList.add(oferta);
						}
					}
					
				}
				
			}
		}
		
		return ofertasList;
	}
	
	public class Categoria implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String nombre;
		private int cantidad;
		private String icono;
		
		public Categoria() {}

		public Categoria(String nombre, int cantidad, String icono) {
			super();
			this.nombre = nombre;
			this.cantidad = cantidad;
			this.icono = icono;
		}
		
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public String getIcono() {
			return icono;
		}
		public void setIcono(String icono) {
			this.icono = icono;
		}
		
		@Override
		public String toString() {
			return this.nombre + " (" + cantidad + ")";
		}
	}
	
	public class Oferta implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String titulo;
		private String presentacion;
		private String imagen;
		private double precionNormal;
		private double ahorro;
		private double precioOferta;
		private double descuento;
		private Date fechaPublicacion;
		private Date fechaVencimiento;
		
		public Oferta() {}
		
		
		
		public Oferta(String titulo, String presentacion, String imagen,
				double precionNormal, double ahorro, double precioOferta,
				double descuento, Date fechaPublicacion, Date fechaVencimiento)
		{
			super();
			this.titulo = titulo;
			this.presentacion = presentacion;
			this.imagen = imagen;
			this.precionNormal = precionNormal;
			this.ahorro = ahorro;
			this.precioOferta = precioOferta;
			this.descuento = descuento;
			this.fechaPublicacion = fechaPublicacion;
			this.fechaVencimiento = fechaVencimiento;
		}

		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public String getPresentacion() {
			return presentacion;
		}
		public void setPresentacion(String presentacion) {
			this.presentacion = presentacion;
		}
		public String getImagen() {
			return imagen;
		}
		public void setImagen(String imagen) {
			this.imagen = imagen;
		}
		public double getPrecionNormal() {
			return precionNormal;
		}
		public void setPrecionNormal(double precionNormal) {
			this.precionNormal = precionNormal;
		}
		public double getAhorro() {
			return ahorro;
		}
		public void setAhorro(double ahorro) {
			this.ahorro = ahorro;
		}
		public double getPrecioOferta() {
			return precioOferta;
		}
		public void setPrecioOferta(double precioOferta) {
			this.precioOferta = precioOferta;
		}
		public double getDescuento() {
			return descuento;
		}
		public void setDescuento(double descuento) {
			this.descuento = descuento;
		}
		public Date getFechaPublicacion() {
			return fechaPublicacion;
		}
		public void setFechaPublicacion(Date fechaPublicacion) {
			this.fechaPublicacion = fechaPublicacion;
		}
		public Date getFechaVencimiento() {
			return fechaVencimiento;
		}
		public void setFechaVencimiento(Date fechaVencimiento) {
			this.fechaVencimiento = fechaVencimiento;
		}
	}

	
	
}
