/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Pelikula;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author DM3-2-12
 */
public class GestionEnListaEnMemoria {
    
    public static ObservableList<Pelikula> cargaPelikula(File file) throws IOException {
        ObservableList<Pelikula> lista = FXCollections.observableArrayList();
                try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodo = doc.getElementsByTagName("Pelikula");
            for (int temp = 0; temp < nodo.getLength(); temp++) {
                Element PelElement = (Element) nodo.item(temp);
                Pelikula pelikula;
                pelikula = new Pelikula(
                        PelElement.getElementsByTagName("Izena").item(0).getTextContent(),
                        PelElement.getElementsByTagName("Zuzendaria").item(0).getTextContent(),
                        PelElement.getElementsByTagName("durazioa").item(0).getTextContent(),
                        Integer.parseInt(PelElement.getElementsByTagName("Adina").item(0).getTextContent()),
                        Integer.parseInt(PelElement.getElementsByTagName("Urtea").item(0).getTextContent())
                );
                lista.add(pelikula);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
     public static void guardarxml(ObservableList<Pelikula> pelikula, File file) {
        try {
            
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
//
            // definimos el elemento raíz del documento
            Element eRaiz = doc.createElement("Pelikulak");
            doc.appendChild(eRaiz);
            for(Pelikula pel:pelikula){
                Element elementPelikula = doc.createElement("Pelikula");
                eRaiz.appendChild(elementPelikula);
                
                Element elementIzena = doc.createElement("Izena");
                elementIzena.appendChild(doc.createTextNode(pel.getIzena()));
                elementPelikula.appendChild(elementIzena);
                
                Element elementZuzendaria = doc.createElement("Zuzendaria");
                elementZuzendaria.appendChild(doc.createTextNode(pel.getZuzendaria()));
                elementPelikula.appendChild(elementZuzendaria);
                
                Element elementDurazioa = doc.createElement("durazioa");
                elementDurazioa.appendChild(doc.createTextNode(pel.getDurazioa()));
                elementPelikula.appendChild(elementDurazioa);
                
                Element elementAdina = doc.createElement("Adina");
                elementAdina.appendChild(doc.createTextNode(String.valueOf(pel.getAdina())));
                elementPelikula.appendChild(elementAdina);
                
                Element elementurtea = doc.createElement("Urtea");
                elementurtea.appendChild(doc.createTextNode(String.valueOf(pel.getUrtea())));
                elementPelikula.appendChild(elementurtea);
            }
            // clases necesarias finalizar la creación del archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult((file));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

