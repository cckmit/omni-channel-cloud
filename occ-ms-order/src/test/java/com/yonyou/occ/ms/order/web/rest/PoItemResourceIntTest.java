package com.yonyou.occ.ms.order.web.rest;

import com.yonyou.occ.ms.order.OccMsOrderApp;

import com.yonyou.occ.ms.order.config.SecurityBeanOverrideConfiguration;

import com.yonyou.occ.ms.order.domain.PoItem;
import com.yonyou.occ.ms.order.domain.PoState;
import com.yonyou.occ.ms.order.domain.PurchaseOrder;
import com.yonyou.occ.ms.order.repository.PoItemRepository;
import com.yonyou.occ.ms.order.service.dto.PoItemDTO;
import com.yonyou.occ.ms.order.service.mapper.PoItemMapper;
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
 * Test class for the PoItemResource REST controller.
 *
 * @see PoItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsOrderApp.class, SecurityBeanOverrideConfiguration.class})
public class PoItemResourceIntTest {

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
    private PoItemRepository poItemRepository;

    @Autowired
    private PoItemMapper poItemMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoItemMockMvc;

    private PoItem poItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoItemResource poItemResource = new PoItemResource(poItemRepository, poItemMapper);
        this.restPoItemMockMvc = MockMvcBuilders.standaloneSetup(poItemResource)
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
    public static PoItem createEntity(EntityManager em) {
        PoItem poItem = new PoItem()
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
        PoState poItemState = PoStateResourceIntTest.createEntity(em);
        em.persist(poItemState);
        em.flush();
        poItem.setPoItemState(poItemState);
        // Add required entity
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIntTest.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        poItem.setPurchaseOrder(purchaseOrder);
        return poItem;
    }

    @Before
    public void initTest() {
        poItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoItem() throws Exception {
        int databaseSizeBeforeCreate = poItemRepository.findAll().size();

        // Create the PoItem
        PoItemDTO poItemDTO = poItemMapper.toDto(poItem);
        restPoItemMockMvc.perform(post("/api/po-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PoItem in the database
        List<PoItem> poItemList = poItemRepository.findAll();
        assertThat(poItemList).hasSize(databaseSizeBeforeCreate + 1);
        PoItem testPoItem = poItemList.get(poItemList.size() - 1);
        assertThat(testPoItem.getProductCategoryId()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_ID);
        assertThat(testPoItem.getProductCategoryCode()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_CODE);
        assertThat(testPoItem.getProductCategoryName()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_NAME);
        assertThat(testPoItem.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testPoItem.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testPoItem.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testPoItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPoItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPoItem.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPoItem.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testPoItem.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testPoItem.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testPoItem.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testPoItem.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testPoItem.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createPoItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poItemRepository.findAll().size();

        // Create the PoItem with an existing ID
        poItem.setId(1L);
        PoItemDTO poItemDTO = poItemMapper.toDto(poItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoItemMockMvc.perform(post("/api/po-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PoItem in the database
        List<PoItem> poItemList = poItemRepository.findAll();
        assertThat(poItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPoItems() throws Exception {
        // Initialize the database
        poItemRepository.saveAndFlush(poItem);

        // Get all the poItemList
        restPoItemMockMvc.perform(get("/api/po-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poItem.getId().intValue())))
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
    public void getPoItem() throws Exception {
        // Initialize the database
        poItemRepository.saveAndFlush(poItem);

        // Get the poItem
        restPoItemMockMvc.perform(get("/api/po-items/{id}", poItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poItem.getId().intValue()))
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
    public void getNonExistingPoItem() throws Exception {
        // Get the poItem
        restPoItemMockMvc.perform(get("/api/po-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoItem() throws Exception {
        // Initialize the database
        poItemRepository.saveAndFlush(poItem);
        int databaseSizeBeforeUpdate = poItemRepository.findAll().size();

        // Update the poItem
        PoItem updatedPoItem = poItemRepository.findOne(poItem.getId());
        // Disconnect from session so that the updates on updatedPoItem are not directly saved in db
        em.detach(updatedPoItem);
        updatedPoItem
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
        PoItemDTO poItemDTO = poItemMapper.toDto(updatedPoItem);

        restPoItemMockMvc.perform(put("/api/po-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poItemDTO)))
            .andExpect(status().isOk());

        // Validate the PoItem in the database
        List<PoItem> poItemList = poItemRepository.findAll();
        assertThat(poItemList).hasSize(databaseSizeBeforeUpdate);
        PoItem testPoItem = poItemList.get(poItemList.size() - 1);
        assertThat(testPoItem.getProductCategoryId()).isEqualTo(UPDATED_PRODUCT_CATEGORY_ID);
        assertThat(testPoItem.getProductCategoryCode()).isEqualTo(UPDATED_PRODUCT_CATEGORY_CODE);
        assertThat(testPoItem.getProductCategoryName()).isEqualTo(UPDATED_PRODUCT_CATEGORY_NAME);
        assertThat(testPoItem.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testPoItem.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testPoItem.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testPoItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPoItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPoItem.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPoItem.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testPoItem.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testPoItem.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPoItem.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testPoItem.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testPoItem.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingPoItem() throws Exception {
        int databaseSizeBeforeUpdate = poItemRepository.findAll().size();

        // Create the PoItem
        PoItemDTO poItemDTO = poItemMapper.toDto(poItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoItemMockMvc.perform(put("/api/po-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PoItem in the database
        List<PoItem> poItemList = poItemRepository.findAll();
        assertThat(poItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePoItem() throws Exception {
        // Initialize the database
        poItemRepository.saveAndFlush(poItem);
        int databaseSizeBeforeDelete = poItemRepository.findAll().size();

        // Get the poItem
        restPoItemMockMvc.perform(delete("/api/po-items/{id}", poItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PoItem> poItemList = poItemRepository.findAll();
        assertThat(poItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoItem.class);
        PoItem poItem1 = new PoItem();
        poItem1.setId(1L);
        PoItem poItem2 = new PoItem();
        poItem2.setId(poItem1.getId());
        assertThat(poItem1).isEqualTo(poItem2);
        poItem2.setId(2L);
        assertThat(poItem1).isNotEqualTo(poItem2);
        poItem1.setId(null);
        assertThat(poItem1).isNotEqualTo(poItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoItemDTO.class);
        PoItemDTO poItemDTO1 = new PoItemDTO();
        poItemDTO1.setId(1L);
        PoItemDTO poItemDTO2 = new PoItemDTO();
        assertThat(poItemDTO1).isNotEqualTo(poItemDTO2);
        poItemDTO2.setId(poItemDTO1.getId());
        assertThat(poItemDTO1).isEqualTo(poItemDTO2);
        poItemDTO2.setId(2L);
        assertThat(poItemDTO1).isNotEqualTo(poItemDTO2);
        poItemDTO1.setId(null);
        assertThat(poItemDTO1).isNotEqualTo(poItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(poItemMapper.fromId(null)).isNull();
    }
}
