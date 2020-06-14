package pl.krzys.dto;

import lombok.Getter;
import lombok.Setter;
import pl.krzys.model.Contact;
import pl.krzys.model.ContactAddress;
import pl.krzys.model.ContactGroup;

import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContactDTO {

    private Long id;
    private String name;
    private String businessEmail;
    private Long companyId;
    private Date created;
    private String createdBy;
    private Date lastModified;
    private String lastModifiedBy;
    private String mobilePhone;
    private Set<Long> contactGroupIds;
    private Set<Long> contactAddressIds;

    public ContactDTO(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.businessEmail = contact.getBusinessEmail();
        this.companyId = contact.getCompanyId();
        this.created = contact.getCreated();
        this.createdBy = contact.getCreatedBy();
        this.lastModified = contact.getLastModified();
        this.lastModifiedBy = contact.getLastModifiedBy();
        this.mobilePhone = contact.getMobilePhone();
        this.contactGroupIds = contact.getContactGroups().stream()
            .map(ContactGroup::getId)
            .collect(Collectors.toSet());
        this.contactAddressIds = contact.getContactAddresses().stream()
            .map(ContactAddress::getId)
            .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("businessEmail='" + businessEmail + "'")
                .add("companyId=" + companyId)
                .add("created=" + created)
                .add("createdBy='" + createdBy + "'")
                .add("lastModified=" + lastModified)
                .add("lastModifiedBy='" + lastModifiedBy + "'")
                .add("mobilePhone='" + mobilePhone + "'")
                .add("contactGroups=" + contactGroupIds)
                .add("contactAddresses=" + contactAddressIds)
                .toString();
    }
}
