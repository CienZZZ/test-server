package pl.krzys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.dto.ContactDTO;
import pl.krzys.service.ContactService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
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

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateOne(@PathVariable("id") long id, @RequestBody ContactDTO data)
    {
//        Optional<ContactDTO> original = contactRepository.findById(id);
//        if(!original.isPresent())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        data.setId(id);
//        return new ResponseEntity<>(contactRepository.save(data), HttpStatus.OK);
        ContactDTO contactToUpdate = contactService.getContactById(id);
        if (contactToUpdate != null) {
            data.setId(id);
            contactService.update(data);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") long id)
    {
        try {
            contactService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}