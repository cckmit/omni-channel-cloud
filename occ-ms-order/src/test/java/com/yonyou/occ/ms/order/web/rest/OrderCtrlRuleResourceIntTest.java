package com.yonyou.occ.ms.order.web.rest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import com.yonyou.occ.ms.order.OccMsOrderApp;
import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;
import com.yonyou.occ.ms.order.domain.OrderCtrlRule;
import com.yonyou.occ.ms.order.domain.PoType;
import com.yonyou.occ.ms.order.domain.SoType;
import com.yonyou.occ.ms.order.repository.OrderCtrlRuleRepository;
import com.yonyou.occ.ms.order.service.OrderCtrlRuleService;
import com.yonyou.occ.ms.order.service.dto.OrderCtrlRuleDTO;
import com.yonyou.occ.ms.order.service.mapper.OrderCtrlRuleMapper;
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
 * Test class for the OrderCtrlRuleResource REST controller.
 *
 * @see OrderCtrlRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class OrderCtrlRuleResourceIntTest {

    private static final Boolean DEFAULT_AUTO_PO_TO_SO = false;
    private static final Boolean UPDATED_AUTO_PO_TO_SO = true;

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
    private OrderCtrlRuleRepository orderCtrlRuleRepository;

    @Autowired
    private OrderCtrlRuleMapper orderCtrlRuleMapper;

    @Autowired
    private OrderCtrlRuleService orderCtrlRuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderCtrlRuleMockMvc;

    private OrderCtrlRule orderCtrlRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderCtrlRuleResource orderCtrlRuleResource = new OrderCtrlRuleResource(orderCtrlRuleService);
        this.restOrderCtrlRuleMockMvc = MockMvcBuilders.standaloneSetup(orderCtrlRuleResource)
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
    public static OrderCtrlRule createEntity(EntityManager em) {
        OrderCtrlRule orderCtrlRule = new OrderCtrlRule()
            .autoPoToSo(DEFAULT_AUTO_PO_TO_SO)
            .isEnabled(DEFAULT_IS_ENABLED)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        PoType poType = PoTypeResourceIntTest.createEntity(em);
        em.persist(poType);
        em.flush();
        orderCtrlRule.setPoType(poType);
        // Add required entity
        SoType soType = SoTypeResourceIntTest.createEntity(em);
        em.persist(soType);
        em.flush();
        orderCtrlRule.setSoType(soType);
        return orderCtrlRule;
    }

    @Before
    public void initTest() {
        orderCtrlRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderCtrlRule() throws Exception {
        int databaseSizeBeforeCreate = orderCtrlRuleRepository.findAll().size();

        // Create the OrderCtrlRule
        OrderCtrlRuleDTO orderCtrlRuleDTO = orderCtrlRuleMapper.toDto(orderCtrlRule);
        restOrderCtrlRuleMockMvc.perform(post("/api/order-ctrl-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCtrlRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderCtrlRule in the database
        List<OrderCtrlRule> orderCtrlRuleList = orderCtrlRuleRepository.findAll();
        assertThat(orderCtrlRuleList).hasSize(databaseSizeBeforeCreate + 1);
        OrderCtrlRule testOrderCtrlRule = orderCtrlRuleList.get(orderCtrlRuleList.size() - 1);
        assertThat(testOrderCtrlRule.isAutoPoToSo()).isEqualTo(DEFAULT_AUTO_PO_TO_SO);
        assertThat(testOrderCtrlRule.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testOrderCtrlRule.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOrderCtrlRule.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testOrderCtrlRule.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testOrderCtrlRule.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testOrderCtrlRule.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testOrderCtrlRule.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testOrderCtrlRule.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createOrderCtrlRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderCtrlRuleRepository.findAll().size();

        // Create the OrderCtrlRule with an existing ID
        orderCtrlRule.setId("1L");
        OrderCtrlRuleDTO orderCtrlRuleDTO = orderCtrlRuleMapper.toDto(orderCtrlRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderCtrlRuleMockMvc.perform(post("/api/order-ctrl-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCtrlRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderCtrlRule in the database
        List<OrderCtrlRule> orderCtrlRuleList = orderCtrlRuleRepository.findAll();
        assertThat(orderCtrlRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderCtrlRules() throws Exception {
        // Initialize the database
        orderCtrlRuleRepository.saveAndFlush(orderCtrlRule);

        // Get all the orderCtrlRuleList
        restOrderCtrlRuleMockMvc.perform(get("/api/order-ctrl-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderCtrlRule.getId())))
            .andExpect(jsonPath("$.[*].autoPoToSo").value(hasItem(DEFAULT_AUTO_PO_TO_SO.booleanValue())))
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
    public void getOrderCtrlRule() throws Exception {
        // Initialize the database
        orderCtrlRuleRepository.saveAndFlush(orderCtrlRule);

        // Get the orderCtrlRule
        restOrderCtrlRuleMockMvc.perform(get("/api/order-ctrl-rules/{id}", orderCtrlRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderCtrlRule.getId()))
            .andExpect(jsonPath("$.autoPoToSo").value(DEFAULT_AUTO_PO_TO_SO.booleanValue()))
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
    public void getNonExistingOrderCtrlRule() throws Exception {
        // Get the orderCtrlRule
        restOrderCtrlRuleMockMvc.perform(get("/api/order-ctrl-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderCtrlRule() throws Exception {
        // Initialize the database
        orderCtrlRuleRepository.saveAndFlush(orderCtrlRule);
        int databaseSizeBeforeUpdate = orderCtrlRuleRepository.findAll().size();

        // Update the orderCtrlRule
        OrderCtrlRule updatedOrderCtrlRule = orderCtrlRuleRepository.findOne(orderCtrlRule.getId());
        // Disconnect from session so that the updates on updatedOrderCtrlRule are not directly saved in db
        em.detach(updatedOrderCtrlRule);
        updatedOrderCtrlRule
            .autoPoToSo(UPDATED_AUTO_PO_TO_SO)
            .isEnabled(UPDATED_IS_ENABLED)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        OrderCtrlRuleDTO orderCtrlRuleDTO = orderCtrlRuleMapper.toDto(updatedOrderCtrlRule);

        restOrderCtrlRuleMockMvc.perform(put("/api/order-ctrl-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCtrlRuleDTO)))
            .andExpect(status().isOk());

        // Validate the OrderCtrlRule in the database
        List<OrderCtrlRule> orderCtrlRuleList = orderCtrlRuleRepository.findAll();
        assertThat(orderCtrlRuleList).hasSize(databaseSizeBeforeUpdate);
        OrderCtrlRule testOrderCtrlRule = orderCtrlRuleList.get(orderCtrlRuleList.size() - 1);
        assertThat(testOrderCtrlRule.isAutoPoToSo()).isEqualTo(UPDATED_AUTO_PO_TO_SO);
        assertThat(testOrderCtrlRule.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testOrderCtrlRule.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOrderCtrlRule.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testOrderCtrlRule.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testOrderCtrlRule.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testOrderCtrlRule.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testOrderCtrlRule.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testOrderCtrlRule.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderCtrlRule() throws Exception {
        int databaseSizeBeforeUpdate = orderCtrlRuleRepository.findAll().size();

        // Create the OrderCtrlRule
        OrderCtrlRuleDTO orderCtrlRuleDTO = orderCtrlRuleMapper.toDto(orderCtrlRule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderCtrlRuleMockMvc.perform(put("/api/order-ctrl-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCtrlRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderCtrlRule in the database
        List<OrderCtrlRule> orderCtrlRuleList = orderCtrlRuleRepository.findAll();
        assertThat(orderCtrlRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderCtrlRule() throws Exception {
        // Initialize the database
        orderCtrlRuleRepository.saveAndFlush(orderCtrlRule);
        int databaseSizeBeforeDelete = orderCtrlRuleRepository.findAll().size();

        // Get the orderCtrlRule
        restOrderCtrlRuleMockMvc.perform(delete("/api/order-ctrl-rules/{id}", orderCtrlRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderCtrlRule> orderCtrlRuleList = orderCtrlRuleRepository.findAll();
        assertThat(orderCtrlRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCtrlRule.class);
        OrderCtrlRule orderCtrlRule1 = new OrderCtrlRule();
        orderCtrlRule1.setId("1L");
        OrderCtrlRule orderCtrlRule2 = new OrderCtrlRule();
        orderCtrlRule2.setId(orderCtrlRule1.getId());
        assertThat(orderCtrlRule1).isEqualTo(orderCtrlRule2);
        orderCtrlRule2.setId("2L");
        assertThat(orderCtrlRule1).isNotEqualTo(orderCtrlRule2);
        orderCtrlRule1.setId(null);
        assertThat(orderCtrlRule1).isNotEqualTo(orderCtrlRule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCtrlRuleDTO.class);
        OrderCtrlRuleDTO orderCtrlRuleDTO1 = new OrderCtrlRuleDTO();
        orderCtrlRuleDTO1.setId("1L");
        OrderCtrlRuleDTO orderCtrlRuleDTO2 = new OrderCtrlRuleDTO();
        assertThat(orderCtrlRuleDTO1).isNotEqualTo(orderCtrlRuleDTO2);
        orderCtrlRuleDTO2.setId(orderCtrlRuleDTO1.getId());
        assertThat(orderCtrlRuleDTO1).isEqualTo(orderCtrlRuleDTO2);
        orderCtrlRuleDTO2.setId("2L");
        assertThat(orderCtrlRuleDTO1).isNotEqualTo(orderCtrlRuleDTO2);
        orderCtrlRuleDTO1.setId(null);
        assertThat(orderCtrlRuleDTO1).isNotEqualTo(orderCtrlRuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderCtrlRuleMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(orderCtrlRuleMapper.fromId(null)).isNull();
    }
}
