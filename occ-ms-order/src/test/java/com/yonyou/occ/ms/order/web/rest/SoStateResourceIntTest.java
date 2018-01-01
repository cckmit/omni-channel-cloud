package com.yonyou.occ.ms.order.web.rest;

import com.yonyou.occ.ms.order.OccMsOrderApp;

import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;

import com.yonyou.occ.ms.order.domain.SoState;
import com.yonyou.occ.ms.order.repository.SoStateRepository;
import com.yonyou.occ.ms.order.service.SoStateService;
import com.yonyou.occ.ms.order.service.dto.SoStateDTO;
import com.yonyou.occ.ms.order.service.mapper.SoStateMapper;
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
 * Test class for the SoStateResource REST controller.
 *
 * @see SoStateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class SoStateResourceIntTest {

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
    private SoStateRepository soStateRepository;

    @Autowired
    private SoStateMapper soStateMapper;

    @Autowired
    private SoStateService soStateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSoStateMockMvc;

    private SoState soState;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SoStateResource soStateResource = new SoStateResource(soStateService);
        this.restSoStateMockMvc = MockMvcBuilders.standaloneSetup(soStateResource)
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
    public static SoState createEntity(EntityManager em) {
        SoState soState = new SoState()
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
        return soState;
    }

    @Before
    public void initTest() {
        soState = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoState() throws Exception {
        int databaseSizeBeforeCreate = soStateRepository.findAll().size();

        // Create the SoState
        SoStateDTO soStateDTO = soStateMapper.toDto(soState);
        restSoStateMockMvc.perform(post("/api/so-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soStateDTO)))
            .andExpect(status().isCreated());

        // Validate the SoState in the database
        List<SoState> soStateList = soStateRepository.findAll();
        assertThat(soStateList).hasSize(databaseSizeBeforeCreate + 1);
        SoState testSoState = soStateList.get(soStateList.size() - 1);
        assertThat(testSoState.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSoState.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSoState.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testSoState.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testSoState.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSoState.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testSoState.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testSoState.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testSoState.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testSoState.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testSoState.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createSoStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = soStateRepository.findAll().size();

        // Create the SoState with an existing ID
        soState.setId(1L);
        SoStateDTO soStateDTO = soStateMapper.toDto(soState);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoStateMockMvc.perform(post("/api/so-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoState in the database
        List<SoState> soStateList = soStateRepository.findAll();
        assertThat(soStateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSoStates() throws Exception {
        // Initialize the database
        soStateRepository.saveAndFlush(soState);

        // Get all the soStateList
        restSoStateMockMvc.perform(get("/api/so-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soState.getId().intValue())))
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
    public void getSoState() throws Exception {
        // Initialize the database
        soStateRepository.saveAndFlush(soState);

        // Get the soState
        restSoStateMockMvc.perform(get("/api/so-states/{id}", soState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(soState.getId().intValue()))
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
    public void getNonExistingSoState() throws Exception {
        // Get the soState
        restSoStateMockMvc.perform(get("/api/so-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoState() throws Exception {
        // Initialize the database
        soStateRepository.saveAndFlush(soState);
        int databaseSizeBeforeUpdate = soStateRepository.findAll().size();

        // Update the soState
        SoState updatedSoState = soStateRepository.findOne(soState.getId());
        // Disconnect from session so that the updates on updatedSoState are not directly saved in db
        em.detach(updatedSoState);
        updatedSoState
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
        SoStateDTO soStateDTO = soStateMapper.toDto(updatedSoState);

        restSoStateMockMvc.perform(put("/api/so-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soStateDTO)))
            .andExpect(status().isOk());

        // Validate the SoState in the database
        List<SoState> soStateList = soStateRepository.findAll();
        assertThat(soStateList).hasSize(databaseSizeBeforeUpdate);
        SoState testSoState = soStateList.get(soStateList.size() - 1);
        assertThat(testSoState.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSoState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSoState.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testSoState.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testSoState.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSoState.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testSoState.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testSoState.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testSoState.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testSoState.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testSoState.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingSoState() throws Exception {
        int databaseSizeBeforeUpdate = soStateRepository.findAll().size();

        // Create the SoState
        SoStateDTO soStateDTO = soStateMapper.toDto(soState);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSoStateMockMvc.perform(put("/api/so-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soStateDTO)))
            .andExpect(status().isCreated());

        // Validate the SoState in the database
        List<SoState> soStateList = soStateRepository.findAll();
        assertThat(soStateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSoState() throws Exception {
        // Initialize the database
        soStateRepository.saveAndFlush(soState);
        int databaseSizeBeforeDelete = soStateRepository.findAll().size();

        // Get the soState
        restSoStateMockMvc.perform(delete("/api/so-states/{id}", soState.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SoState> soStateList = soStateRepository.findAll();
        assertThat(soStateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoState.class);
        SoState soState1 = new SoState();
        soState1.setId(1L);
        SoState soState2 = new SoState();
        soState2.setId(soState1.getId());
        assertThat(soState1).isEqualTo(soState2);
        soState2.setId(2L);
        assertThat(soState1).isNotEqualTo(soState2);
        soState1.setId(null);
        assertThat(soState1).isNotEqualTo(soState2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoStateDTO.class);
        SoStateDTO soStateDTO1 = new SoStateDTO();
        soStateDTO1.setId(1L);
        SoStateDTO soStateDTO2 = new SoStateDTO();
        assertThat(soStateDTO1).isNotEqualTo(soStateDTO2);
        soStateDTO2.setId(soStateDTO1.getId());
        assertThat(soStateDTO1).isEqualTo(soStateDTO2);
        soStateDTO2.setId(2L);
        assertThat(soStateDTO1).isNotEqualTo(soStateDTO2);
        soStateDTO1.setId(null);
        assertThat(soStateDTO1).isNotEqualTo(soStateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(soStateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(soStateMapper.fromId(null)).isNull();
    }
}
