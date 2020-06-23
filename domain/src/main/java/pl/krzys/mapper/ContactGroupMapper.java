package pl.krzys.mapper;

import pl.krzys.dto.ContactGroupDTO;
import pl.krzys.model.CongMapping;
import pl.krzys.model.ContactGroup;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactGroupMapper {

    public ContactGroupDTO contactGroupToContactGroupDTO(ContactGroup contactGroup) {
        return new ContactGroupDTO(contactGroup);
    }

    public ContactGroup contactGroupDTOToContactGroup(ContactGroupDTO contactGroupDTO) {
        if (contactGroupDTO == null) {
            return null;
        } else {
            ContactGroup contactGroup = new ContactGroup();
            contactGroup.setId(contactGroupDTO.getId());
            contactGroup.setDescription(contactGroupDTO.getDescription());
            contactGroup.setName(contactGroupDTO.getName());
            Set<CongMapping> congMappings = this.congMappingsFromIds(contactGroupDTO.getCongMappingIds());
            contactGroup.setCongMappings(congMappings);
            contactGroup.setContactGroup(contactGroupFromId(contactGroupDTO.getContactGroupId()));
            Set<ContactGroup> contactGroups = this.contactGroupsFromIds(contactGroupDTO.getContactGroupIds());
            contactGroup.setContactGroups(contactGroups);
            return contactGroup;
        }
    }

    private Set<CongMapping> congMappingsFromIds(Set<Long> congMappingIds) {
        Set<CongMapping> congMappingSet = new HashSet<>();

        if (congMappingIds != null) {
            congMappingSet = congMappingIds.stream().map(ids -> {
                CongMapping congMapping = new CongMapping();
                congMapping.setId(ids);
                return congMapping;
            }).collect(Collectors.toSet());
        }

        return congMappingSet;
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

    public static ContactGroup contactGroupFromId(Long id) {
        if (id == null) {
            return null;
        } else {
            ContactGroup contactGroup = new ContactGroup();
            contactGroup.setId(id);
            return contactGroup;
        }
    }
}
