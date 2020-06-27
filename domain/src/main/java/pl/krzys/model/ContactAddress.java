package pl.krzys.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="contact_address")
@NamedQuery(name="ContactAddress.findAll", query="SELECT c FROM ContactAddress c")
@Getter
@Setter
@NoArgsConstructor
public class ContactAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "default",
            table = "id_table",
            pkColumnName = "table_name",
            valueColumnName = "next_id",
            allocationSize = 10)
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "default")
    @Column(unique=true, nullable=false)
    private Long id;

    @Column(name="as_company", columnDefinition = "int2")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean asCompany;

    @Column(columnDefinition = "int2")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean business;

    private String city;

    private String country;

    private String district;

    @Column(name="post_code")
    private String postCode;

    private String province;

    private String street;

    //bi-directional many-to-one association to Contact
    @ManyToOne
    @JoinColumn(name="contact_id")
    private Contact contact;

}
