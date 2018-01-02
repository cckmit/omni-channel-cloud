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
import com.yonyou.occ.ms.order.domain.SaleOrder;
import com.yonyou.occ.ms.order.domain.SoItem;
import com.yonyou.occ.ms.order.domain.SoState;
import com.yonyou.occ.ms.order.repository.SoItemRepository;
import com.yonyou.occ.ms.order.service.dto.SoItemDTO;
import com.yonyou.occ.ms.order.service.mapper.SoItemMapper;
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
 * Test class for the SoItemResource REST controller.
 *
 * @see SoItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class SoItemResourceIntTest {

    private static final String DEFAULT_PRODUCT_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CATEGORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(0);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(1);

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
    private SoItemRepository soItemRepository;

    @Autowired
    private SoItemMapper soItemMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSoItemMockMvc;

    private SoItem soItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SoItemResource soItemResource = new SoItemResource(soItemRepository, soItemMapper);
        this.restSoItemMockMvc = MockMvcBuilders.standaloneSetup(soItemResource)
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
    public static SoItem createEntity(EntityManager em) {
        SoItem soItem = new SoItem()
            .productCategoryId(DEFAULT_PRODUCT_CATEGORY_ID)
            .productCategoryCode(DEFAULT_PRODUCT_CATEGORY_CODE)
            .productCategoryName(DEFAULT_PRODUCT_CATEGORY_NAME)
            .productId(DEFAULT_PRODUCT_ID)
            .productCode(DEFAULT_PRODUCT_CODE)
            .productName(DEFAULT_PRODUCT_NAME)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        // Add required entity
        SoState soItemState = SoStateResourceIntTest.createEntity(em);
        em.persist(soItemState);
        em.flush();
        soItem.setSoItemState(soItemState);
        // Add required entity
        SaleOrder saleOrder = SaleOrderResourceIntTest.createEntity(em);
        em.persist(saleOrder);
        em.flush();
        soItem.setSaleOrder(saleOrder);
        return soItem;
    }

    @Before
    public void initTest() {
        soItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoItem() throws Exception {
        int databaseSizeBeforeCreate = soItemRepository.findAll().size();

        // Create the SoItem
        SoItemDTO soItemDTO = soItemMapper.toDto(soItem);
        restSoItemMockMvc.perform(post("/api/so-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SoItem in the database
        List<SoItem> soItemList = soItemRepository.findAll();
        assertThat(soItemList).hasSize(databaseSizeBeforeCreate + 1);
        SoItem testSoItem = soItemList.get(soItemList.size() - 1);
        assertThat(testSoItem.getProductCategoryId()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_ID);
        assertThat(testSoItem.getProductCategoryCode()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_CODE);
        assertThat(testSoItem.getProductCategoryName()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_NAME);
        assertThat(testSoItem.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSoItem.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testSoItem.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testSoItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSoItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSoItem.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSoItem.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testSoItem.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testSoItem.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testSoItem.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testSoItem.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testSoItem.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createSoItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = soItemRepository.findAll().size();

        // Create the SoItem with an existing ID
        soItem.setId("1L");
        SoItemDTO soItemDTO = soItemMapper.toDto(soItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoItemMockMvc.perform(post("/api/so-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoItem in the database
        List<SoItem> soItemList = soItemRepository.findAll();
        assertThat(soItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSoItems() throws Exception {
        // Initialize the database
        soItemRepository.saveAndFlush(soItem);

        // Get all the soItemList
        restSoItemMockMvc.perform(get("/api/so-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soItem.getId())))
            .andExpect(jsonPath("$.[*].productCategoryId").value(hasItem(DEFAULT_PRODUCT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].productCategoryCode").value(hasItem(DEFAULT_PRODUCT_CATEGORY_CODE.toString())))
            .andExpect(jsonPath("$.[*].productCategoryName").value(hasItem(DEFAULT_PRODUCT_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
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
    public void getSoItem() throws Exception {
        // Initialize the database
        soItemRepository.saveAndFlush(soItem);

        // Get the soItem
        restSoItemMockMvc.perform(get("/api/so-items/{id}", soItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(soItem.getId()))
            .andExpect(jsonPath("$.productCategoryId").value(DEFAULT_PRODUCT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.productCategoryCode").value(DEFAULT_PRODUCT_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.productCategoryName").value(DEFAULT_PRODUCT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
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
    public void getNonExistingSoItem() throws Exception {
        // Get the soItem
        restSoItemMockMvc.perform(get("/api/so-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoItem() throws Exception {
        // Initialize the database
        soItemRepository.saveAndFlush(soItem);
        int databaseSizeBeforeUpdate = soItemRepository.findAll().size();

        // Update the soItem
        SoItem updatedSoItem = soItemRepository.findOne(soItem.getId());
        // Disconnect from session so that the updates on updatedSoItem are not directly saved in db
        em.detach(updatedSoItem);
        updatedSoItem
            .productCategoryId(UPDATED_PRODUCT_CATEGORY_ID)
            .productCategoryCode(UPDATED_PRODUCT_CATEGORY_CODE)
            .productCategoryName(UPDATED_PRODUCT_CATEGORY_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        SoItemDTO soItemDTO = soItemMapper.toDto(updatedSoItem);

        restSoItemMockMvc.perform(put("/api/so-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soItemDTO)))
            .andExpect(status().isOk());

        // Validate the SoItem in the database
        List<SoItem> soItemList = soItemRepository.findAll();
        assertThat(soItemList).hasSize(databaseSizeBeforeUpdate);
        SoItem testSoItem = soItemList.get(soItemList.size() - 1);
        assertThat(testSoItem.getProductCategoryId()).isEqualTo(UPDATED_PRODUCT_CATEGORY_ID);
        assertThat(testSoItem.getProductCategoryCode()).isEqualTo(UPDATED_PRODUCT_CATEGORY_CODE);
        assertThat(testSoItem.getProductCategoryName()).isEqualTo(UPDATED_PRODUCT_CATEGORY_NAME);
        assertThat(testSoItem.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSoItem.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testSoItem.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testSoItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSoItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSoItem.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSoItem.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testSoItem.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testSoItem.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testSoItem.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testSoItem.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testSoItem.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingSoItem() throws Exception {
        int databaseSizeBeforeUpdate = soItemRepository.findAll().size();

        // Create the SoItem
        SoItemDTO soItemDTO = soItemMapper.toDto(soItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSoItemMockMvc.perform(put("/api/so-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SoItem in the database
        List<SoItem> soItemList = soItemRepository.findAll();
        assertThat(soItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSoItem() throws Exception {
        // Initialize the database
        soItemRepository.saveAndFlush(soItem);
        int databaseSizeBeforeDelete = soItemRepository.findAll().size();

        // Get the soItem
        restSoItemMockMvc.perform(delete("/api/so-items/{id}", soItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SoItem> soItemList = soItemRepository.findAll();
        assertThat(soItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoItem.class);
        SoItem soItem1 = new SoItem();
        soItem1.setId("1L");
        SoItem soItem2 = new SoItem();
        soItem2.setId(soItem1.getId());
        assertThat(soItem1).isEqualTo(soItem2);
        soItem2.setId("2L");
        assertThat(soItem1).isNotEqualTo(soItem2);
        soItem1.setId(null);
        assertThat(soItem1).isNotEqualTo(soItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoItemDTO.class);
        SoItemDTO soItemDTO1 = new SoItemDTO();
        soItemDTO1.setId("1L");
        SoItemDTO soItemDTO2 = new SoItemDTO();
        assertThat(soItemDTO1).isNotEqualTo(soItemDTO2);
        soItemDTO2.setId(soItemDTO1.getId());
        assertThat(soItemDTO1).isEqualTo(soItemDTO2);
        soItemDTO2.setId("2L");
        assertThat(soItemDTO1).isNotEqualTo(soItemDTO2);
        soItemDTO1.setId(null);
        assertThat(soItemDTO1).isNotEqualTo(soItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(soItemMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(soItemMapper.fromId(null)).isNull();
    }
}
