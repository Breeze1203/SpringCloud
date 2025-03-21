package org.pt.controller;

import org.pt.entity.ConfigUpdate;
import org.pt.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ClassName BusController
 * @Author pt
 * @Description
 * @Date 2025/3/21 11:14
 **/
@RestController
@RequestMapping("/bus-a")
public class BusController {
    private final ConfigService configService;


    @Autowired
    public BusController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateConfig(@RequestBody ConfigUpdate request) {
        try {
            configService.updateConfig(request.getSuffix(), request.getContent());
            return ResponseEntity.ok("Configuration updated successfully for bus-" + request.getSuffix() + ".properties");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to update configuration: " + e.getMessage());
        }
    }
}
