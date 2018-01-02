package com.yonyou.occ.ms.order.web.rest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import com.yonyou.occ.ms.order.OccMsOrderApp;
import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;
import com.yonyou.occ.ms.order.domain.SoType;
import com.yonyou.occ.ms.order.repository.SoTypeRepository;
import com.yonyou.occ.ms.order.service.SoTypeService;
import com.yonyou.occ.ms.order.service.dto.SoTypeDTO;
import com.yonyou.occ.ms.order.service.mapper.SoTypeMapper;
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

import static com.yonyou.occ.ms.order.web.rest.TestUtil.createFormattingConversionService;
import static com.yonyou.occ.ms.order.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the SoTypeResource REST controller.
 *
 * @see SoTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class SoTypeResourceIntTest {

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
    private SoTypeRepository soTypeRepository;

    @Autowired
    private SoTypeMapper soTypeMapper;

    @Autowired
    private SoTypeService soTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSoTypeMockMvc;

    private SoType soType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SoTypeResource soTypeResource = new SoTypeResource(soTypeService);
        this.restSoTypeMockMvc = MockMvcBuilders.standaloneSetup(soTypeResource)
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
    public static SoType createEntity(EntityManager em) {
        SoType soType = new SoType()
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
        return soType;
    }

    @Before
    public void initTest() {
        soType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoType() throws Exception {
        int databaseSizeBeforeCreate = soTypeRepository.findAll().size();

        // Create the SoType
        SoTypeDTO soTypeDTO = soTypeMapper.toDto(soType);
        restSoTypeMockMvc.perform(post("/api/so-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SoType in the database
        List<SoType> soTypeList = soTypeRepository.findAll();
        assertThat(soTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SoType testSoType = soTypeList.get(soTypeList.size() - 1);
        assertThat(testSoType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSoType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSoType.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testSoType.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testSoType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSoType.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testSoType.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testSoType.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testSoType.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testSoType.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testSoType.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createSoTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = soTypeRepository.findAll().size();

        // Create the SoType with an existing ID
        soType.setId("1L");
        SoTypeDTO soTypeDTO = soTypeMapper.toDto(soType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoTypeMockMvc.perform(post("/api/so-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoType in the database
        List<SoType> soTypeList = soTypeRepository.findAll();
        assertThat(soTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSoTypes() throws Exception {
        // Initialize the database
        soTypeRepository.saveAndFlush(soType);

        // Get all the soTypeList
        restSoTypeMockMvc.perform(get("/api/so-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soType.getId())))
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
    public void getSoType() throws Exception {
        // Initialize the database
        soTypeRepository.saveAndFlush(soType);

        // Get the soType
        restSoTypeMockMvc.perform(get("/api/so-types/{id}", soType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(soType.getId()))
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
    public void getNonExistingSoType() throws Exception {
        // Get the soType
        restSoTypeMockMvc.perform(get("/api/so-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoType() throws Exception {
        // Initialize the database
        soTypeRepository.saveAndFlush(soType);
        int databaseSizeBeforeUpdate = soTypeRepository.findAll().size();

        // Update the soType
        SoType updatedSoType = soTypeRepository.findOne(soType.getId());
        // Disconnect from session so that the updates on updatedSoType are not directly saved in db
        em.detach(updatedSoType);
        updatedSoType
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
        SoTypeDTO soTypeDTO = soTypeMapper.toDto(updatedSoType);

        restSoTypeMockMvc.perform(put("/api/so-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SoType in the database
        List<SoType> soTypeList = soTypeRepository.findAll();
        assertThat(soTypeList).hasSize(databaseSizeBeforeUpdate);
        SoType testSoType = soTypeList.get(soTypeList.size() - 1);
        assertThat(testSoType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSoType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSoType.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testSoType.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testSoType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSoType.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testSoType.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testSoType.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testSoType.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testSoType.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testSoType.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingSoType() throws Exception {
        int databaseSizeBeforeUpdate = soTypeRepository.findAll().size();

        // Create the SoType
        SoTypeDTO soTypeDTO = soTypeMapper.toDto(soType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSoTypeMockMvc.perform(put("/api/so-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SoType in the database
        List<SoType> soTypeList = soTypeRepository.findAll();
        assertThat(soTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSoType() throws Exception {
        // Initialize the database
        soTypeRepository.saveAndFlush(soType);
        int databaseSizeBeforeDelete = soTypeRepository.findAll().size();

        // Get the soType
        restSoTypeMockMvc.perform(delete("/api/so-types/{id}", soType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SoType> soTypeList = soTypeRepository.findAll();
        assertThat(soTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoType.class);
        SoType soType1 = new SoType();
        soType1.setId("1L");
        SoType soType2 = new SoType();
        soType2.setId(soType1.getId());
        assertThat(soType1).isEqualTo(soType2);
        soType2.setId("2L");
        assertThat(soType1).isNotEqualTo(soType2);
        soType1.setId(null);
        assertThat(soType1).isNotEqualTo(soType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoTypeDTO.class);
        SoTypeDTO soTypeDTO1 = new SoTypeDTO();
        soTypeDTO1.setId("1L");
        SoTypeDTO soTypeDTO2 = new SoTypeDTO();
        assertThat(soTypeDTO1).isNotEqualTo(soTypeDTO2);
        soTypeDTO2.setId(soTypeDTO1.getId());
        assertThat(soTypeDTO1).isEqualTo(soTypeDTO2);
        soTypeDTO2.setId("2L");
        assertThat(soTypeDTO1).isNotEqualTo(soTypeDTO2);
        soTypeDTO1.setId(null);
        assertThat(soTypeDTO1).isNotEqualTo(soTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(soTypeMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(soTypeMapper.fromId(null)).isNull();
    }
}
