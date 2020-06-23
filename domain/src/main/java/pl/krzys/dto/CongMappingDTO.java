package pl.krzys.dto;

import lombok.Getter;
import lombok.Setter;
import pl.krzys.model.CongMapping;

import java.util.Date;
import java.util.StringJoiner;

@Getter
@Setter
public class CongMappingDTO {

    private Long id;
    private Date created;
    private Long contactId;
    private Long contactGroupId;

    public CongMappingDTO() {
    }

    public CongMappingDTO(CongMapping congMapping) {
        this.id = congMapping.getId();
        this.created = congMapping.getCreated();
        this.contactId = congMapping.getContact().getId();
        this.contactGroupId = congMapping.getContactGroup().getId();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CongMappingDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("created=" + created)
                .add("contactId=" + contactId)
                .add("contactGroupId=" + contactGroupId)
                .toString();
    }
}
