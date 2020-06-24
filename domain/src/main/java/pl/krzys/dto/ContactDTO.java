package pl.krzys.dto;

import lombok.Getter;
import lombok.Setter;
import pl.krzys.model.Contact;
import pl.krzys.model.ContactAddress;
import pl.krzys.model.ContactGroup;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContactDTO {

    private Long id;
    @NotNull(message = "name can not be null")
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

    public ContactDTO() {
    }

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
//        this.contactGroupIds = contact.getContactGroups().stream()    // TODO: how it should be ? if we don't check null then we have null pointer in tests
//            .map(ContactGroup::getId)
//            .collect(Collectors.toSet());
        this.contactGroupIds = getContactGroups(contact);
        this.contactAddressIds = getContactAddress(contact);
    }

    private Set<Long> getContactGroups(Contact contact) {
        if (contact.getContactGroups() != null) {
            return contact.getContactGroups().stream()
                    .map(ContactGroup::getId)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    private Set<Long> getContactAddress(Contact contact) {
        if (contact.getContactAddresses() != null) {
            return contact.getContactAddresses().stream()
                    .map(ContactAddress::getId)
                    .collect(Collectors.toSet());
        }
        return null;
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
