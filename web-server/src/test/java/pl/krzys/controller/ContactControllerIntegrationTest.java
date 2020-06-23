package pl.krzys.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.krzys.TestUtil;
import pl.krzys.dto.ContactDTO;
import pl.krzys.mapper.ContactMapper;
import pl.krzys.model.Contact;
import pl.krzys.repository.ContactRepository;
import pl.krzys.service.ContactService;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactControllerIntegrationTest {

    private static final String DEFAULT_NAME = "Jacek";
    private static final String UPDATED_NAME = "Zenek";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactMapper contactMapper;

    private Contact contact;

    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact();
        contact.setName(DEFAULT_NAME);
        return contact;
    }

    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact();
        contact.setName(UPDATED_NAME);
        return contact;
    }

    @BeforeAll
    void setup() {
        contactRepository.deleteAll();
    }

    @BeforeEach
    void initTest() {
        contact = createEntity(em);
    }

    @Test
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);
        mockMvc.perform(post("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
                .andExpect(status().isCreated());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(DEFAULT_NAME);
    }

}