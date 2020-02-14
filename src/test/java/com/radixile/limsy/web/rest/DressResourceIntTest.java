package com.radixile.limsy.web.rest;

import com.radixile.limsy.LimsyApp;

import com.radixile.limsy.domain.Dress;
import com.radixile.limsy.repository.DressRepository;
import com.radixile.limsy.service.DressService;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the DressResource REST controller.
 *
 * @see DressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsyApp.class)
public class DressResourceIntTest {

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Boolean DEFAULT_IN_USE = false;
    private static final Boolean UPDATED_IN_USE = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private DressRepository dressRepository;

    @Autowired
    private DressService dressService;

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

    private MockMvc restDressMockMvc;

    private Dress dress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DressResource dressResource = new DressResource(dressService);
        this.restDressMockMvc = MockMvcBuilders.standaloneSetup(dressResource)
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
    public static Dress createEntity(EntityManager em) {
        Dress dress = new Dress()
            .color(DEFAULT_COLOR)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .price(DEFAULT_PRICE)
            .inUse(DEFAULT_IN_USE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return dress;
    }

    @Before
    public void initTest() {
        dress = createEntity(em);
    }

    @Test
    @Transactional
    public void createDress() throws Exception {
        int databaseSizeBeforeCreate = dressRepository.findAll().size();

        // Create the Dress
        restDressMockMvc.perform(post("/api/dresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dress)))
            .andExpect(status().isCreated());

        // Validate the Dress in the database
        List<Dress> dressList = dressRepository.findAll();
        assertThat(dressList).hasSize(databaseSizeBeforeCreate + 1);
        Dress testDress = dressList.get(dressList.size() - 1);
        assertThat(testDress.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testDress.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testDress.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDress.isInUse()).isEqualTo(DEFAULT_IN_USE);
        assertThat(testDress.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDress.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dressRepository.findAll().size();

        // Create the Dress with an existing ID
        dress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDressMockMvc.perform(post("/api/dresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dress)))
            .andExpect(status().isBadRequest());

        // Validate the Dress in the database
        List<Dress> dressList = dressRepository.findAll();
        assertThat(dressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDresses() throws Exception {
        // Initialize the database
        dressRepository.saveAndFlush(dress);

        // Get all the dressList
        restDressMockMvc.perform(get("/api/dresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dress.getId().intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].inUse").value(hasItem(DEFAULT_IN_USE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getDress() throws Exception {
        // Initialize the database
        dressRepository.saveAndFlush(dress);

        // Get the dress
        restDressMockMvc.perform(get("/api/dresses/{id}", dress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dress.getId().intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.inUse").value(DEFAULT_IN_USE.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingDress() throws Exception {
        // Get the dress
        restDressMockMvc.perform(get("/api/dresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDress() throws Exception {
        // Initialize the database
        dressService.save(dress);

        int databaseSizeBeforeUpdate = dressRepository.findAll().size();

        // Update the dress
        Dress updatedDress = dressRepository.findById(dress.getId()).get();
        // Disconnect from session so that the updates on updatedDress are not directly saved in db
        em.detach(updatedDress);
        updatedDress
            .color(UPDATED_COLOR)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .price(UPDATED_PRICE)
            .inUse(UPDATED_IN_USE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restDressMockMvc.perform(put("/api/dresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDress)))
            .andExpect(status().isOk());

        // Validate the Dress in the database
        List<Dress> dressList = dressRepository.findAll();
        assertThat(dressList).hasSize(databaseSizeBeforeUpdate);
        Dress testDress = dressList.get(dressList.size() - 1);
        assertThat(testDress.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testDress.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testDress.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDress.isInUse()).isEqualTo(UPDATED_IN_USE);
        assertThat(testDress.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDress.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDress() throws Exception {
        int databaseSizeBeforeUpdate = dressRepository.findAll().size();

        // Create the Dress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDressMockMvc.perform(put("/api/dresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dress)))
            .andExpect(status().isBadRequest());

        // Validate the Dress in the database
        List<Dress> dressList = dressRepository.findAll();
        assertThat(dressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDress() throws Exception {
        // Initialize the database
        dressService.save(dress);

        int databaseSizeBeforeDelete = dressRepository.findAll().size();

        // Delete the dress
        restDressMockMvc.perform(delete("/api/dresses/{id}", dress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dress> dressList = dressRepository.findAll();
        assertThat(dressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dress.class);
        Dress dress1 = new Dress();
        dress1.setId(1L);
        Dress dress2 = new Dress();
        dress2.setId(dress1.getId());
        assertThat(dress1).isEqualTo(dress2);
        dress2.setId(2L);
        assertThat(dress1).isNotEqualTo(dress2);
        dress1.setId(null);
        assertThat(dress1).isNotEqualTo(dress2);
    }
}
