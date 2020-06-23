package pl.krzys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.dto.CongMappingDTO;
import pl.krzys.service.CongMappingService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/congmapping")
public class CongMappingController {

    private static final Logger log = LoggerFactory.getLogger(CongMappingController.class);

    CongMappingService congMappingService;

    public CongMappingController(CongMappingService congMappingService) {
        this.congMappingService = congMappingService;
    }

    @GetMapping()
    public ResponseEntity<List<CongMappingDTO>> getAll() {
        log.info("received request to list congMapping");
        return ResponseEntity.ok(congMappingService.getAllCongMapping().asJava());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongMappingDTO> getOneById(@PathVariable("id") Long id)
    {
        log.info("received request to get one congMapping");
        return ResponseEntity.ok(congMappingService.getCongMappingById(id));
    }


    @PostMapping
    public ResponseEntity<CongMappingDTO> create(@RequestBody CongMappingDTO data) throws URISyntaxException {
        log.info("received request to create congMapping");
        CongMappingDTO createdCongMapping = congMappingService.createNew(data);
        return ResponseEntity.created(new URI("/api/congmapping/" + data.getId())).body(createdCongMapping);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CongMappingDTO> updateOne(@PathVariable("id") Long id, @RequestBody CongMappingDTO data)
    {
        log.info("received request to update congMapping");
        CongMappingDTO congMappingToUpdate = congMappingService.getCongMappingById(id);
        if (congMappingToUpdate != null) {
            data.setId(id);
            congMappingService.update(data);
        }
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") Long id)
    {
        log.info("received request to delete congMapping");
        congMappingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}