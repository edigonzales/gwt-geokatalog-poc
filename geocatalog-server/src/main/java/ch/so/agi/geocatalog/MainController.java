package ch.so.agi.geocatalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getCapabilities() throws Exception {
        WmsCapabilities capabilities = new WmsCapabilities("https://geo.so.ch/api/wms?VERSION=1.1.1&Request=GetCapabilities&Service=WMS");
        var wmsLayers = capabilities.getNamedLayers();
            
        return ResponseEntity.ok().body(wmsLayers);
    }
 }
