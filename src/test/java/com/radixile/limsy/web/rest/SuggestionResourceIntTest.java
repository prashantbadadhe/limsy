package com.radixile.limsy.web.rest;

import com.radixile.limsy.LimsyApp;

import com.radixile.limsy.domain.Suggestion;
import com.radixile.limsy.repository.SuggestionRepository;
import com.radixile.limsy.service.SuggestionService;
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
 * Test class for the SuggestionResource REST controller.
 *
 * @see SuggestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsyApp.class)
public class SuggestionResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private SuggestionService suggestionService;

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

    private MockMvc restSuggestionMockMvc;

    private Suggestion suggestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuggestionResource suggestionResource = new SuggestionResource(suggestionService);
        this.restSuggestionMockMvc = MockMvcBuilders.standaloneSetup(suggestionResource)
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
    public static Suggestion createEntity(EntityManager em) {
        Suggestion suggestion = new Suggestion()
            .date(DEFAULT_DATE);
        return suggestion;
    }

    @Before
    public void initTest() {
        suggestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuggestion() throws Exception {
        int databaseSizeBeforeCreate = suggestionRepository.findAll().size();

        // Create the Suggestion
        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isCreated());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeCreate + 1);
        Suggestion testSuggestion = suggestionList.get(suggestionList.size() - 1);
        assertThat(testSuggestion.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createSuggestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suggestionRepository.findAll().size();

        // Create the Suggestion with an existing ID
        suggestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setDate(null);

        // Create the Suggestion, which fails.

        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuggestions() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        // Get all the suggestionList
        restSuggestionMockMvc.perform(get("/api/suggestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        // Get the suggestion
        restSuggestionMockMvc.perform(get("/api/suggestions/{id}", suggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suggestion.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuggestion() throws Exception {
        // Get the suggestion
        restSuggestionMockMvc.perform(get("/api/suggestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuggestion() throws Exception {
        // Initialize the database
        suggestionService.save(suggestion);

        int databaseSizeBeforeUpdate = suggestionRepository.findAll().size();

        // Update the suggestion
        Suggestion updatedSuggestion = suggestionRepository.findById(suggestion.getId()).get();
        // Disconnect from session so that the updates on updatedSuggestion are not directly saved in db
        em.detach(updatedSuggestion);
        updatedSuggestion
            .date(UPDATED_DATE);

        restSuggestionMockMvc.perform(put("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuggestion)))
            .andExpect(status().isOk());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeUpdate);
        Suggestion testSuggestion = suggestionList.get(suggestionList.size() - 1);
        assertThat(testSuggestion.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSuggestion() throws Exception {
        int databaseSizeBeforeUpdate = suggestionRepository.findAll().size();

        // Create the Suggestion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuggestionMockMvc.perform(put("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuggestion() throws Exception {
        // Initialize the database
        suggestionService.save(suggestion);

        int databaseSizeBeforeDelete = suggestionRepository.findAll().size();

        // Delete the suggestion
        restSuggestionMockMvc.perform(delete("/api/suggestions/{id}", suggestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suggestion.class);
        Suggestion suggestion1 = new Suggestion();
        suggestion1.setId(1L);
        Suggestion suggestion2 = new Suggestion();
        suggestion2.setId(suggestion1.getId());
        assertThat(suggestion1).isEqualTo(suggestion2);
        suggestion2.setId(2L);
        assertThat(suggestion1).isNotEqualTo(suggestion2);
        suggestion1.setId(null);
        assertThat(suggestion1).isNotEqualTo(suggestion2);
    }
}
