package com.yonyou.occ.ms.order.handler;

import java.time.ZonedDateTime;
import java.util.Set;

import com.yonyou.occ.ms.common.domain.vo.customer.Customer;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccount;
import com.yonyou.occ.ms.common.domain.vo.product.Product;
import com.yonyou.occ.ms.common.domain.vo.product.ProductCategory;
import com.yonyou.occ.ms.order.domain.PoItem;
import com.yonyou.occ.ms.order.domain.PoPayment;
import com.yonyou.occ.ms.order.domain.PoState;
import com.yonyou.occ.ms.order.domain.PoType;
import com.yonyou.occ.ms.order.domain.PurchaseOrder;
import com.yonyou.occ.ms.order.enums.PoStateEnum;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderCreatedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderDeletedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPayFailedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPaySuccessfulEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmitConfirmedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmitRollbackedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmittedEvent;
import com.yonyou.occ.ms.order.repository.PoStateRepository;
import com.yonyou.occ.ms.order.repository.PurchaseOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * PurchaseOrder event handler class. PurchaseOrder events are handled here.
 * For instance, using JPA Repository to save entity.
 *
 * @author WangRui
 * @date 2018-01-11 16:08:28
 */
@Slf4j
@Component
public class PurchaseOrderEventHandler {
    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PoStateRepository poStateRepository;

    public PurchaseOrderEventHandler(PurchaseOrderRepository purchaseOrderRepository,
        PoStateRepository poStateRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.poStateRepository = poStateRepository;
    }

    @EventHandler
    public void handle(PurchaseOrderCreatedEvent event) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(event.getId().getId());
        purchaseOrder.setCode(event.getCode());
        purchaseOrder.setOrderDate(ZonedDateTime.now());
        purchaseOrder.setTotalAmount(event.getTotalAmount());
        PoType poType = new PoType();
        poType.setId(event.getPoTypeId().getId());
        purchaseOrder.setPoType(poType);
        Customer customer = event.getCustomer();
        purchaseOrder.setCustomerId(customer.getId().getId());
        purchaseOrder.setCustomerCode(customer.getCode());
        purchaseOrder.setCustomerName(customer.getName());
        CustomerAccount customerAccount = event.getCustomerAccount();
        purchaseOrder.setAccountId(customerAccount.getId().getId());
        purchaseOrder.setAccountCode(customerAccount.getCode());
        purchaseOrder.setAccountName(customerAccount.getName());
        PoState poState = poStateRepository.findByCode(PoStateEnum.CREATED.getCode());
        purchaseOrder.setPoState(poState);

        Set<PoItem> poItems = purchaseOrder.getPoItems();
        PurchaseOrder finalPurchaseOrder = purchaseOrder;
        event.getPoItems().forEach(poi -> {
            PoItem poItem = new PoItem();
            poItem.setPurchaseOrder(finalPurchaseOrder);
            poItem.setId(poi.getId().getId());
            ProductCategory productCategory = poi.getProductCategory();
            poItem.setProductCategoryId(productCategory.getId().getId());
            poItem.setProductCategoryCode(productCategory.getCode());
            poItem.setProductCategoryName(productCategory.getName());
            Product product = poi.getProduct();
            poItem.setProductId(product.getId().getId());
            poItem.setProductCode(product.getCode());
            poItem.setProductName(product.getName());
            poItem.setPrice(poi.getPrice());
            poItem.setQuantity(poi.getQuantity());
            poItems.add(poItem);
        });

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.debug("PurchaseOrder {} is created.", purchaseOrder);
    }

    @EventHandler
    public void handle(PurchaseOrderPayFailedEvent event) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(event.getId().getId());

        Set<PoPayment> poPayments = purchaseOrder.getPoPayments();
        PoPayment poPayment = new PoPayment();
        poPayment.setPurchaseOrder(purchaseOrder);
        poPayment.setAmount(event.getAmount());
        poPayment.setPaymentSuccessful(false);
        poPayment.setTimePaid(ZonedDateTime.now());
        poPayments.add(poPayment);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.debug("PurchaseOrder {} is paid failed.", purchaseOrder);
    }

    @EventHandler
    public void handle(PurchaseOrderPaySuccessfulEvent event) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(event.getId().getId());
        PoState poState = poStateRepository.findByCode(PoStateEnum.PAID.getCode());
        purchaseOrder.setPoState(poState);

        Set<PoPayment> poPayments = purchaseOrder.getPoPayments();
        PoPayment poPayment = new PoPayment();
        poPayment.setPurchaseOrder(purchaseOrder);
        poPayment.setAmount(event.getAmount());
        poPayment.setPaymentSuccessful(true);
        poPayment.setTimePaid(ZonedDateTime.now());
        poPayments.add(poPayment);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.debug("PurchaseOrder {} is paid successful.", purchaseOrder);
    }

    @EventHandler
    public void handle(PurchaseOrderSubmittedEvent event) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(event.getId().getId());
        PoState poState = poStateRepository.findByCode(PoStateEnum.SUBMITTED.getCode());
        purchaseOrder.setPoState(poState);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.debug("PurchaseOrder {} is submitted.", purchaseOrder);
    }

    @EventHandler
    public void handle(PurchaseOrderSubmitRollbackedEvent event) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(event.getId().getId());
        PoState poState = poStateRepository.findByCode(PoStateEnum.PAID.getCode());
        purchaseOrder.setPoState(poState);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.debug("PurchaseOrder submit {} is rollbacked.", purchaseOrder);
    }

    @EventHandler
    public void handle(PurchaseOrderSubmitConfirmedEvent event) {
        log.debug("PurchaseOrder submit {} is confirmed.", event.getId());
    }

    @EventHandler
    public void handle(PurchaseOrderDeletedEvent event) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(event.getId().getId());
        purchaseOrder.setDr(1);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.debug("PurchaseOrder {} is deleted.", purchaseOrder);
    }
}
