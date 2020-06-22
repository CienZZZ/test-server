package pl.krzys.mapper;

import pl.krzys.dto.ContactAddressDTO;
import pl.krzys.model.ContactAddress;

import static pl.krzys.mapper.ContactMapper.contactFromId;

public class ContactAddressMapper {

    public ContactAddressDTO contactAddressToContactAddressDTO(ContactAddress contactAddress) {
        return new ContactAddressDTO(contactAddress);
    }

    public ContactAddress contactAddressDTOToContactAddress(ContactAddressDTO contactAddressDTO) {
        if (contactAddressDTO == null) {
            return null;
        } else {
            ContactAddress contactAddress = new ContactAddress();
            contactAddress.setId(contactAddressDTO.getId());
            contactAddress.setAsCompany(contactAddressDTO.isAsCompany());
            contactAddress.setBusiness(contactAddressDTO.isBusiness());
            contactAddress.setCity(contactAddressDTO.getCity());
            contactAddress.setCountry(contactAddressDTO.getCountry());
            contactAddress.setDistrict(contactAddressDTO.getDistrict());
            contactAddress.setPostCode(contactAddressDTO.getPostCode());
            contactAddress.setProvince(contactAddressDTO.getProvince());
            contactAddress.setStreet(contactAddressDTO.getStreet());
            contactAddress.setContact(contactFromId(contactAddressDTO.getId()));
            return contactAddress;
        }
    }

}
