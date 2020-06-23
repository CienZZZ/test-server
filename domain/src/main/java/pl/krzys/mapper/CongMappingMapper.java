package pl.krzys.mapper;

import pl.krzys.dto.CongMappingDTO;
import pl.krzys.model.CongMapping;

import static pl.krzys.mapper.ContactGroupMapper.contactGroupFromId;
import static pl.krzys.mapper.ContactMapper.contactFromId;

public class CongMappingMapper {

    public CongMappingDTO congMappingToCongMappingDTO(CongMapping congMapping) {
        return new CongMappingDTO(congMapping);
    }

    public CongMapping congMappingDTOToCongMapping(CongMappingDTO congMappingDTO) {
        if (congMappingDTO == null) {
            return null;
        } else {
            CongMapping congMapping = new CongMapping();
            congMapping.setId(congMappingDTO.getId());
            congMapping.setCreated(congMappingDTO.getCreated());
            congMapping.setContact(contactFromId(congMappingDTO.getContactId()));
            congMapping.setContactGroup(contactGroupFromId(congMappingDTO.getContactGroupId()));
            return congMapping;
        }
    }
}
