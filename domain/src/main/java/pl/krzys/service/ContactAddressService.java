package pl.krzys.service;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzys.dto.ContactAddressDTO;
import pl.krzys.exception.ResourceNotFoundException;
import pl.krzys.mapper.ContactAddressMapper;
import pl.krzys.model.ContactAddress;
import pl.krzys.repository.ContactAddressRepository;

import java.util.Optional;

@Transactional
@Service("contactAddressService")
public class ContactAddressService {

    ContactAddressRepository contactAddressRepository;
    ContactAddressMapper contactAddressMapper;

    public ContactAddressService(ContactAddressRepository contactAddressRepository, ContactAddressMapper contactAddressMapper) {
        this.contactAddressRepository = contactAddressRepository;
        this.contactAddressMapper = contactAddressMapper;
    }

    public List<ContactAddressDTO> getAllContactsAddresses() {
        return List.ofAll(this.contactAddressRepository.findAll()).map(ContactAddressDTO::new);
    }

    public ContactAddressDTO getContactAddressById(Long id) {
        Optional<ContactAddress> contactAddress = this.contactAddressRepository.findById(id);
        return contactAddress.map( c-> contactAddressMapper.contactAddressToContactAddressDTO(c)).orElseThrow(
                ResourceNotFoundException::new
        );
    }

    public ContactAddressDTO createNew(ContactAddressDTO newContactAddressDTO) {
        // TODO: need check if exist ?
        ContactAddress newContactAddress;
        newContactAddress = contactAddressMapper.contactAddressDTOToContactAddress(newContactAddressDTO);
        contactAddressRepository.save(newContactAddress);
        return contactAddressMapper.contactAddressToContactAddressDTO(newContactAddress);
    }

    public Optional<ContactAddressDTO> update(ContactAddressDTO updateContactAddressDTO) {
        return Optional.of(this.contactAddressRepository
                .findById(updateContactAddressDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(contactAddress -> {
                    contactAddress = contactAddressMapper.contactAddressDTOToContactAddress(updateContactAddressDTO);
                    contactAddressRepository.save(contactAddress);
                    return contactAddress;
                })
                .map(ContactAddressDTO::new);
    }

    public void delete(Long contactAddressId) {
        if(!contactAddressRepository.findById(contactAddressId).isPresent()){
            throw new ResourceNotFoundException();
        } else {
            this.contactAddressRepository.deleteById(contactAddressId);
        }
    }
}
