package pl.krzys.dto;

import lombok.Getter;
import lombok.Setter;
import pl.krzys.model.CongMapping;
import pl.krzys.model.ContactGroup;

import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContactGroupDTO {

    private Long id;
    private String description;
    private String name;
    private Set<Long> congMappingIds;
    private Long contactGroupId;
    private Set<Long> contactGroupIds;

    public ContactGroupDTO() {
    }

    public ContactGroupDTO(ContactGroup contactGroup) {
        this.id = contactGroup.getId();
        this.description = contactGroup.getDescription();
        this.name = contactGroup.getName();
        this.congMappingIds = contactGroup.getCongMappings().stream()
            .map(CongMapping::getId)
            .collect(Collectors.toSet());
        this.contactGroupId = contactGroup.getContactGroup().getId();
        this.contactGroupIds = contactGroup.getContactGroups().stream()
            .map(ContactGroup::getId)
            .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactGroupDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("description='" + description + "'")
                .add("name='" + name + "'")
                .add("congMappingIds=" + congMappingIds)
                .add("contactGroupId=" + contactGroupId)
                .add("contactGroupIds=" + contactGroupIds)
                .toString();
    }
}
