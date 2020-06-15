package pl.krzys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.dto.ContactDTO;
import pl.krzys.repository.ContactRepository;
import pl.krzys.service.ContactService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    ContactService contactService;
    ContactRepository contactRepository;

    public ContactController(ContactService contactService, ContactRepository contactRepository) {
        this.contactService = contactService;
        this.contactRepository = contactRepository;
    }

    @GetMapping()
    public ResponseEntity<List<ContactDTO>> getAll() {
        try {

            List<ContactDTO> list = new ArrayList<>(contactService.getAllContacts().asJava());

            if(list.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
            return new ResponseEntity<>(list, HttpStatus.OK);
    
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getOneById(@PathVariable("id") long id)
    {
//        Optional<ContactDTO> data = Optional.ofNullable(contactService.getContactById(id));
//        if(data.isPresent())
//            return new ResponseEntity<>(data.get(), HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ContactDTO contactDTO = contactService.getContactById(id);
        if(contactDTO != null)
            return new ResponseEntity<>(contactDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO data) {
        try {
            ContactDTO created = contactService.createNew(data);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Contact> updateOne(@PathVariable("id") long id, @RequestBody Contact data)
//    {
//        Optional<Contact> original = repository.findById(id);
//        if(!original.isPresent())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        data.setId(id);
//        return new ResponseEntity<>(repository.save(data), HttpStatus.OK);
//    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") long id)
    {
        try {
            contactService.delete(id);
//            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}