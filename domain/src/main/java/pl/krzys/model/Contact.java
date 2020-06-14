package pl.krzys.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="contact")
@NamedQuery(name="Contact.findAll", query="SELECT c FROM Contact c")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contact {

    @Id
    @TableGenerator(name = "default",
            table = "id_table",
            pkColumnName = "table_name",
            valueColumnName = "next_id",
            allocationSize = 10)
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "default")
    @Column(unique=true, nullable=false)
    private Long id;

    private String name;

    @Column(name="business_email")
    private String businessEmail;

    @Column(name="company_id")
    private Long companyId;

    @Type(type = "pl.krzys.types.DateLongType")
    private Date created;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="last_modified")
    @Type(type = "pl.krzys.types.DateLongType")
    private Date lastModified;

    @Column(name="last_modified_by")
    private String lastModifiedBy;

    @Column(name="mobile_phone")
    private String mobilePhone;

    //bi-directional many-to-one association to CongMapping
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "cong_mapping",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<ContactGroup> contactGroups;

    //bi-directional many-to-one association to ContactAddress
    @ManyToMany(mappedBy="contact", fetch=FetchType.LAZY)
    private Set<ContactAddress> contactAddresses;

}
