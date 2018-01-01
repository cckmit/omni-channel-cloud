package com.yonyou.occ.ms.order.web.rest;

import com.yonyou.occ.ms.order.OccMsOrderApp;

import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;

import com.yonyou.occ.ms.order.domain.PoState;
import com.yonyou.occ.ms.order.repository.PoStateRepository;
import com.yonyou.occ.ms.order.service.PoStateService;
import com.yonyou.occ.ms.order.service.dto.PoStateDTO;
import com.yonyou.occ.ms.order.service.mapper.PoStateMapper;
import com.yonyou.occ.ms.order.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.yonyou.occ.ms.order.web.rest.TestUtil.sameInstant;
import static com.yonyou.occ.ms.order.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PoStateResource REST controller.
 *
 * @see PoStateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class PoStateResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Integer DEFAULT_DR = 1;
    private static final Integer UPDATED_DR = 2;

    private static final ZonedDateTime DEFAULT_TS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATOR = "AAAAAAAAAA";
    private static final String UPDATED_CREATOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIER = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PoStateRepository poStateRepository;

    @Autowired
    private PoStateMapper poStateMapper;

    @Autowired
    private PoStateService poStateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoStateMockMvc;

    private PoState poState;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoStateResource poStateResource = new PoStateResource(poStateService);
        this.restPoStateMockMvc = MockMvcBuilders.standaloneSetup(poStateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoState createEntity(EntityManager em) {
        PoState poState = new PoState()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .isEnabled(DEFAULT_IS_ENABLED)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        return poState;
    }

    @Before
    public void initTest() {
        poState = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoState() throws Exception {
        int databaseSizeBeforeCreate = poStateRepository.findAll().size();

        // Create the PoState
        PoStateDTO poStateDTO = poStateMapper.toDto(poState);
        restPoStateMockMvc.perform(post("/api/po-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poStateDTO)))
            .andExpect(status().isCreated());

        // Validate the PoState in the database
        List<PoState> poStateList = poStateRepository.findAll();
        assertThat(poStateList).hasSize(databaseSizeBeforeCreate + 1);
        PoState testPoState = poStateList.get(poStateList.size() - 1);
        assertThat(testPoState.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPoState.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPoState.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPoState.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testPoState.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPoState.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testPoState.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testPoState.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testPoState.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testPoState.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testPoState.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createPoStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poStateRepository.findAll().size();

        // Create the PoState with an existing ID
        poState.setId(1L);
        PoStateDTO poStateDTO = poStateMapper.toDto(poState);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoStateMockMvc.perform(post("/api/po-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PoState in the database
        List<PoState> poStateList = poStateRepository.findAll();
        assertThat(poStateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPoStates() throws Exception {
        // Initialize the database
        poStateRepository.saveAndFlush(poState);

        // Get all the poStateList
        restPoStateMockMvc.perform(get("/api/po-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poState.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].dr").value(hasItem(DEFAULT_DR)))
            .andExpect(jsonPath("$.[*].ts").value(hasItem(sameInstant(DEFAULT_TS))))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(sameInstant(DEFAULT_TIME_CREATED))))
            .andExpect(jsonPath("$.[*].modifier").value(hasItem(DEFAULT_MODIFIER.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(sameInstant(DEFAULT_TIME_MODIFIED))));
    }

    @Test
    @Transactional
    public void getPoState() throws Exception {
        // Initialize the database
        poStateRepository.saveAndFlush(poState);

        // Get the poState
        restPoStateMockMvc.perform(get("/api/po-states/{id}", poState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poState.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.dr").value(DEFAULT_DR))
            .andExpect(jsonPath("$.ts").value(sameInstant(DEFAULT_TS)))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR.toString()))
            .andExpect(jsonPath("$.timeCreated").value(sameInstant(DEFAULT_TIME_CREATED)))
            .andExpect(jsonPath("$.modifier").value(DEFAULT_MODIFIER.toString()))
            .andExpect(jsonPath("$.timeModified").value(sameInstant(DEFAULT_TIME_MODIFIED)));
    }

    @Test
    @Transactional
    public void getNonExistingPoState() throws Exception {
        // Get the poState
        restPoStateMockMvc.perform(get("/api/po-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoState() throws Exception {
        // Initialize the database
        poStateRepository.saveAndFlush(poState);
        int databaseSizeBeforeUpdate = poStateRepository.findAll().size();

        // Update the poState
        PoState updatedPoState = poStateRepository.findOne(poState.getId());
        // Disconnect from session so that the updates on updatedPoState are not directly saved in db
        em.detach(updatedPoState);
        updatedPoState
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .isEnabled(UPDATED_IS_ENABLED)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        PoStateDTO poStateDTO = poStateMapper.toDto(updatedPoState);

        restPoStateMockMvc.perform(put("/api/po-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poStateDTO)))
            .andExpect(status().isOk());

        // Validate the PoState in the database
        List<PoState> poStateList = poStateRepository.findAll();
        assertThat(poStateList).hasSize(databaseSizeBeforeUpdate);
        PoState testPoState = poStateList.get(poStateList.size() - 1);
        assertThat(testPoState.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPoState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPoState.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPoState.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testPoState.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPoState.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testPoState.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testPoState.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPoState.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testPoState.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testPoState.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingPoState() throws Exception {
        int databaseSizeBeforeUpdate = poStateRepository.findAll().size();

        // Create the PoState
        PoStateDTO poStateDTO = poStateMapper.toDto(poState);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoStateMockMvc.perform(put("/api/po-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poStateDTO)))
            .andExpect(status().isCreated());

        // Validate the PoState in the database
        List<PoState> poStateList = poStateRepository.findAll();
        assertThat(poStateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePoState() throws Exception {
        // Initialize the database
        poStateRepository.saveAndFlush(poState);
        int databaseSizeBeforeDelete = poStateRepository.findAll().size();

        // Get the poState
        restPoStateMockMvc.perform(delete("/api/po-states/{id}", poState.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PoState> poStateList = poStateRepository.findAll();
        assertThat(poStateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoState.class);
        PoState poState1 = new PoState();
        poState1.setId(1L);
        PoState poState2 = new PoState();
        poState2.setId(poState1.getId());
        assertThat(poState1).isEqualTo(poState2);
        poState2.setId(2L);
        assertThat(poState1).isNotEqualTo(poState2);
        poState1.setId(null);
        assertThat(poState1).isNotEqualTo(poState2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoStateDTO.class);
        PoStateDTO poStateDTO1 = new PoStateDTO();
        poStateDTO1.setId(1L);
        PoStateDTO poStateDTO2 = new PoStateDTO();
        assertThat(poStateDTO1).isNotEqualTo(poStateDTO2);
        poStateDTO2.setId(poStateDTO1.getId());
        assertThat(poStateDTO1).isEqualTo(poStateDTO2);
        poStateDTO2.setId(2L);
        assertThat(poStateDTO1).isNotEqualTo(poStateDTO2);
        poStateDTO1.setId(null);
        assertThat(poStateDTO1).isNotEqualTo(poStateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poStateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(poStateMapper.fromId(null)).isNull();
    }
}
