package com.radixile.limsy.web.rest;

import com.radixile.limsy.LimsyApp;

import com.radixile.limsy.domain.Used;
import com.radixile.limsy.repository.UsedRepository;
import com.radixile.limsy.service.UsedService;
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
import java.util.List;


import static com.radixile.limsy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsedResource REST controller.
 *
 * @see UsedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsyApp.class)
public class UsedResourceIntTest {

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    private static final Boolean DEFAULT_MANUAL_SELECT = false;
    private static final Boolean UPDATED_MANUAL_SELECT = true;

    @Autowired
    private UsedRepository usedRepository;

    @Autowired
    private UsedService usedService;

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

    private MockMvc restUsedMockMvc;

    private Used used;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsedResource usedResource = new UsedResource(usedService);
        this.restUsedMockMvc = MockMvcBuilders.standaloneSetup(usedResource)
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
    public static Used createEntity(EntityManager em) {
        Used used = new Used()
            .selected(DEFAULT_SELECTED)
            .manualSelect(DEFAULT_MANUAL_SELECT);
        return used;
    }

    @Before
    public void initTest() {
        used = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsed() throws Exception {
        int databaseSizeBeforeCreate = usedRepository.findAll().size();

        // Create the Used
        restUsedMockMvc.perform(post("/api/useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(used)))
            .andExpect(status().isCreated());

        // Validate the Used in the database
        List<Used> usedList = usedRepository.findAll();
        assertThat(usedList).hasSize(databaseSizeBeforeCreate + 1);
        Used testUsed = usedList.get(usedList.size() - 1);
        assertThat(testUsed.isSelected()).isEqualTo(DEFAULT_SELECTED);
        assertThat(testUsed.isManualSelect()).isEqualTo(DEFAULT_MANUAL_SELECT);
    }

    @Test
    @Transactional
    public void createUsedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usedRepository.findAll().size();

        // Create the Used with an existing ID
        used.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsedMockMvc.perform(post("/api/useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(used)))
            .andExpect(status().isBadRequest());

        // Validate the Used in the database
        List<Used> usedList = usedRepository.findAll();
        assertThat(usedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUseds() throws Exception {
        // Initialize the database
        usedRepository.saveAndFlush(used);

        // Get all the usedList
        restUsedMockMvc.perform(get("/api/useds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(used.getId().intValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].manualSelect").value(hasItem(DEFAULT_MANUAL_SELECT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUsed() throws Exception {
        // Initialize the database
        usedRepository.saveAndFlush(used);

        // Get the used
        restUsedMockMvc.perform(get("/api/useds/{id}", used.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(used.getId().intValue()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()))
            .andExpect(jsonPath("$.manualSelect").value(DEFAULT_MANUAL_SELECT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsed() throws Exception {
        // Get the used
        restUsedMockMvc.perform(get("/api/useds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsed() throws Exception {
        // Initialize the database
        usedService.save(used);

        int databaseSizeBeforeUpdate = usedRepository.findAll().size();

        // Update the used
        Used updatedUsed = usedRepository.findById(used.getId()).get();
        // Disconnect from session so that the updates on updatedUsed are not directly saved in db
        em.detach(updatedUsed);
        updatedUsed
            .selected(UPDATED_SELECTED)
            .manualSelect(UPDATED_MANUAL_SELECT);

        restUsedMockMvc.perform(put("/api/useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsed)))
            .andExpect(status().isOk());

        // Validate the Used in the database
        List<Used> usedList = usedRepository.findAll();
        assertThat(usedList).hasSize(databaseSizeBeforeUpdate);
        Used testUsed = usedList.get(usedList.size() - 1);
        assertThat(testUsed.isSelected()).isEqualTo(UPDATED_SELECTED);
        assertThat(testUsed.isManualSelect()).isEqualTo(UPDATED_MANUAL_SELECT);
    }

    @Test
    @Transactional
    public void updateNonExistingUsed() throws Exception {
        int databaseSizeBeforeUpdate = usedRepository.findAll().size();

        // Create the Used

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsedMockMvc.perform(put("/api/useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(used)))
            .andExpect(status().isBadRequest());

        // Validate the Used in the database
        List<Used> usedList = usedRepository.findAll();
        assertThat(usedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsed() throws Exception {
        // Initialize the database
        usedService.save(used);

        int databaseSizeBeforeDelete = usedRepository.findAll().size();

        // Delete the used
        restUsedMockMvc.perform(delete("/api/useds/{id}", used.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Used> usedList = usedRepository.findAll();
        assertThat(usedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Used.class);
        Used used1 = new Used();
        used1.setId(1L);
        Used used2 = new Used();
        used2.setId(used1.getId());
        assertThat(used1).isEqualTo(used2);
        used2.setId(2L);
        assertThat(used1).isNotEqualTo(used2);
        used1.setId(null);
        assertThat(used1).isNotEqualTo(used2);
    }
}
