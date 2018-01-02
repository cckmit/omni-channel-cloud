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
import com.yonyou.occ.ms.inventory.domain.LockLog;
import com.yonyou.occ.ms.inventory.repository.LockLogRepository;
import com.yonyou.occ.ms.inventory.service.dto.LockLogDTO;
import com.yonyou.occ.ms.inventory.service.mapper.LockLogMapper;
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
 * Test class for the LockLogResource REST controller.
 *
 * @see LockLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsInventoryApp.class, SecurityBeanOverrideConfiguration.class})
public class LockLogResourceIntTest {

    private static final BigDecimal DEFAULT_LOCKED_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOCKED_QUANTITY = new BigDecimal(2);

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
    private LockLogRepository lockLogRepository;

    @Autowired
    private LockLogMapper lockLogMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLockLogMockMvc;

    private LockLog lockLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LockLogResource lockLogResource = new LockLogResource(lockLogRepository, lockLogMapper);
        this.restLockLogMockMvc = MockMvcBuilders.standaloneSetup(lockLogResource)
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
    public static LockLog createEntity(EntityManager em) {
        LockLog lockLog = new LockLog()
            .lockedQuantity(DEFAULT_LOCKED_QUANTITY)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        Inventory inventory = InventoryResourceIntTest.createEntity(em);
        em.persist(inventory);
        em.flush();
        lockLog.setInventory(inventory);
        return lockLog;
    }

    @Before
    public void initTest() {
        lockLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createLockLog() throws Exception {
        int databaseSizeBeforeCreate = lockLogRepository.findAll().size();

        // Create the LockLog
        LockLogDTO lockLogDTO = lockLogMapper.toDto(lockLog);
        restLockLogMockMvc.perform(post("/api/lock-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lockLogDTO)))
            .andExpect(status().isCreated());

        // Validate the LockLog in the database
        List<LockLog> lockLogList = lockLogRepository.findAll();
        assertThat(lockLogList).hasSize(databaseSizeBeforeCreate + 1);
        LockLog testLockLog = lockLogList.get(lockLogList.size() - 1);
        assertThat(testLockLog.getLockedQuantity()).isEqualTo(DEFAULT_LOCKED_QUANTITY);
        assertThat(testLockLog.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLockLog.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testLockLog.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testLockLog.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testLockLog.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testLockLog.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testLockLog.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createLockLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lockLogRepository.findAll().size();

        // Create the LockLog with an existing ID
        lockLog.setId("1L");
        LockLogDTO lockLogDTO = lockLogMapper.toDto(lockLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLockLogMockMvc.perform(post("/api/lock-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lockLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LockLog in the database
        List<LockLog> lockLogList = lockLogRepository.findAll();
        assertThat(lockLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLockLogs() throws Exception {
        // Initialize the database
        lockLogRepository.saveAndFlush(lockLog);

        // Get all the lockLogList
        restLockLogMockMvc.perform(get("/api/lock-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lockLog.getId())))
            .andExpect(jsonPath("$.[*].lockedQuantity").value(hasItem(DEFAULT_LOCKED_QUANTITY.intValue())))
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
    public void getLockLog() throws Exception {
        // Initialize the database
        lockLogRepository.saveAndFlush(lockLog);

        // Get the lockLog
        restLockLogMockMvc.perform(get("/api/lock-logs/{id}", lockLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lockLog.getId()))
            .andExpect(jsonPath("$.lockedQuantity").value(DEFAULT_LOCKED_QUANTITY.intValue()))
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
    public void getNonExistingLockLog() throws Exception {
        // Get the lockLog
        restLockLogMockMvc.perform(get("/api/lock-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLockLog() throws Exception {
        // Initialize the database
        lockLogRepository.saveAndFlush(lockLog);
        int databaseSizeBeforeUpdate = lockLogRepository.findAll().size();

        // Update the lockLog
        LockLog updatedLockLog = lockLogRepository.findOne(lockLog.getId());
        // Disconnect from session so that the updates on updatedLockLog are not directly saved in db
        em.detach(updatedLockLog);
        updatedLockLog
            .lockedQuantity(UPDATED_LOCKED_QUANTITY)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        LockLogDTO lockLogDTO = lockLogMapper.toDto(updatedLockLog);

        restLockLogMockMvc.perform(put("/api/lock-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lockLogDTO)))
            .andExpect(status().isOk());

        // Validate the LockLog in the database
        List<LockLog> lockLogList = lockLogRepository.findAll();
        assertThat(lockLogList).hasSize(databaseSizeBeforeUpdate);
        LockLog testLockLog = lockLogList.get(lockLogList.size() - 1);
        assertThat(testLockLog.getLockedQuantity()).isEqualTo(UPDATED_LOCKED_QUANTITY);
        assertThat(testLockLog.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLockLog.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testLockLog.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testLockLog.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testLockLog.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testLockLog.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testLockLog.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingLockLog() throws Exception {
        int databaseSizeBeforeUpdate = lockLogRepository.findAll().size();

        // Create the LockLog
        LockLogDTO lockLogDTO = lockLogMapper.toDto(lockLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLockLogMockMvc.perform(put("/api/lock-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lockLogDTO)))
            .andExpect(status().isCreated());

        // Validate the LockLog in the database
        List<LockLog> lockLogList = lockLogRepository.findAll();
        assertThat(lockLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLockLog() throws Exception {
        // Initialize the database
        lockLogRepository.saveAndFlush(lockLog);
        int databaseSizeBeforeDelete = lockLogRepository.findAll().size();

        // Get the lockLog
        restLockLogMockMvc.perform(delete("/api/lock-logs/{id}", lockLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LockLog> lockLogList = lockLogRepository.findAll();
        assertThat(lockLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LockLog.class);
        LockLog lockLog1 = new LockLog();
        lockLog1.setId("1L");
        LockLog lockLog2 = new LockLog();
        lockLog2.setId(lockLog1.getId());
        assertThat(lockLog1).isEqualTo(lockLog2);
        lockLog2.setId("2L");
        assertThat(lockLog1).isNotEqualTo(lockLog2);
        lockLog1.setId(null);
        assertThat(lockLog1).isNotEqualTo(lockLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LockLogDTO.class);
        LockLogDTO lockLogDTO1 = new LockLogDTO();
        lockLogDTO1.setId("1L");
        LockLogDTO lockLogDTO2 = new LockLogDTO();
        assertThat(lockLogDTO1).isNotEqualTo(lockLogDTO2);
        lockLogDTO2.setId(lockLogDTO1.getId());
        assertThat(lockLogDTO1).isEqualTo(lockLogDTO2);
        lockLogDTO2.setId("2L");
        assertThat(lockLogDTO1).isNotEqualTo(lockLogDTO2);
        lockLogDTO1.setId(null);
        assertThat(lockLogDTO1).isNotEqualTo(lockLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lockLogMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(lockLogMapper.fromId(null)).isNull();
    }
}
