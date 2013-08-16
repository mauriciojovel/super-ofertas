package com.madXdesign.superofertas.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.madXdesign.superofertas.constant.SuperSelectoXML;
import com.madXdesign.superofertas.constant.TAG;

public class SelectosParserXML 
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private URL urlXML;
	private DocumentBuilder documentBuilder;
	private static Document document;
	
	public SelectosParserXML(String urlXML) {
		try {
			if (this.urlXML == null) {
                this.urlXML = new URL(urlXML);
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                documentBuilder = factory.newDocumentBuilder();
                //document = getDocument();
            }
		} catch (MalformedURLException e) {
			Log.e(TAG.XML, "Error al generar la clase con la url " + urlXML, e);
		} catch (ParserConfigurationException e) {
			Log.e(TAG.XML, "Error de mal formado xml " + urlXML, e);
		}
	}
	
	private Document getDocument() {
	    if(document == null) {
	        try {
                document = documentBuilder.parse(getInputStream());
            } catch (SAXException e) {
                Log.e(TAG.XML, "Error de mal formado xml " + urlXML, e);
            } catch (IOException e) {
                Log.e(TAG.XML, "No se pudo leer el xml " + urlXML, e);
            }
	    }
	    return document;
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
		Element root = getDocument().getDocumentElement();
		NodeList categoriasNodes = root.getElementsByTagName(
								SuperSelectoXML.CATEGORIAS_NODE);
		if(categoriasNodes.getLength() > 0) {
			//Lista de categorias
			NodeList categoriasNode = categoriasNodes.item(0).getChildNodes();
			for (int i = 0; i < categoriasNode.getLength(); i++) {
				Node categoria = categoriasNode.item(i);
				Categoria cat = new Categoria();
				NodeList datosCategoria = categoria.getChildNodes();
				for (int j = 0; j < datosCategoria.getLength(); j++) {
					Node dat = datosCategoria.item(j);
					if(dat.getNodeName().equals(SuperSelectoXML.NOMBRE_NODE)) {
						cat.setNombre(getText(dat));
					} else if(dat.getNodeName()
								.equals(SuperSelectoXML.CANTIDAD_NODE)) {
						cat.setCantidad(Integer.parseInt(getText(dat)));
					} else if(dat.getNodeName()
								.equals(SuperSelectoXML.ICONO_NODE)) {
						cat.setIcono(getText(dat));
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
				new ArrayList<Oferta>();
		
		Element root = getDocument().getDocumentElement();
		NodeList categoriasNodes = root.getElementsByTagName(
				SuperSelectoXML.CATEGORIAS_NODE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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
								&& !getText(dat).equals(categoria)) {
							break;
						}
					} else if(dat.getNodeName().equals(
											SuperSelectoXML.OFERTAS_NODE)) {
						NodeList ofertasNodeList = dat.getChildNodes();
						for (int k = 0; k < ofertasNodeList.getLength(); k++) {
							Oferta oferta = new Oferta();
							Node ofertaData = ofertasNodeList.item(k);
							NodeList ofertaDataNodeList = 
							                        ofertaData.getChildNodes();
							for (int l = 0; l < ofertaDataNodeList.getLength();
							        l++) {
							    Node ofertaDataNode =ofertaDataNodeList.item(l);
							    if(ofertaDataNode.getNodeName()
	                                    .equals(SuperSelectoXML.TITULO_NODE)) {
	                                oferta.setTitulo(getText(ofertaDataNode));
	                            } else if(ofertaDataNode.getNodeName().equals(
	                                    SuperSelectoXML.PRECIO_OFERTA_NODE)) {
	                                oferta.setPrecioOferta(
	                                        Double.parseDouble(
	                                                getText(ofertaDataNode)));
	                            } else if(ofertaDataNode.getNodeName().equals(
	                                    SuperSelectoXML.PRECIO_NORMAL_NODE)) {
                                    oferta.setPrecionNormal(Double.parseDouble(
                                            getText(ofertaDataNode)));
                                } else if(ofertaDataNode.getNodeName().equals(
                                        SuperSelectoXML.AHORRO_NODE)) {
                                    oferta.setAhorro(Double.parseDouble(
                                            getText(ofertaDataNode)));
                                }else if(ofertaDataNode.getNodeName().equals(
                                        SuperSelectoXML.DESCUENTO_NODE)) {
                                    oferta.setDescuento(Double.parseDouble(
                                            getText(ofertaDataNode)));
                                } else if(ofertaDataNode.getNodeName().equals(
                                        SuperSelectoXML.IMAGEN_NODE)) {
                                    oferta.setImagen(
                                            getText(ofertaDataNode));
                                } else if(ofertaDataNode.getNodeName().equals(
                                        SuperSelectoXML
                                                .FECHA_VENCIMIENTO_NODE)) {
                                    
                                    try {
                                        oferta.setFechaVencimiento(sdf
                                                .parse(getText(ofertaDataNode))
                                                );
                                    } catch (ParseException e) {
                                        Log.e(TAG.XML
                                                , "Error de conversion de fecha"
                                                ,e);
                                    }
                                } 
                            }
							if(ofertaDataNodeList.getLength()>0) {
							    ofertasList.add(oferta);
							}
							
						}
					}
					
				}
				
			}
		}
		
		return ofertasList;
	}
	
	public String getText(Node node) {
	    StringBuilder builder = new StringBuilder();
	    NodeList fragemtos = node.getChildNodes();
	    for (int i = 0; i < fragemtos.getLength(); i++) {
            builder.append(fragemtos.item(i).getNodeValue());
        }
	    return builder.toString();
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
	
}
