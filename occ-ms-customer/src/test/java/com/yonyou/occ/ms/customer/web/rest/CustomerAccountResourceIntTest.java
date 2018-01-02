package com.yonyou.occ.ms.customer.web.rest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import com.yonyou.occ.ms.customer.OccMsCustomerApp;
import com.yonyou.occ.ms.customer.config.SecurityBeanOverrideConfiguration;
import com.yonyou.occ.ms.customer.domain.Customer;
import com.yonyou.occ.ms.customer.domain.CustomerAccount;
import com.yonyou.occ.ms.customer.repository.CustomerAccountRepository;
import com.yonyou.occ.ms.customer.service.CustomerAccountService;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import com.yonyou.occ.ms.customer.service.mapper.CustomerAccountMapper;
import com.yonyou.occ.ms.customer.web.rest.errors.ExceptionTranslator;
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

import static com.yonyou.occ.ms.customer.web.rest.TestUtil.createFormattingConversionService;
import static com.yonyou.occ.ms.customer.web.rest.TestUtil.sameInstant;
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
 * Test class for the CustomerAccountResource REST controller.
 *
 * @see CustomerAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsCustomerApp.class, SecurityBeanOverrideConfiguration.class})
public class CustomerAccountResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CREDIT = new BigDecimal(0);
    private static final BigDecimal UPDATED_CREDIT = new BigDecimal(1);

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
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerAccountMockMvc;

    private CustomerAccount customerAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerAccountResource customerAccountResource = new CustomerAccountResource(customerAccountService);
        this.restCustomerAccountMockMvc = MockMvcBuilders.standaloneSetup(customerAccountResource)
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
    public static CustomerAccount createEntity(EntityManager em) {
        CustomerAccount customerAccount = new CustomerAccount()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .credit(DEFAULT_CREDIT)
            .isEnabled(DEFAULT_IS_ENABLED)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        Customer customer = CustomerResourceIntTest.createEntity(em);
        em.persist(customer);
        em.flush();
        customerAccount.setCustomer(customer);
        return customerAccount;
    }

    @Before
    public void initTest() {
        customerAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerAccount() throws Exception {
        int databaseSizeBeforeCreate = customerAccountRepository.findAll().size();

        // Create the CustomerAccount
        CustomerAccountDTO customerAccountDTO = customerAccountMapper.toDto(customerAccount);
        restCustomerAccountMockMvc.perform(post("/api/customer-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerAccount in the database
        List<CustomerAccount> customerAccountList = customerAccountRepository.findAll();
        assertThat(customerAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAccount testCustomerAccount = customerAccountList.get(customerAccountList.size() - 1);
        assertThat(testCustomerAccount.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerAccount.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testCustomerAccount.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testCustomerAccount.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCustomerAccount.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testCustomerAccount.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testCustomerAccount.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testCustomerAccount.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testCustomerAccount.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testCustomerAccount.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createCustomerAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerAccountRepository.findAll().size();

        // Create the CustomerAccount with an existing ID
        customerAccount.setId("1L");
        CustomerAccountDTO customerAccountDTO = customerAccountMapper.toDto(customerAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAccountMockMvc.perform(post("/api/customer-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerAccount in the database
        List<CustomerAccount> customerAccountList = customerAccountRepository.findAll();
        assertThat(customerAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerAccounts() throws Exception {
        // Initialize the database
        customerAccountRepository.saveAndFlush(customerAccount);

        // Get all the customerAccountList
        restCustomerAccountMockMvc.perform(get("/api/customer-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAccount.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT.intValue())))
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
    public void getCustomerAccount() throws Exception {
        // Initialize the database
        customerAccountRepository.saveAndFlush(customerAccount);

        // Get the customerAccount
        restCustomerAccountMockMvc.perform(get("/api/customer-accounts/{id}", customerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerAccount.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT.intValue()))
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
    public void getNonExistingCustomerAccount() throws Exception {
        // Get the customerAccount
        restCustomerAccountMockMvc.perform(get("/api/customer-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAccount() throws Exception {
        // Initialize the database
        customerAccountRepository.saveAndFlush(customerAccount);
        int databaseSizeBeforeUpdate = customerAccountRepository.findAll().size();

        // Update the customerAccount
        CustomerAccount updatedCustomerAccount = customerAccountRepository.findOne(customerAccount.getId());
        // Disconnect from session so that the updates on updatedCustomerAccount are not directly saved in db
        em.detach(updatedCustomerAccount);
        updatedCustomerAccount
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .credit(UPDATED_CREDIT)
            .isEnabled(UPDATED_IS_ENABLED)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        CustomerAccountDTO customerAccountDTO = customerAccountMapper.toDto(updatedCustomerAccount);

        restCustomerAccountMockMvc.perform(put("/api/customer-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccountDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerAccount in the database
        List<CustomerAccount> customerAccountList = customerAccountRepository.findAll();
        assertThat(customerAccountList).hasSize(databaseSizeBeforeUpdate);
        CustomerAccount testCustomerAccount = customerAccountList.get(customerAccountList.size() - 1);
        assertThat(testCustomerAccount.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerAccount.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testCustomerAccount.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testCustomerAccount.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCustomerAccount.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testCustomerAccount.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testCustomerAccount.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testCustomerAccount.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testCustomerAccount.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testCustomerAccount.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerAccount() throws Exception {
        int databaseSizeBeforeUpdate = customerAccountRepository.findAll().size();

        // Create the CustomerAccount
        CustomerAccountDTO customerAccountDTO = customerAccountMapper.toDto(customerAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerAccountMockMvc.perform(put("/api/customer-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerAccount in the database
        List<CustomerAccount> customerAccountList = customerAccountRepository.findAll();
        assertThat(customerAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerAccount() throws Exception {
        // Initialize the database
        customerAccountRepository.saveAndFlush(customerAccount);
        int databaseSizeBeforeDelete = customerAccountRepository.findAll().size();

        // Get the customerAccount
        restCustomerAccountMockMvc.perform(delete("/api/customer-accounts/{id}", customerAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerAccount> customerAccountList = customerAccountRepository.findAll();
        assertThat(customerAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAccount.class);
        CustomerAccount customerAccount1 = new CustomerAccount();
        customerAccount1.setId("1L");
        CustomerAccount customerAccount2 = new CustomerAccount();
        customerAccount2.setId(customerAccount1.getId());
        assertThat(customerAccount1).isEqualTo(customerAccount2);
        customerAccount2.setId("2L");
        assertThat(customerAccount1).isNotEqualTo(customerAccount2);
        customerAccount1.setId(null);
        assertThat(customerAccount1).isNotEqualTo(customerAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAccountDTO.class);
        CustomerAccountDTO customerAccountDTO1 = new CustomerAccountDTO();
        customerAccountDTO1.setId("1L");
        CustomerAccountDTO customerAccountDTO2 = new CustomerAccountDTO();
        assertThat(customerAccountDTO1).isNotEqualTo(customerAccountDTO2);
        customerAccountDTO2.setId(customerAccountDTO1.getId());
        assertThat(customerAccountDTO1).isEqualTo(customerAccountDTO2);
        customerAccountDTO2.setId("2L");
        assertThat(customerAccountDTO1).isNotEqualTo(customerAccountDTO2);
        customerAccountDTO1.setId(null);
        assertThat(customerAccountDTO1).isNotEqualTo(customerAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerAccountMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(customerAccountMapper.fromId(null)).isNull();
    }
}
