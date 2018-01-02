package com.yonyou.occ.ms.order.web.rest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import com.yonyou.occ.ms.order.OccMsOrderApp;
import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;
import com.yonyou.occ.ms.order.domain.PoPayment;
import com.yonyou.occ.ms.order.domain.PurchaseOrder;
import com.yonyou.occ.ms.order.repository.PoPaymentRepository;
import com.yonyou.occ.ms.order.service.dto.PoPaymentDTO;
import com.yonyou.occ.ms.order.service.mapper.PoPaymentMapper;
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
 * Test class for the PoPaymentResource REST controller.
 *
 * @see PoPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class PoPaymentResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    private static final Boolean DEFAULT_PAYMENT_SUCCESSFUL = false;
    private static final Boolean UPDATED_PAYMENT_SUCCESSFUL = true;

    private static final String DEFAULT_FAILED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_FAILED_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_PAID = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_PAID = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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
    private PoPaymentRepository poPaymentRepository;

    @Autowired
    private PoPaymentMapper poPaymentMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoPaymentMockMvc;

    private PoPayment poPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoPaymentResource poPaymentResource = new PoPaymentResource(poPaymentRepository, poPaymentMapper);
        this.restPoPaymentMockMvc = MockMvcBuilders.standaloneSetup(poPaymentResource)
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
    public static PoPayment createEntity(EntityManager em) {
        PoPayment poPayment = new PoPayment()
            .amount(DEFAULT_AMOUNT)
            .paymentSuccessful(DEFAULT_PAYMENT_SUCCESSFUL)
            .failedReason(DEFAULT_FAILED_REASON)
            .timePaid(DEFAULT_TIME_PAID)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIntTest.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        poPayment.setPurchaseOrder(purchaseOrder);
        return poPayment;
    }

    @Before
    public void initTest() {
        poPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoPayment() throws Exception {
        int databaseSizeBeforeCreate = poPaymentRepository.findAll().size();

        // Create the PoPayment
        PoPaymentDTO poPaymentDTO = poPaymentMapper.toDto(poPayment);
        restPoPaymentMockMvc.perform(post("/api/po-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the PoPayment in the database
        List<PoPayment> poPaymentList = poPaymentRepository.findAll();
        assertThat(poPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        PoPayment testPoPayment = poPaymentList.get(poPaymentList.size() - 1);
        assertThat(testPoPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPoPayment.isPaymentSuccessful()).isEqualTo(DEFAULT_PAYMENT_SUCCESSFUL);
        assertThat(testPoPayment.getFailedReason()).isEqualTo(DEFAULT_FAILED_REASON);
        assertThat(testPoPayment.getTimePaid()).isEqualTo(DEFAULT_TIME_PAID);
        assertThat(testPoPayment.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPoPayment.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testPoPayment.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testPoPayment.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testPoPayment.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testPoPayment.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testPoPayment.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createPoPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poPaymentRepository.findAll().size();

        // Create the PoPayment with an existing ID
        poPayment.setId("1L");
        PoPaymentDTO poPaymentDTO = poPaymentMapper.toDto(poPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoPaymentMockMvc.perform(post("/api/po-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PoPayment in the database
        List<PoPayment> poPaymentList = poPaymentRepository.findAll();
        assertThat(poPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPoPayments() throws Exception {
        // Initialize the database
        poPaymentRepository.saveAndFlush(poPayment);

        // Get all the poPaymentList
        restPoPaymentMockMvc.perform(get("/api/po-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poPayment.getId())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentSuccessful").value(hasItem(DEFAULT_PAYMENT_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].failedReason").value(hasItem(DEFAULT_FAILED_REASON.toString())))
            .andExpect(jsonPath("$.[*].timePaid").value(hasItem(sameInstant(DEFAULT_TIME_PAID))))
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
    public void getPoPayment() throws Exception {
        // Initialize the database
        poPaymentRepository.saveAndFlush(poPayment);

        // Get the poPayment
        restPoPaymentMockMvc.perform(get("/api/po-payments/{id}", poPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poPayment.getId()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentSuccessful").value(DEFAULT_PAYMENT_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.failedReason").value(DEFAULT_FAILED_REASON.toString()))
            .andExpect(jsonPath("$.timePaid").value(sameInstant(DEFAULT_TIME_PAID)))
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
    public void getNonExistingPoPayment() throws Exception {
        // Get the poPayment
        restPoPaymentMockMvc.perform(get("/api/po-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoPayment() throws Exception {
        // Initialize the database
        poPaymentRepository.saveAndFlush(poPayment);
        int databaseSizeBeforeUpdate = poPaymentRepository.findAll().size();

        // Update the poPayment
        PoPayment updatedPoPayment = poPaymentRepository.findOne(poPayment.getId());
        // Disconnect from session so that the updates on updatedPoPayment are not directly saved in db
        em.detach(updatedPoPayment);
        updatedPoPayment
            .amount(UPDATED_AMOUNT)
            .paymentSuccessful(UPDATED_PAYMENT_SUCCESSFUL)
            .failedReason(UPDATED_FAILED_REASON)
            .timePaid(UPDATED_TIME_PAID)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        PoPaymentDTO poPaymentDTO = poPaymentMapper.toDto(updatedPoPayment);

        restPoPaymentMockMvc.perform(put("/api/po-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the PoPayment in the database
        List<PoPayment> poPaymentList = poPaymentRepository.findAll();
        assertThat(poPaymentList).hasSize(databaseSizeBeforeUpdate);
        PoPayment testPoPayment = poPaymentList.get(poPaymentList.size() - 1);
        assertThat(testPoPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPoPayment.isPaymentSuccessful()).isEqualTo(UPDATED_PAYMENT_SUCCESSFUL);
        assertThat(testPoPayment.getFailedReason()).isEqualTo(UPDATED_FAILED_REASON);
        assertThat(testPoPayment.getTimePaid()).isEqualTo(UPDATED_TIME_PAID);
        assertThat(testPoPayment.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPoPayment.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testPoPayment.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testPoPayment.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPoPayment.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testPoPayment.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testPoPayment.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingPoPayment() throws Exception {
        int databaseSizeBeforeUpdate = poPaymentRepository.findAll().size();

        // Create the PoPayment
        PoPaymentDTO poPaymentDTO = poPaymentMapper.toDto(poPayment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoPaymentMockMvc.perform(put("/api/po-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the PoPayment in the database
        List<PoPayment> poPaymentList = poPaymentRepository.findAll();
        assertThat(poPaymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePoPayment() throws Exception {
        // Initialize the database
        poPaymentRepository.saveAndFlush(poPayment);
        int databaseSizeBeforeDelete = poPaymentRepository.findAll().size();

        // Get the poPayment
        restPoPaymentMockMvc.perform(delete("/api/po-payments/{id}", poPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PoPayment> poPaymentList = poPaymentRepository.findAll();
        assertThat(poPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoPayment.class);
        PoPayment poPayment1 = new PoPayment();
        poPayment1.setId("1L");
        PoPayment poPayment2 = new PoPayment();
        poPayment2.setId(poPayment1.getId());
        assertThat(poPayment1).isEqualTo(poPayment2);
        poPayment2.setId("2L");
        assertThat(poPayment1).isNotEqualTo(poPayment2);
        poPayment1.setId(null);
        assertThat(poPayment1).isNotEqualTo(poPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoPaymentDTO.class);
        PoPaymentDTO poPaymentDTO1 = new PoPaymentDTO();
        poPaymentDTO1.setId("1L");
        PoPaymentDTO poPaymentDTO2 = new PoPaymentDTO();
        assertThat(poPaymentDTO1).isNotEqualTo(poPaymentDTO2);
        poPaymentDTO2.setId(poPaymentDTO1.getId());
        assertThat(poPaymentDTO1).isEqualTo(poPaymentDTO2);
        poPaymentDTO2.setId("2L");
        assertThat(poPaymentDTO1).isNotEqualTo(poPaymentDTO2);
        poPaymentDTO1.setId(null);
        assertThat(poPaymentDTO1).isNotEqualTo(poPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poPaymentMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(poPaymentMapper.fromId(null)).isNull();
    }
}
