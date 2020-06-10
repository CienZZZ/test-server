package pl.krzys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzys.model.ContactGroup;
import pl.krzys.repository.ContactGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contactgroup")
public class ContactGroupController {
    @Autowired
    ContactGroupRepository repository;
    
    @GetMapping()
    public ResponseEntity<List<ContactGroup>> getAll() {
        try {
            List<ContactGroup> list = new ArrayList<>();

            repository.findAll().forEach(list::add);
    
            if(list.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
            return new ResponseEntity<>(list, HttpStatus.OK);
    
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContactGroup> getOneById(@PathVariable("id") long id)
    {
        Optional<ContactGroup> data = repository.findById(id);
        if(data.isPresent())
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ContactGroup> create(@RequestBody ContactGroup data) {
        try {
            ContactGroup created = repository.save(data);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactGroup> updateOne(@PathVariable("id") long id, @RequestBody ContactGroup data)
    {
        Optional<ContactGroup> original = repository.findById(id);
        if(!original.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        data.setId(id);
        return new ResponseEntity<>(repository.save(data), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") long id)
    {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}