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
import com.yonyou.occ.ms.inventory.repository.InventoryRepository;
import com.yonyou.occ.ms.inventory.service.InventoryService;
import com.yonyou.occ.ms.inventory.service.dto.InventoryDTO;
import com.yonyou.occ.ms.inventory.service.mapper.InventoryMapper;
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
 * Test class for the InventoryResource REST controller.
 *
 * @see InventoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OccMsInventoryApp.class, SecurityBeanOverrideConfiguration.class})
public class InventoryResourceIntTest {

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TO_SELL_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TO_SELL_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LOCKED_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOCKED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SALED_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALED_QUANTITY = new BigDecimal(2);

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
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryResource inventoryResource = new InventoryResource(inventoryService);
        this.restInventoryMockMvc = MockMvcBuilders.standaloneSetup(inventoryResource)
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
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .productId(DEFAULT_PRODUCT_ID)
            .productCode(DEFAULT_PRODUCT_CODE)
            .productName(DEFAULT_PRODUCT_NAME)
            .toSellQuantity(DEFAULT_TO_SELL_QUANTITY)
            .lockedQuantity(DEFAULT_LOCKED_QUANTITY)
            .saledQuantity(DEFAULT_SALED_QUANTITY)
            .isEnabled(DEFAULT_IS_ENABLED)
            .version(DEFAULT_VERSION)
            .dr(DEFAULT_DR)
            .ts(DEFAULT_TS)
            .creator(DEFAULT_CREATOR)
            .timeCreated(DEFAULT_TIME_CREATED)
            .modifier(DEFAULT_MODIFIER)
            .timeModified(DEFAULT_TIME_MODIFIED);
        return inventory;
    }

    @Before
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testInventory.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testInventory.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testInventory.getToSellQuantity()).isEqualTo(DEFAULT_TO_SELL_QUANTITY);
        assertThat(testInventory.getLockedQuantity()).isEqualTo(DEFAULT_LOCKED_QUANTITY);
        assertThat(testInventory.getSaledQuantity()).isEqualTo(DEFAULT_SALED_QUANTITY);
        assertThat(testInventory.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testInventory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testInventory.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testInventory.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testInventory.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testInventory.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testInventory.getModifier()).isEqualTo(DEFAULT_MODIFIER);
        assertThat(testInventory.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void createInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory with an existing ID
        inventory.setId("1L");
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].toSellQuantity").value(hasItem(DEFAULT_TO_SELL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].lockedQuantity").value(hasItem(DEFAULT_LOCKED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].saledQuantity").value(hasItem(DEFAULT_SALED_QUANTITY.intValue())))
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
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.toSellQuantity").value(DEFAULT_TO_SELL_QUANTITY.intValue()))
            .andExpect(jsonPath("$.lockedQuantity").value(DEFAULT_LOCKED_QUANTITY.intValue()))
            .andExpect(jsonPath("$.saledQuantity").value(DEFAULT_SALED_QUANTITY.intValue()))
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
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findOne(inventory.getId());
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .productId(UPDATED_PRODUCT_ID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .toSellQuantity(UPDATED_TO_SELL_QUANTITY)
            .lockedQuantity(UPDATED_LOCKED_QUANTITY)
            .saledQuantity(UPDATED_SALED_QUANTITY)
            .isEnabled(UPDATED_IS_ENABLED)
            .version(UPDATED_VERSION)
            .dr(UPDATED_DR)
            .ts(UPDATED_TS)
            .creator(UPDATED_CREATOR)
            .timeCreated(UPDATED_TIME_CREATED)
            .modifier(UPDATED_MODIFIER)
            .timeModified(UPDATED_TIME_MODIFIED);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(updatedInventory);

        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testInventory.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testInventory.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testInventory.getToSellQuantity()).isEqualTo(UPDATED_TO_SELL_QUANTITY);
        assertThat(testInventory.getLockedQuantity()).isEqualTo(UPDATED_LOCKED_QUANTITY);
        assertThat(testInventory.getSaledQuantity()).isEqualTo(UPDATED_SALED_QUANTITY);
        assertThat(testInventory.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testInventory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testInventory.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testInventory.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testInventory.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testInventory.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testInventory.getModifier()).isEqualTo(UPDATED_MODIFIER);
        assertThat(testInventory.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Get the inventory
        restInventoryMockMvc.perform(delete("/api/inventories/{id}", inventory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventory.class);
        Inventory inventory1 = new Inventory();
        inventory1.setId("1L");
        Inventory inventory2 = new Inventory();
        inventory2.setId(inventory1.getId());
        assertThat(inventory1).isEqualTo(inventory2);
        inventory2.setId("2L");
        assertThat(inventory1).isNotEqualTo(inventory2);
        inventory1.setId(null);
        assertThat(inventory1).isNotEqualTo(inventory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryDTO.class);
        InventoryDTO inventoryDTO1 = new InventoryDTO();
        inventoryDTO1.setId("1L");
        InventoryDTO inventoryDTO2 = new InventoryDTO();
        assertThat(inventoryDTO1).isNotEqualTo(inventoryDTO2);
        inventoryDTO2.setId(inventoryDTO1.getId());
        assertThat(inventoryDTO1).isEqualTo(inventoryDTO2);
        inventoryDTO2.setId("2L");
        assertThat(inventoryDTO1).isNotEqualTo(inventoryDTO2);
        inventoryDTO1.setId(null);
        assertThat(inventoryDTO1).isNotEqualTo(inventoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inventoryMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(inventoryMapper.fromId(null)).isNull();
    }
}
