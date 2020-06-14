package pl.krzys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzys.dto.ContactDTO;
import pl.krzys.service.ContactService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
//    @Autowired
//    ContactRepository repository;
//
//    public ContactController(ContactRepository repository) {
//        this.repository = repository;
//    }
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

//    @GetMapping()
//    public ResponseEntity<List<Contact>> getAll() {
//        try {
//            List<Contact> list = new ArrayList<>();
//
//            repository.findAll().forEach(list::add);
//
//            if(list.isEmpty())
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//            return new ResponseEntity<>(list, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    
//    @GetMapping("/{id}")
//    public ResponseEntity<Contact> getOneById(@PathVariable("id") long id)
//    {
//        Optional<Contact> data = repository.findById(id);
//        if(data.isPresent())
//            return new ResponseEntity<>(data.get(), HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping
//    public ResponseEntity<Contact> create(@RequestBody Contact data) {
//        try {
//            Contact created = repository.save(data);
//            return new ResponseEntity<>(created, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
//        }
//    }
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
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") long id)
//    {
//        try {
//            repository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
//    }

}