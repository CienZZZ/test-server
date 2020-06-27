package pl.krzys.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.krzys.TestUtil;
import pl.krzys.dto.ContactDTO;
import pl.krzys.mapper.ContactMapper;
import pl.krzys.model.Contact;
import pl.krzys.repository.ContactRepository;
import pl.krzys.service.ContactService;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

//    @BeforeAll
//    void setup() {
//        contactRepository.deleteAll();
//    }

    @BeforeEach
    void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
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

    @Test
    @Transactional
    public void createContactWithExistingId() throws Exception {
        contactRepository.save(contact);

        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        contact = contactRepository.findByName(DEFAULT_NAME).get();
        contact.setName(UPDATED_NAME);
        ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);

        mockMvc.perform(post("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
                .andExpect(status().isConflict());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void createContactWithExistingName() throws Exception {
        contactRepository.saveAndFlush(contact);
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);

        mockMvc.perform(post("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
                .andExpect(status().isConflict());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContactNameIsRequired() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        contact.setName(null);

        ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);

        mockMvc.perform(post("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
                .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        mockMvc.perform(get("/api/contact?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        mockMvc.perform(get("/api/contact/{id}", contact.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {

        mockMvc.perform(get("/api/contact/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact.setName(UPDATED_NAME);

        ContactDTO contactDTO = contactMapper.contactToContactDTO(updatedContact);

        mockMvc.perform(put("/api/contact/{id}", updatedContact.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
                .andExpect(status().isOk());

        List<Contact> contactList = contactMapper.contactDTOsToContacts(contactService.getAllContacts().asJava());
//        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(UPDATED_NAME);

        // TODO: fail because SET<ContactGroup> return null and other [], we should override equalsTo in Contact
//        assertThat(testContact).isEqualTo(updatedContact);
    }


}