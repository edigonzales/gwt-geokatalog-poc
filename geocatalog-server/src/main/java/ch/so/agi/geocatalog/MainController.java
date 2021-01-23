package ch.so.agi.geocatalog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.Layer;
import org.geotools.ows.wms.WMSCapabilities;
import org.geotools.ows.wms.WMSUtils;
import org.geotools.ows.wms.WebMapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

@RestController
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Settings settings;
    
    @GetMapping("/settings")
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok().body(settings);
    }
    
    @GetMapping("/capabilities")
    public ResponseEntity<?> getCapabilities() throws IOException {
        URL url = null;
        try {
            // v1.3.0 macht Probleme.
            url = new URL("https://geo.so.ch/api/wms?VERSION=1.1.1&Request=GetCapabilities&Service=WMS");
        } catch (MalformedURLException e) {
            // should not reach here
            log.error(e.getMessage());
        }

        WebMapServer wms = null;
        /*
        try {
            log.info("1");
            wms = new WebMapServer(url);
            log.info("2");            
          } catch (IOException e) {
            //There was an error communicating with the server
            //For example, the server is down
          } catch (ServiceException e) {
            //The server returned a ServiceException (unusual in this case)
              log.error(e.getMessage());
          } catch (SAXException e) {
            //Unable to parse the response from the server
            //For example, the capabilities it returned was not valid
          }

        WMSCapabilities capabilities = wms.getCapabilities();
        String serverName = capabilities.getService().getName();
        String serverTitle = capabilities.getService().getTitle();
        System.out.println("Capabilities retrieved from server: " + serverName + " (" + serverTitle + ")");

        Layer[] layers = WMSUtils.getNamedLayers(capabilities);
        for (Layer layer : layers) {
            System.out.print(layer.getName() + " -- " + layer.getTitle());
            if (layer.getParent() != null) {
                System.out.println(" | " + layer.getParent());
            } else {
                System.out.println("");
            }
        }
        System.out.println(layers.length);
        */
        
        WmsCapabilities capabilities = new WmsCapabilities("https://geo.so.ch/api/wms?VERSION=1.1.1&Request=GetCapabilities&Service=WMS");
        
        try {
            capabilities.getNamedLayers();
 
        } catch(Exception e) {
            e.getMessage();
        }
        
        
        return ResponseEntity.ok().body("xxx");
    }
 }
