package com.yonyou.occ.ms.inventory.web.rest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import com.yonyou.occ.ms.inventory.OccMsInventoryApp;
import com.yonyou.occ.ms.inventory.config.SecurityBeanOverrideConfiguration;
import com.yonyou.occ.ms.inventory.domain.Inventory;
import com.yonyou.occ.ms.inventory.domain.OperationLog;
import com.yonyou.occ.ms.inventory.domain.OperationType;
import com.yonyou.occ.ms.inventory.repository.OperationLogRepository;
import com.yonyou.occ.ms.inventory.service.dto.OperationLogDTO;
import com.yonyou.occ.ms.inventory.service.mapper.OperationLogMapper;
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

import static com.yonyou.occ.ms.inventory.web.rest.TestUtil.createFormattingConversionService;
import static com.yonyou.occ.ms.inventory.web.rest.TestUtil.sameInstant;
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
 * Test class for the OperationLogResource REST controller.
 *
 * @see OperationLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsInventoryApp.class, SecurityBeanOverrideConfiguration.class})
public class OperationLogResourceIntTest {

    private static final BigDecimal DEFAULT_OPERATION_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OPERATION_QUANTITY = new BigDecimal(2);

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
    private OperationLogRepository operationLogRepository;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperationLogMockMvc;

    private OperationLog operationLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationLogResource operationLogResource = new OperationLogResource(operationLogRepository, operationLogMapper);
        this.restOperationLogMockMvc = MockMvcBuilders.standaloneSetup(operationLogResource)
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
    public static OperationLog createEntity(EntityManager em) {
        OperationLog operationLog = new OperationLog()
            .operationQuantity(DEFAULT_OPERATION_QUANTITY)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        OperationType operationType = OperationTypeResourceIntTest.createEntity(em);
        em.persist(operationType);
        em.flush();
        operationLog.setOperationType(operationType);
        // Add required entity
        Inventory inventory = InventoryResourceIntTest.createEntity(em);
        em.persist(inventory);
        em.flush();
        operationLog.setInventory(inventory);
        return operationLog;
    }

    @Before
    public void initTest() {
        operationLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationLog() throws Exception {
        int databaseSizeBeforeCreate = operationLogRepository.findAll().size();

        // Create the OperationLog
        OperationLogDTO operationLogDTO = operationLogMapper.toDto(operationLog);
        restOperationLogMockMvc.perform(post("/api/operation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationLogDTO)))
            .andExpect(status().isCreated());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeCreate + 1);
        OperationLog testOperationLog = operationLogList.get(operationLogList.size() - 1);
        assertThat(testOperationLog.getOperationQuantity()).isEqualTo(DEFAULT_OPERATION_QUANTITY);
        assertThat(testOperationLog.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOperationLog.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testOperationLog.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testOperationLog.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testOperationLog.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testOperationLog.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testOperationLog.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createOperationLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationLogRepository.findAll().size();

        // Create the OperationLog with an existing ID
        operationLog.setId("1L");
        OperationLogDTO operationLogDTO = operationLogMapper.toDto(operationLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationLogMockMvc.perform(post("/api/operation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOperationLogs() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        // Get all the operationLogList
        restOperationLogMockMvc.perform(get("/api/operation-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationLog.getId())))
            .andExpect(jsonPath("$.[*].operationQuantity").value(hasItem(DEFAULT_OPERATION_QUANTITY.intValue())))
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
    public void getOperationLog() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        // Get the operationLog
        restOperationLogMockMvc.perform(get("/api/operation-logs/{id}", operationLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operationLog.getId()))
            .andExpect(jsonPath("$.operationQuantity").value(DEFAULT_OPERATION_QUANTITY.intValue()))
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
    public void getNonExistingOperationLog() throws Exception {
        // Get the operationLog
        restOperationLogMockMvc.perform(get("/api/operation-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationLog() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();

        // Update the operationLog
        OperationLog updatedOperationLog = operationLogRepository.findOne(operationLog.getId());
        // Disconnect from session so that the updates on updatedOperationLog are not directly saved in db
        em.detach(updatedOperationLog);
        updatedOperationLog
            .operationQuantity(UPDATED_OPERATION_QUANTITY)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        OperationLogDTO operationLogDTO = operationLogMapper.toDto(updatedOperationLog);

        restOperationLogMockMvc.perform(put("/api/operation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationLogDTO)))
            .andExpect(status().isOk());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
        OperationLog testOperationLog = operationLogList.get(operationLogList.size() - 1);
        assertThat(testOperationLog.getOperationQuantity()).isEqualTo(UPDATED_OPERATION_QUANTITY);
        assertThat(testOperationLog.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOperationLog.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testOperationLog.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testOperationLog.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testOperationLog.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testOperationLog.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testOperationLog.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();

        // Create the OperationLog
        OperationLogDTO operationLogDTO = operationLogMapper.toDto(operationLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperationLogMockMvc.perform(put("/api/operation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationLogDTO)))
            .andExpect(status().isCreated());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperationLog() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);
        int databaseSizeBeforeDelete = operationLogRepository.findAll().size();

        // Get the operationLog
        restOperationLogMockMvc.perform(delete("/api/operation-logs/{id}", operationLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationLog.class);
        OperationLog operationLog1 = new OperationLog();
        operationLog1.setId("1L");
        OperationLog operationLog2 = new OperationLog();
        operationLog2.setId(operationLog1.getId());
        assertThat(operationLog1).isEqualTo(operationLog2);
        operationLog2.setId("2L");
        assertThat(operationLog1).isNotEqualTo(operationLog2);
        operationLog1.setId(null);
        assertThat(operationLog1).isNotEqualTo(operationLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationLogDTO.class);
        OperationLogDTO operationLogDTO1 = new OperationLogDTO();
        operationLogDTO1.setId("1L");
        OperationLogDTO operationLogDTO2 = new OperationLogDTO();
        assertThat(operationLogDTO1).isNotEqualTo(operationLogDTO2);
        operationLogDTO2.setId(operationLogDTO1.getId());
        assertThat(operationLogDTO1).isEqualTo(operationLogDTO2);
        operationLogDTO2.setId("2L");
        assertThat(operationLogDTO1).isNotEqualTo(operationLogDTO2);
        operationLogDTO1.setId(null);
        assertThat(operationLogDTO1).isNotEqualTo(operationLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operationLogMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(operationLogMapper.fromId(null)).isNull();
    }
}
