package com.yonyou.occ.ms.inventory.web.rest;

import com.yonyou.occ.ms.inventory.OccMsInventoryApp;

import com.yonyou.occ.ms.inventory.config.SecurityBeanOverrideConfiguration;

import com.yonyou.occ.ms.inventory.domain.OperationType;
import com.yonyou.occ.ms.inventory.repository.OperationTypeRepository;
import com.yonyou.occ.ms.inventory.service.OperationTypeService;
import com.yonyou.occ.ms.inventory.service.dto.OperationTypeDTO;
import com.yonyou.occ.ms.inventory.service.mapper.OperationTypeMapper;
import com.yonyou.occ.ms.inventory.web.rest.errors.ExceptionTranslator;

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

import static com.yonyou.occ.ms.inventory.web.rest.TestUtil.sameInstant;
import static com.yonyou.occ.ms.inventory.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OperationTypeResource REST controller.
 *
 * @see OperationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsInventoryApp.class, SecurityBeanOverrideConfiguration.class})
public class OperationTypeResourceIntTest {

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
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private OperationTypeMapper operationTypeMapper;

    @Autowired
    private OperationTypeService operationTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperationTypeMockMvc;

    private OperationType operationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationTypeResource operationTypeResource = new OperationTypeResource(operationTypeService);
        this.restOperationTypeMockMvc = MockMvcBuilders.standaloneSetup(operationTypeResource)
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
    public static OperationType createEntity(EntityManager em) {
        OperationType operationType = new OperationType()
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
        return operationType;
    }

    @Before
    public void initTest() {
        operationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationType() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);
        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperationType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOperationType.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testOperationType.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testOperationType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOperationType.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testOperationType.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testOperationType.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testOperationType.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testOperationType.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testOperationType.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createOperationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // Create the OperationType with an existing ID
        operationType.setId(1L);
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOperationTypes() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList
        restOperationTypeMockMvc.perform(get("/api/operation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationType.getId().intValue())))
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
    public void getOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get the operationType
        restOperationTypeMockMvc.perform(get("/api/operation-types/{id}", operationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operationType.getId().intValue()))
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
    public void getNonExistingOperationType() throws Exception {
        // Get the operationType
        restOperationTypeMockMvc.perform(get("/api/operation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Update the operationType
        OperationType updatedOperationType = operationTypeRepository.findOne(operationType.getId());
        // Disconnect from session so that the updates on updatedOperationType are not directly saved in db
        em.detach(updatedOperationType);
        updatedOperationType
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
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(updatedOperationType);

        restOperationTypeMockMvc.perform(put("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperationType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOperationType.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testOperationType.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testOperationType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOperationType.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testOperationType.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testOperationType.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testOperationType.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testOperationType.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testOperationType.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperationTypeMockMvc.perform(put("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);
        int databaseSizeBeforeDelete = operationTypeRepository.findAll().size();

        // Get the operationType
        restOperationTypeMockMvc.perform(delete("/api/operation-types/{id}", operationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationType.class);
        OperationType operationType1 = new OperationType();
        operationType1.setId(1L);
        OperationType operationType2 = new OperationType();
        operationType2.setId(operationType1.getId());
        assertThat(operationType1).isEqualTo(operationType2);
        operationType2.setId(2L);
        assertThat(operationType1).isNotEqualTo(operationType2);
        operationType1.setId(null);
        assertThat(operationType1).isNotEqualTo(operationType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationTypeDTO.class);
        OperationTypeDTO operationTypeDTO1 = new OperationTypeDTO();
        operationTypeDTO1.setId(1L);
        OperationTypeDTO operationTypeDTO2 = new OperationTypeDTO();
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
        operationTypeDTO2.setId(operationTypeDTO1.getId());
        assertThat(operationTypeDTO1).isEqualTo(operationTypeDTO2);
        operationTypeDTO2.setId(2L);
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
        operationTypeDTO1.setId(null);
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operationTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operationTypeMapper.fromId(null)).isNull();
    }
}
