package pl.krzys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.dto.ContactAddressDTO;
import pl.krzys.service.ContactAddressService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/contactaddress")
public class ContactAddressController {

    private static final Logger log = LoggerFactory.getLogger(ContactAddressController.class);

    ContactAddressService contactAddressService;

    public ContactAddressController(ContactAddressService contactAddressService) {
        this.contactAddressService = contactAddressService;
    }

    @GetMapping()
    public ResponseEntity<List<ContactAddressDTO>> getAll() {
        log.info("received request to list contact Addresses");
        return ResponseEntity.ok(contactAddressService.getAllContactsAddresses().asJava());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactAddressDTO> getOneById(@PathVariable("id") Long id)
    {
        log.info("received request to get one contact address");
        return ResponseEntity.ok(contactAddressService.getContactAddressById(id));
    }


    @PostMapping
    public ResponseEntity<ContactAddressDTO> create(@RequestBody ContactAddressDTO data) throws URISyntaxException {
        log.info("received request to create contact address");
        ContactAddressDTO createdContactAddress = contactAddressService.createNew(data);
        return ResponseEntity.created(new URI("/api/contactaddress/" + data.getId())).body(createdContactAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactAddressDTO> updateOne(@PathVariable("id") Long id, @RequestBody ContactAddressDTO data)
    {
        log.info("received request to update contact address");
        ContactAddressDTO contactAddressToUpdate = contactAddressService.getContactAddressById(id);
        if (contactAddressToUpdate != null) {
            data.setId(id);
            contactAddressService.update(data);
        }
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") Long id)
    {
        log.info("received request to delete contact address");
        contactAddressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}