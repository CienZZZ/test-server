package pl.krzys.service;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzys.dto.ContactDTO;
import pl.krzys.exception.ResourceExistsException;
import pl.krzys.exception.ResourceNotFoundException;
import pl.krzys.mapper.ContactMapper;
import pl.krzys.model.Contact;
import pl.krzys.repository.ContactRepository;

import java.util.Optional;

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

    public ContactDTO getContactById(long id) {
        Optional<Contact> contact = this.contactRepository.findById(id);
        return contact.map( c-> contactMapper.contactToContactDTO(c)).orElseThrow(
                ResourceNotFoundException::new
        );
    }

    public ContactDTO createNew(ContactDTO newContactDTO) {
        if (this.contactRepository.findByName(newContactDTO.getName()).isPresent()){
            throw new ResourceExistsException(newContactDTO.getName());
        } else {
            Contact newContact;
            newContact = contactMapper.contactDTOToContact(newContactDTO);
            contactRepository.save(newContact);
            return contactMapper.contactToContactDTO(newContact);
        }
    }

    public void delete(long contactId) {
        if(!contactRepository.findById(contactId).isPresent()){
            throw new ResourceNotFoundException();
        } else {
            this.contactRepository.deleteById(contactId);
        }
    }

}
