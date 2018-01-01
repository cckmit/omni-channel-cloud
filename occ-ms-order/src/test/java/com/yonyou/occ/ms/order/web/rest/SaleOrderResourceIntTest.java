package com.yonyou.occ.ms.order.web.rest;

import com.yonyou.occ.ms.order.OccMsOrderApp;

import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;

import com.yonyou.occ.ms.order.domain.SaleOrder;
import com.yonyou.occ.ms.order.domain.SoType;
import com.yonyou.occ.ms.order.domain.SoState;
import com.yonyou.occ.ms.order.repository.SaleOrderRepository;
import com.yonyou.occ.ms.order.service.SaleOrderService;
import com.yonyou.occ.ms.order.service.dto.SaleOrderDTO;
import com.yonyou.occ.ms.order.service.mapper.SaleOrderMapper;
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
import java.math.BigDecimal;
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
 * Test class for the SaleOrderResource REST controller.
 *
 * @see SaleOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class SaleOrderResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(1);

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

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
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private SaleOrderService saleOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleOrderMockMvc;

    private SaleOrder saleOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleOrderResource saleOrderResource = new SaleOrderResource(saleOrderService);
        this.restSaleOrderMockMvc = MockMvcBuilders.standaloneSetup(saleOrderResource)
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
    public static SaleOrder createEntity(EntityManager em) {
        SaleOrder saleOrder = new SaleOrder()
            .code(DEFAULT_CODE)
            .orderDate(DEFAULT_ORDER_DATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .customerId(DEFAULT_CUSTOMER_ID)
            .customerCode(DEFAULT_CUSTOMER_CODE)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .accountId(DEFAULT_ACCOUNT_ID)
            .accountCode(DEFAULT_ACCOUNT_CODE)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        SoType soType = SoTypeResourceIntTest.createEntity(em);
        em.persist(soType);
        em.flush();
        saleOrder.setSoType(soType);
        // Add required entity
        SoState soState = SoStateResourceIntTest.createEntity(em);
        em.persist(soState);
        em.flush();
        saleOrder.setSoState(soState);
        return saleOrder;
    }

    @Before
    public void initTest() {
        saleOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleOrder() throws Exception {
        int databaseSizeBeforeCreate = saleOrderRepository.findAll().size();

        // Create the SaleOrder
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(saleOrder);
        restSaleOrderMockMvc.perform(post("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSaleOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testSaleOrder.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testSaleOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSaleOrder.getCustomerCode()).isEqualTo(DEFAULT_CUSTOMER_CODE);
        assertThat(testSaleOrder.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testSaleOrder.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testSaleOrder.getAccountCode()).isEqualTo(DEFAULT_ACCOUNT_CODE);
        assertThat(testSaleOrder.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testSaleOrder.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSaleOrder.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testSaleOrder.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testSaleOrder.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testSaleOrder.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testSaleOrder.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testSaleOrder.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createSaleOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleOrderRepository.findAll().size();

        // Create the SaleOrder with an existing ID
        saleOrder.setId(1L);
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(saleOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleOrderMockMvc.perform(post("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSaleOrders() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList
        restSaleOrderMockMvc.perform(get("/api/sale-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))
            .andExpect(jsonPath("$.[*].customerCode").value(hasItem(DEFAULT_CUSTOMER_CODE.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.toString())))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
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
    public void getSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get the saleOrder
        restSaleOrderMockMvc.perform(get("/api/sale-orders/{id}", saleOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleOrder.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.orderDate").value(sameInstant(DEFAULT_ORDER_DATE)))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.toString()))
            .andExpect(jsonPath("$.customerCode").value(DEFAULT_CUSTOMER_CODE.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID.toString()))
            .andExpect(jsonPath("$.accountCode").value(DEFAULT_ACCOUNT_CODE.toString()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
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
    public void getNonExistingSaleOrder() throws Exception {
        // Get the saleOrder
        restSaleOrderMockMvc.perform(get("/api/sale-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Update the saleOrder
        SaleOrder updatedSaleOrder = saleOrderRepository.findOne(saleOrder.getId());
        // Disconnect from session so that the updates on updatedSaleOrder are not directly saved in db
        em.detach(updatedSaleOrder);
        updatedSaleOrder
            .code(UPDATED_CODE)
            .orderDate(UPDATED_ORDER_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerCode(UPDATED_CUSTOMER_CODE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(updatedSaleOrder);

        restSaleOrderMockMvc.perform(put("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isOk());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSaleOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testSaleOrder.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testSaleOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSaleOrder.getCustomerCode()).isEqualTo(UPDATED_CUSTOMER_CODE);
        assertThat(testSaleOrder.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testSaleOrder.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testSaleOrder.getAccountCode()).isEqualTo(UPDATED_ACCOUNT_CODE);
        assertThat(testSaleOrder.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testSaleOrder.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSaleOrder.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testSaleOrder.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testSaleOrder.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testSaleOrder.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testSaleOrder.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testSaleOrder.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Create the SaleOrder
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(saleOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleOrderMockMvc.perform(put("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);
        int databaseSizeBeforeDelete = saleOrderRepository.findAll().size();

        // Get the saleOrder
        restSaleOrderMockMvc.perform(delete("/api/sale-orders/{id}", saleOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleOrder.class);
        SaleOrder saleOrder1 = new SaleOrder();
        saleOrder1.setId(1L);
        SaleOrder saleOrder2 = new SaleOrder();
        saleOrder2.setId(saleOrder1.getId());
        assertThat(saleOrder1).isEqualTo(saleOrder2);
        saleOrder2.setId(2L);
        assertThat(saleOrder1).isNotEqualTo(saleOrder2);
        saleOrder1.setId(null);
        assertThat(saleOrder1).isNotEqualTo(saleOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleOrderDTO.class);
        SaleOrderDTO saleOrderDTO1 = new SaleOrderDTO();
        saleOrderDTO1.setId(1L);
        SaleOrderDTO saleOrderDTO2 = new SaleOrderDTO();
        assertThat(saleOrderDTO1).isNotEqualTo(saleOrderDTO2);
        saleOrderDTO2.setId(saleOrderDTO1.getId());
        assertThat(saleOrderDTO1).isEqualTo(saleOrderDTO2);
        saleOrderDTO2.setId(2L);
        assertThat(saleOrderDTO1).isNotEqualTo(saleOrderDTO2);
        saleOrderDTO1.setId(null);
        assertThat(saleOrderDTO1).isNotEqualTo(saleOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(saleOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(saleOrderMapper.fromId(null)).isNull();
    }
}
