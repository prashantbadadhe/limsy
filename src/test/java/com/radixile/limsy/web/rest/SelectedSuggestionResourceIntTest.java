package com.radixile.limsy.web.rest;

import com.radixile.limsy.LimsyApp;

import com.radixile.limsy.domain.SelectedSuggestion;
import com.radixile.limsy.repository.SelectedSuggestionRepository;
import com.radixile.limsy.service.SelectedSuggestionService;
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
 * Test class for the SelectedSuggestionResource REST controller.
 *
 * @see SelectedSuggestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsyApp.class)
public class SelectedSuggestionResourceIntTest {

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    private static final Boolean DEFAULT_MANUAL_SELECT = false;
    private static final Boolean UPDATED_MANUAL_SELECT = true;

    @Autowired
    private SelectedSuggestionRepository selectedSuggestionRepository;

    @Autowired
    private SelectedSuggestionService selectedSuggestionService;

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

    private MockMvc restSelectedSuggestionMockMvc;

    private SelectedSuggestion selectedSuggestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelectedSuggestionResource selectedSuggestionResource = new SelectedSuggestionResource(selectedSuggestionService);
        this.restSelectedSuggestionMockMvc = MockMvcBuilders.standaloneSetup(selectedSuggestionResource)
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
    public static SelectedSuggestion createEntity(EntityManager em) {
        SelectedSuggestion selectedSuggestion = new SelectedSuggestion()
            .selected(DEFAULT_SELECTED)
            .manualSelect(DEFAULT_MANUAL_SELECT);
        return selectedSuggestion;
    }

    @Before
    public void initTest() {
        selectedSuggestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSelectedSuggestion() throws Exception {
        int databaseSizeBeforeCreate = selectedSuggestionRepository.findAll().size();

        // Create the SelectedSuggestion
        restSelectedSuggestionMockMvc.perform(post("/api/selected-suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedSuggestion)))
            .andExpect(status().isCreated());

        // Validate the SelectedSuggestion in the database
        List<SelectedSuggestion> selectedSuggestionList = selectedSuggestionRepository.findAll();
        assertThat(selectedSuggestionList).hasSize(databaseSizeBeforeCreate + 1);
        SelectedSuggestion testSelectedSuggestion = selectedSuggestionList.get(selectedSuggestionList.size() - 1);
        assertThat(testSelectedSuggestion.isSelected()).isEqualTo(DEFAULT_SELECTED);
        assertThat(testSelectedSuggestion.isManualSelect()).isEqualTo(DEFAULT_MANUAL_SELECT);
    }

    @Test
    @Transactional
    public void createSelectedSuggestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selectedSuggestionRepository.findAll().size();

        // Create the SelectedSuggestion with an existing ID
        selectedSuggestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelectedSuggestionMockMvc.perform(post("/api/selected-suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedSuggestion)))
            .andExpect(status().isBadRequest());

        // Validate the SelectedSuggestion in the database
        List<SelectedSuggestion> selectedSuggestionList = selectedSuggestionRepository.findAll();
        assertThat(selectedSuggestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSelectedSuggestions() throws Exception {
        // Initialize the database
        selectedSuggestionRepository.saveAndFlush(selectedSuggestion);

        // Get all the selectedSuggestionList
        restSelectedSuggestionMockMvc.perform(get("/api/selected-suggestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectedSuggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].manualSelect").value(hasItem(DEFAULT_MANUAL_SELECT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSelectedSuggestion() throws Exception {
        // Initialize the database
        selectedSuggestionRepository.saveAndFlush(selectedSuggestion);

        // Get the selectedSuggestion
        restSelectedSuggestionMockMvc.perform(get("/api/selected-suggestions/{id}", selectedSuggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(selectedSuggestion.getId().intValue()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()))
            .andExpect(jsonPath("$.manualSelect").value(DEFAULT_MANUAL_SELECT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSelectedSuggestion() throws Exception {
        // Get the selectedSuggestion
        restSelectedSuggestionMockMvc.perform(get("/api/selected-suggestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSelectedSuggestion() throws Exception {
        // Initialize the database
        selectedSuggestionService.save(selectedSuggestion);

        int databaseSizeBeforeUpdate = selectedSuggestionRepository.findAll().size();

        // Update the selectedSuggestion
        SelectedSuggestion updatedSelectedSuggestion = selectedSuggestionRepository.findById(selectedSuggestion.getId()).get();
        // Disconnect from session so that the updates on updatedSelectedSuggestion are not directly saved in db
        em.detach(updatedSelectedSuggestion);
        updatedSelectedSuggestion
            .selected(UPDATED_SELECTED)
            .manualSelect(UPDATED_MANUAL_SELECT);

        restSelectedSuggestionMockMvc.perform(put("/api/selected-suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSelectedSuggestion)))
            .andExpect(status().isOk());

        // Validate the SelectedSuggestion in the database
        List<SelectedSuggestion> selectedSuggestionList = selectedSuggestionRepository.findAll();
        assertThat(selectedSuggestionList).hasSize(databaseSizeBeforeUpdate);
        SelectedSuggestion testSelectedSuggestion = selectedSuggestionList.get(selectedSuggestionList.size() - 1);
        assertThat(testSelectedSuggestion.isSelected()).isEqualTo(UPDATED_SELECTED);
        assertThat(testSelectedSuggestion.isManualSelect()).isEqualTo(UPDATED_MANUAL_SELECT);
    }

    @Test
    @Transactional
    public void updateNonExistingSelectedSuggestion() throws Exception {
        int databaseSizeBeforeUpdate = selectedSuggestionRepository.findAll().size();

        // Create the SelectedSuggestion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelectedSuggestionMockMvc.perform(put("/api/selected-suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedSuggestion)))
            .andExpect(status().isBadRequest());

        // Validate the SelectedSuggestion in the database
        List<SelectedSuggestion> selectedSuggestionList = selectedSuggestionRepository.findAll();
        assertThat(selectedSuggestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSelectedSuggestion() throws Exception {
        // Initialize the database
        selectedSuggestionService.save(selectedSuggestion);

        int databaseSizeBeforeDelete = selectedSuggestionRepository.findAll().size();

        // Delete the selectedSuggestion
        restSelectedSuggestionMockMvc.perform(delete("/api/selected-suggestions/{id}", selectedSuggestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SelectedSuggestion> selectedSuggestionList = selectedSuggestionRepository.findAll();
        assertThat(selectedSuggestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectedSuggestion.class);
        SelectedSuggestion selectedSuggestion1 = new SelectedSuggestion();
        selectedSuggestion1.setId(1L);
        SelectedSuggestion selectedSuggestion2 = new SelectedSuggestion();
        selectedSuggestion2.setId(selectedSuggestion1.getId());
        assertThat(selectedSuggestion1).isEqualTo(selectedSuggestion2);
        selectedSuggestion2.setId(2L);
        assertThat(selectedSuggestion1).isNotEqualTo(selectedSuggestion2);
        selectedSuggestion1.setId(null);
        assertThat(selectedSuggestion1).isNotEqualTo(selectedSuggestion2);
    }
}
