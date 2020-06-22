package pl.krzys.mapper;

import org.springframework.stereotype.Component;
import pl.krzys.dto.ContactDTO;
import pl.krzys.model.Contact;
import pl.krzys.model.ContactAddress;
import pl.krzys.model.ContactGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ContactMapper {

    public List<ContactDTO> contactsToContactDTOs(List<Contact> contacts) {
        return contacts.stream()
                .filter(Objects::nonNull)
                .map(this::contactToContactDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO contactToContactDTO(Contact contact) {
        return new ContactDTO(contact);
    }

    public List<Contact> contactDTOsToContacts(List<ContactDTO> contactDTOs) {
        return contactDTOs.stream()
                .filter(Objects::nonNull)
                .map(this::contactDTOToContact)
                .collect(Collectors.toList());
    }

    public Contact contactDTOToContact(ContactDTO contactDTO) {
        if (contactDTO == null) {
            return null;
        } else {
            Contact contact = new Contact();
            contact.setId(contactDTO.getId());
            contact.setName(contactDTO.getName());
            contact.setBusinessEmail(contactDTO.getBusinessEmail());
            contact.setCompanyId(contactDTO.getCompanyId());
            contact.setCreated(contactDTO.getCreated());
            contact.setCreatedBy(contactDTO.getCreatedBy());
            contact.setLastModified(contactDTO.getLastModified());
            contact.setLastModifiedBy(contactDTO.getLastModifiedBy());
            contact.setMobilePhone(contactDTO.getMobilePhone());
            Set<ContactGroup> contactGroups = this.contactGroupsFromIds(contactDTO.getContactGroupIds());
            contact.setContactGroups(contactGroups);
            Set<ContactAddress> contactAddresses = this.contactAddressesFromIds(contactDTO.getContactAddressIds());
            contact.setContactAddresses(contactAddresses);
            return contact;
        }
    }

    private Set<ContactGroup> contactGroupsFromIds(Set<Long> contactGroupIds) {
        Set<ContactGroup> contactGroupSet = new HashSet<>();

        if (contactGroupIds != null) {
            contactGroupSet = contactGroupIds.stream().map(ids -> {
                ContactGroup contactGroup = new ContactGroup();
                contactGroup.setId(ids);
                return contactGroup;
            }).collect(Collectors.toSet());
        }

        return contactGroupSet;
    }

    private Set<ContactAddress> contactAddressesFromIds(Set<Long> contactAddressIds) {
        Set<ContactAddress> contactAddressSet = new HashSet<>();

        if (contactAddressIds != null) {
            contactAddressSet = contactAddressIds.stream().map(ids -> {
                ContactAddress contactAddress = new ContactAddress();
                contactAddress.setId(ids);
                return contactAddress;
            }).collect(Collectors.toSet());
        }

        return contactAddressSet;
    }

    public static Contact contactFromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }

}
