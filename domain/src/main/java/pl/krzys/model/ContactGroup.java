package pl.krzys.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="contact_group")
@NamedQuery(name="ContactGroup.findAll", query="SELECT c FROM ContactGroup c")
public class ContactGroup {

    @Id
    @TableGenerator(name = "default",
            table = "id_table",
            pkColumnName = "table_name",
            valueColumnName = "next_id",
            allocationSize = 10)
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "default")
    @Column(unique=true, nullable=false)
    private Long id;

    private String description;

    private String name;

    //bi-directional many-to-one association to CongMapping
    @OneToMany(mappedBy="contactGroup", fetch=FetchType.EAGER)
    private Set<CongMapping> congMappings;

    //bi-directional many-to-one association to ContactGroup
    @ManyToOne
    @JoinColumn(name="parent_id")
    private ContactGroup contactGroup;

    //bi-directional many-to-one association to ContactGroup
    @OneToMany(mappedBy="contactGroup", fetch=FetchType.EAGER)
    private Set<ContactGroup> contactGroups;

    public ContactGroup() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CongMapping> getCongMappings() {
        return this.congMappings;
    }

    public void setCongMappings(Set<CongMapping> congMappings) {
        this.congMappings = congMappings;
    }

    public CongMapping addCongMapping(CongMapping congMapping) {
        getCongMappings().add(congMapping);
        congMapping.setContactGroup(this);

        return congMapping;
    }

    public CongMapping removeCongMapping(CongMapping congMapping) {
        getCongMappings().remove(congMapping);
        congMapping.setContactGroup(null);

        return congMapping;
    }

    public ContactGroup getContactGroup() {
        return this.contactGroup;
    }

    public void setContactGroup(ContactGroup contactGroup) {
        this.contactGroup = contactGroup;
    }

    public Set<ContactGroup> getContactGroups() {
        return this.contactGroups;
    }

    public void setContactGroups(Set<ContactGroup> contactGroups) {
        this.contactGroups = contactGroups;
    }

    public ContactGroup addContactGroup(ContactGroup contactGroup) {
        getContactGroups().add(contactGroup);
        contactGroup.setContactGroup(this);

        return contactGroup;
    }

    public ContactGroup removeContactGroup(ContactGroup contactGroup) {
        getContactGroups().remove(contactGroup);
        contactGroup.setContactGroup(null);

        return contactGroup;
    }
}
