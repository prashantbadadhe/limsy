package com.radixile.limsy.web.rest;

import com.radixile.limsy.LimsyApp;

import com.radixile.limsy.domain.UserAdditional;
import com.radixile.limsy.repository.UserAdditionalRepository;
import com.radixile.limsy.service.UserAdditionalService;
import com.radixile.limsy.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.radixile.limsy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserAdditionalResource REST controller.
 *
 * @see UserAdditionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsyApp.class)
public class UserAdditionalResourceIntTest {

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private UserAdditionalRepository userAdditionalRepository;

    @Autowired
    private UserAdditionalService userAdditionalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserAdditionalMockMvc;

    private UserAdditional userAdditional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAdditionalResource userAdditionalResource = new UserAdditionalResource(userAdditionalService);
        this.restUserAdditionalMockMvc = MockMvcBuilders.standaloneSetup(userAdditionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAdditional createEntity(EntityManager em) {
        UserAdditional userAdditional = new UserAdditional()
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL);
        return userAdditional;
    }

    @Before
    public void initTest() {
        userAdditional = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAdditional() throws Exception {
        int databaseSizeBeforeCreate = userAdditionalRepository.findAll().size();

        // Create the UserAdditional
        restUserAdditionalMockMvc.perform(post("/api/user-additionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAdditional)))
            .andExpect(status().isCreated());

        // Validate the UserAdditional in the database
        List<UserAdditional> userAdditionalList = userAdditionalRepository.findAll();
        assertThat(userAdditionalList).hasSize(databaseSizeBeforeCreate + 1);
        UserAdditional testUserAdditional = userAdditionalList.get(userAdditionalList.size() - 1);
        assertThat(testUserAdditional.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserAdditional.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testUserAdditional.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserAdditional.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createUserAdditionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAdditionalRepository.findAll().size();

        // Create the UserAdditional with an existing ID
        userAdditional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAdditionalMockMvc.perform(post("/api/user-additionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAdditional)))
            .andExpect(status().isBadRequest());

        // Validate the UserAdditional in the database
        List<UserAdditional> userAdditionalList = userAdditionalRepository.findAll();
        assertThat(userAdditionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAdditionals() throws Exception {
        // Initialize the database
        userAdditionalRepository.saveAndFlush(userAdditional);

        // Get all the userAdditionalList
        restUserAdditionalMockMvc.perform(get("/api/user-additionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAdditional.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getUserAdditional() throws Exception {
        // Initialize the database
        userAdditionalRepository.saveAndFlush(userAdditional);

        // Get the userAdditional
        restUserAdditionalMockMvc.perform(get("/api/user-additionals/{id}", userAdditional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAdditional.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAdditional() throws Exception {
        // Get the userAdditional
        restUserAdditionalMockMvc.perform(get("/api/user-additionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAdditional() throws Exception {
        // Initialize the database
        userAdditionalService.save(userAdditional);

        int databaseSizeBeforeUpdate = userAdditionalRepository.findAll().size();

        // Update the userAdditional
        UserAdditional updatedUserAdditional = userAdditionalRepository.findById(userAdditional.getId()).get();
        // Disconnect from session so that the updates on updatedUserAdditional are not directly saved in db
        em.detach(updatedUserAdditional);
        updatedUserAdditional
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);

        restUserAdditionalMockMvc.perform(put("/api/user-additionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAdditional)))
            .andExpect(status().isOk());

        // Validate the UserAdditional in the database
        List<UserAdditional> userAdditionalList = userAdditionalRepository.findAll();
        assertThat(userAdditionalList).hasSize(databaseSizeBeforeUpdate);
        UserAdditional testUserAdditional = userAdditionalList.get(userAdditionalList.size() - 1);
        assertThat(testUserAdditional.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserAdditional.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testUserAdditional.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserAdditional.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAdditional() throws Exception {
        int databaseSizeBeforeUpdate = userAdditionalRepository.findAll().size();

        // Create the UserAdditional

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAdditionalMockMvc.perform(put("/api/user-additionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAdditional)))
            .andExpect(status().isBadRequest());

        // Validate the UserAdditional in the database
        List<UserAdditional> userAdditionalList = userAdditionalRepository.findAll();
        assertThat(userAdditionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAdditional() throws Exception {
        // Initialize the database
        userAdditionalService.save(userAdditional);

        int databaseSizeBeforeDelete = userAdditionalRepository.findAll().size();

        // Delete the userAdditional
        restUserAdditionalMockMvc.perform(delete("/api/user-additionals/{id}", userAdditional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAdditional> userAdditionalList = userAdditionalRepository.findAll();
        assertThat(userAdditionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAdditional.class);
        UserAdditional userAdditional1 = new UserAdditional();
        userAdditional1.setId(1L);
        UserAdditional userAdditional2 = new UserAdditional();
        userAdditional2.setId(userAdditional1.getId());
        assertThat(userAdditional1).isEqualTo(userAdditional2);
        userAdditional2.setId(2L);
        assertThat(userAdditional1).isNotEqualTo(userAdditional2);
        userAdditional1.setId(null);
        assertThat(userAdditional1).isNotEqualTo(userAdditional2);
    }
}
