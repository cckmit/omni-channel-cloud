package com.yonyou.occ.ms.order.web.rest;

import com.yonyou.occ.ms.order.OccMsOrderApp;

import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;

import com.yonyou.occ.ms.order.domain.PoType;
import com.yonyou.occ.ms.order.repository.PoTypeRepository;
import com.yonyou.occ.ms.order.service.PoTypeService;
import com.yonyou.occ.ms.order.service.dto.PoTypeDTO;
import com.yonyou.occ.ms.order.service.mapper.PoTypeMapper;
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
 * Test class for the PoTypeResource REST controller.
 *
 * @see PoTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class PoTypeResourceIntTest {

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
    private PoTypeRepository poTypeRepository;

    @Autowired
    private PoTypeMapper poTypeMapper;

    @Autowired
    private PoTypeService poTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoTypeMockMvc;

    private PoType poType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoTypeResource poTypeResource = new PoTypeResource(poTypeService);
        this.restPoTypeMockMvc = MockMvcBuilders.standaloneSetup(poTypeResource)
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
    public static PoType createEntity(EntityManager em) {
        PoType poType = new PoType()
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
        return poType;
    }

    @Before
    public void initTest() {
        poType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoType() throws Exception {
        int databaseSizeBeforeCreate = poTypeRepository.findAll().size();

        // Create the PoType
        PoTypeDTO poTypeDTO = poTypeMapper.toDto(poType);
        restPoTypeMockMvc.perform(post("/api/po-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PoType in the database
        List<PoType> poTypeList = poTypeRepository.findAll();
        assertThat(poTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PoType testPoType = poTypeList.get(poTypeList.size() - 1);
        assertThat(testPoType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPoType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPoType.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPoType.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testPoType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPoType.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testPoType.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testPoType.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testPoType.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testPoType.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testPoType.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createPoTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poTypeRepository.findAll().size();

        // Create the PoType with an existing ID
        poType.setId(1L);
        PoTypeDTO poTypeDTO = poTypeMapper.toDto(poType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoTypeMockMvc.perform(post("/api/po-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PoType in the database
        List<PoType> poTypeList = poTypeRepository.findAll();
        assertThat(poTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPoTypes() throws Exception {
        // Initialize the database
        poTypeRepository.saveAndFlush(poType);

        // Get all the poTypeList
        restPoTypeMockMvc.perform(get("/api/po-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poType.getId().intValue())))
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
    public void getPoType() throws Exception {
        // Initialize the database
        poTypeRepository.saveAndFlush(poType);

        // Get the poType
        restPoTypeMockMvc.perform(get("/api/po-types/{id}", poType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poType.getId().intValue()))
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
    public void getNonExistingPoType() throws Exception {
        // Get the poType
        restPoTypeMockMvc.perform(get("/api/po-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoType() throws Exception {
        // Initialize the database
        poTypeRepository.saveAndFlush(poType);
        int databaseSizeBeforeUpdate = poTypeRepository.findAll().size();

        // Update the poType
        PoType updatedPoType = poTypeRepository.findOne(poType.getId());
        // Disconnect from session so that the updates on updatedPoType are not directly saved in db
        em.detach(updatedPoType);
        updatedPoType
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
        PoTypeDTO poTypeDTO = poTypeMapper.toDto(updatedPoType);

        restPoTypeMockMvc.perform(put("/api/po-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PoType in the database
        List<PoType> poTypeList = poTypeRepository.findAll();
        assertThat(poTypeList).hasSize(databaseSizeBeforeUpdate);
        PoType testPoType = poTypeList.get(poTypeList.size() - 1);
        assertThat(testPoType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPoType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPoType.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPoType.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testPoType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPoType.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testPoType.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testPoType.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPoType.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testPoType.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testPoType.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingPoType() throws Exception {
        int databaseSizeBeforeUpdate = poTypeRepository.findAll().size();

        // Create the PoType
        PoTypeDTO poTypeDTO = poTypeMapper.toDto(poType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoTypeMockMvc.perform(put("/api/po-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PoType in the database
        List<PoType> poTypeList = poTypeRepository.findAll();
        assertThat(poTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePoType() throws Exception {
        // Initialize the database
        poTypeRepository.saveAndFlush(poType);
        int databaseSizeBeforeDelete = poTypeRepository.findAll().size();

        // Get the poType
        restPoTypeMockMvc.perform(delete("/api/po-types/{id}", poType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PoType> poTypeList = poTypeRepository.findAll();
        assertThat(poTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoType.class);
        PoType poType1 = new PoType();
        poType1.setId(1L);
        PoType poType2 = new PoType();
        poType2.setId(poType1.getId());
        assertThat(poType1).isEqualTo(poType2);
        poType2.setId(2L);
        assertThat(poType1).isNotEqualTo(poType2);
        poType1.setId(null);
        assertThat(poType1).isNotEqualTo(poType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoTypeDTO.class);
        PoTypeDTO poTypeDTO1 = new PoTypeDTO();
        poTypeDTO1.setId(1L);
        PoTypeDTO poTypeDTO2 = new PoTypeDTO();
        assertThat(poTypeDTO1).isNotEqualTo(poTypeDTO2);
        poTypeDTO2.setId(poTypeDTO1.getId());
        assertThat(poTypeDTO1).isEqualTo(poTypeDTO2);
        poTypeDTO2.setId(2L);
        assertThat(poTypeDTO1).isNotEqualTo(poTypeDTO2);
        poTypeDTO1.setId(null);
        assertThat(poTypeDTO1).isNotEqualTo(poTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(poTypeMapper.fromId(null)).isNull();
    }
}
