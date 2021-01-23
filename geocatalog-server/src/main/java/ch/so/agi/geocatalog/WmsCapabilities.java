package ch.so.agi.geocatalog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WmsCapabilities {
    String url;
    File file;
    
    public WmsCapabilities(String url) throws IOException {
        this.url = url;
        Path tempFile = Files.createTempFile("temp-", ".xml");
        file = tempFile.toFile();
        ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    
    // TODO: exception handling
    public void getNamedLayers() throws Exception {
        System.out.println(file.length());
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        DocumentBuilder db = dbf.newDocumentBuilder();  
        Document doc = db.parse(file);  
        
        doc.getDocumentElement().normalize();  
        NodeList nodeList = doc.getElementsByTagName("Layer"); 

        
        // Logik geht nicht ganz auf, da AGDI die Web GIS Client Logik
        // nicht 1:1 im GetCapabilities abbildet.
        for (int itr = 0; itr < nodeList.getLength(); itr++) {  
            Node node = nodeList.item(itr);                 
            if (node.getNodeType() == Node.ELEMENT_NODE) {  
                Element eElement = (Element) node;
                String name =  eElement.getElementsByTagName("Name").item(0).getTextContent();
                String title =  eElement.getElementsByTagName("Title").item(0).getTextContent();
                System.out.print(name);  
              
                NodeList childLayerNodes = eElement.getElementsByTagName("Layer");
                System.out.println(" " + childLayerNodes.getLength());
                
                String layerGroupTitle = "";                
                
                if (childLayerNodes.getLength() == 0) {
                    Node parentNode = node.getParentNode();
                    //Element pElement = (Element) parentNode;
//                    System.out.println(parentNode.getNodeName());

                    // TODO: Abbruchkriterium, z.B. max 10
                    while (parentNode.getNodeName().equalsIgnoreCase("Layer")) {
                        //System.out.println(parentNode.getNodeName());
                        Element pElement = (Element) parentNode;
                        //System.out.println(pElement.getElementsByTagName("Name").item(0).getTextContent());
                        
                        String pTitle = pElement.getElementsByTagName("Name").item(0).getTextContent();
                        if (!pTitle.equals("somap")) {
                            layerGroupTitle = pTitle;
                        }
                        
                        parentNode = parentNode.getParentNode();
                    }
                    System.out.println("Layergruppe: " + layerGroupTitle);
                }
            }
        }
    }
}
