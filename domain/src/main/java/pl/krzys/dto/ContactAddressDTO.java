package pl.krzys.dto;

import lombok.Getter;
import lombok.Setter;
import pl.krzys.model.ContactAddress;

import java.util.StringJoiner;

@Getter
@Setter
public class ContactAddressDTO {

    private Long id;
    private boolean asCompany;
    private boolean business;
    private String city;
    private String country;
    private String district;
    private String postCode;
    private String province;
    private String street;
    private Long contactId;

    public ContactAddressDTO() {
    }

    public ContactAddressDTO(ContactAddress contactAddress) {
        this.id = contactAddress.getId();
        this.asCompany = contactAddress.isAsCompany();
        this.business = contactAddress.isBusiness();
        this.city = contactAddress.getCity();
        this.country = contactAddress.getCountry();
        this.district = contactAddress.getDistrict();
        this.postCode = contactAddress.getPostCode();
        this.province = contactAddress.getProvince();
        this.street = contactAddress.getStreet();
        this.contactId = contactAddress.getContact().getId();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactAddressDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("asCompany=" + asCompany)
                .add("business=" + business)
                .add("city='" + city + "'")
                .add("country='" + country + "'")
                .add("district='" + district + "'")
                .add("postCode='" + postCode + "'")
                .add("province='" + province + "'")
                .add("street='" + street + "'")
                .add("contactId=" + contactId)
                .toString();
    }
}
