package pl.krzys.service;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzys.dto.ContactDTO;
import pl.krzys.mapper.ContactMapper;
import pl.krzys.repository.ContactRepository;

@Transactional
@Service("contactService")
public class ContactService {

    ContactRepository contactRepository;
    ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    public List<ContactDTO> getAllContacts() {
        return List.ofAll(this.contactRepository.findAll()).map(ContactDTO::new);
    }



}
