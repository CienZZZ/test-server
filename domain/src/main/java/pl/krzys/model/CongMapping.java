package pl.krzys.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cong_mapping")
@NamedQuery(name="CongMapping.findAll", query="SELECT c FROM CongMapping c")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CongMapping {

    @Id
    @TableGenerator(name = "default",
            table = "id_table",
            pkColumnName = "table_name",
            valueColumnName = "next_id",
            allocationSize = 10)
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "default")
    @Column(unique=true, nullable=false)
    private Long id;

    @Type(type = "pl.krzys.types.DateLongType")
    private Date created;

    //bi-directional many-to-one association to Contact
    @ManyToOne
    @JoinColumn(name="contact_id")
    private Contact contact;

    //bi-directional many-to-one association to ContactGroup
    @ManyToOne
    @JoinColumn(name="group_id")
    private ContactGroup contactGroup;
}
