package pl.krzys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.dto.ContactGroupDTO;
import pl.krzys.service.ContactGroupService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/contactgroup")
public class ContactGroupController {

    private static final Logger log = LoggerFactory.getLogger(ContactGroupController.class);

    ContactGroupService contactGroupService;

    public ContactGroupController(ContactGroupService contactGroupService) {
        this.contactGroupService = contactGroupService;
    }

    @GetMapping()
    public ResponseEntity<List<ContactGroupDTO>> getAll() {
        log.info("received request to list contacts groups");
        return ResponseEntity.ok(contactGroupService.getAllContactGroups().asJava());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactGroupDTO> getOneById(@PathVariable("id") Long id)
    {
        log.info("received request to get one contact group");
        return ResponseEntity.ok(contactGroupService.getContactGroupById(id));
    }


    @PostMapping
    public ResponseEntity<ContactGroupDTO> create(@RequestBody ContactGroupDTO data) throws URISyntaxException {
        log.info("received request to create contact group");
        ContactGroupDTO createdContactGroup = contactGroupService.createNew(data);
        return ResponseEntity.created(new URI("/api/contactgroup/" + data.getName())).body(createdContactGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactGroupDTO> updateOne(@PathVariable("id") Long id, @RequestBody ContactGroupDTO data)
    {
        log.info("received request to update contact group");
        ContactGroupDTO contactGroupToUpdate = contactGroupService.getContactGroupById(id);
        if (contactGroupToUpdate != null) {
            data.setId(id);
            contactGroupService.update(data);
        }
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") Long id)
    {
        log.info("received request to delete contact group");
        contactGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}