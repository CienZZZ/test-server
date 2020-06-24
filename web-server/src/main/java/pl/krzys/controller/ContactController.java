package pl.krzys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.dto.ContactDTO;
import pl.krzys.service.ContactService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping()
    public ResponseEntity<List<ContactDTO>> getAll() {
        log.info("received request to list contacts");
        return ResponseEntity.ok(contactService.getAllContacts().asJava());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getOneById(@PathVariable("id") Long id)
    {
        log.info("received request to get one contact");
        return ResponseEntity.ok(contactService.getContactById(id));
    }


    @PostMapping
    public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO data) throws URISyntaxException {
        log.info("received request to create contact");
        ContactDTO createdContact = contactService.createNew(data);
        return ResponseEntity.created(new URI("/api/contact/" + data.getName())).body(createdContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateOne(@PathVariable("id") Long id, @Valid @RequestBody ContactDTO data)
    {
        log.info("received request to update contact");
        ContactDTO contactToUpdate = contactService.getContactById(id);
        if (contactToUpdate != null) {
            data.setId(id);
            contactService.update(data);
        }
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") Long id)
    {
        log.info("received request to delete contact");
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }

}