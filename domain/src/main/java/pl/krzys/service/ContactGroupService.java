package pl.krzys.service;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzys.dto.ContactGroupDTO;
import pl.krzys.exception.ResourceExistsException;
import pl.krzys.exception.ResourceNotFoundException;
import pl.krzys.mapper.ContactGroupMapper;
import pl.krzys.model.ContactGroup;
import pl.krzys.repository.ContactGroupRepository;

import java.util.Optional;

@Transactional
@Service("contactGroupService")
public class ContactGroupService {

    ContactGroupRepository contactGroupRepository;
    ContactGroupMapper contactGroupMapper;

    public ContactGroupService(ContactGroupRepository contactGroupRepository, ContactGroupMapper contactGroupMapper) {
        this.contactGroupRepository = contactGroupRepository;
        this.contactGroupMapper = contactGroupMapper;
    }

    public List<ContactGroupDTO> getAllContactGroups() {
        return List.ofAll(this.contactGroupRepository.findAll()).map(ContactGroupDTO::new);
    }

    public ContactGroupDTO getContactGroupById(Long id) {
        Optional<ContactGroup> contactGroup = this.contactGroupRepository.findById(id);
        return contactGroup.map( c-> contactGroupMapper.contactGroupToContactGroupDTO(c)).orElseThrow(
                ResourceNotFoundException::new
        );
    }

    public ContactGroupDTO createNew(ContactGroupDTO newContactGroupDTO) {
        if (this.contactGroupRepository.findByName(newContactGroupDTO.getName()).isPresent()){
            throw new ResourceExistsException(newContactGroupDTO.getName());
        } else {
            ContactGroup newContactGroup;
            newContactGroup = contactGroupMapper.contactGroupDTOToContactGroup(newContactGroupDTO);
            contactGroupRepository.save(newContactGroup);
            return contactGroupMapper.contactGroupToContactGroupDTO(newContactGroup);
        }
    }

    public Optional<ContactGroupDTO> update(ContactGroupDTO updateContactGroupDTO) {
        return Optional.of(this.contactGroupRepository
                .findById(updateContactGroupDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(contactGroup -> {
                    contactGroup = contactGroupMapper.contactGroupDTOToContactGroup(updateContactGroupDTO);
                    contactGroupRepository.save(contactGroup);
                    return contactGroup;
                })
                .map(ContactGroupDTO::new);
    }

    public void delete(Long contactGroupId) {
        if(!contactGroupRepository.findById(contactGroupId).isPresent()){
            throw new ResourceNotFoundException();
        } else {
            this.contactGroupRepository.deleteById(contactGroupId);
        }
    }
}
