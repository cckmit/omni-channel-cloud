package com.yonyou.occ.ms.order.command.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yonyou.occ.ms.common.domain.vo.customer.Customer;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccount;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItem;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import com.yonyou.occ.ms.common.domain.vo.product.Product;
import com.yonyou.occ.ms.common.domain.vo.product.ProductCategory;
import com.yonyou.occ.ms.common.domain.vo.product.ProductCategoryId;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import com.yonyou.occ.ms.customer.service.dto.CustomerDTO;
import com.yonyou.occ.ms.order.command.aggregate.PurchaseOrderAggregate;
import com.yonyou.occ.ms.order.command.po.CreatePurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.DeletePurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.FailPayPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.StartPayPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.SuccessPayPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.web.rest.CustomerAccountService;
import com.yonyou.occ.ms.order.command.web.rest.CustomerService;
import com.yonyou.occ.ms.order.command.web.rest.ProductService;
import com.yonyou.occ.ms.product.service.dto.ProductDTO;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PurchaseOrder command handler, all types of PurchaseOrder commands are handled here.
 *
 * @author WangRui
 * @date 2018-01-10 16:45:14
 */
@Component
public class PurchaseOrderCommandHandler {
    private final Repository<PurchaseOrderAggregate> repository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private ProductService productService;

    public PurchaseOrderCommandHandler(Repository<PurchaseOrderAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreatePurchaseOrderCommand command) throws Exception {
        CustomerId customerId = command.getCustomerId();
        CustomerDTO customerDTO = customerService.getCustomer(customerId.getId()).getBody();
        Customer customer = new Customer(customerId, customerDTO.getCode(), customerDTO.getName());
        CustomerAccountId customerAccountId = command.getCustomerAccountId();
        CustomerAccountDTO customerAccountDTO = customerAccountService.getCustomerAccount(customerAccountId.getId())
            .getBody();
        CustomerAccount customerAccount = new CustomerAccount(customerAccountId, customerAccountDTO.getCode(),
            customerAccountDTO.getName());

        List<PoItem> poItems = new ArrayList<>();
        command.getProducts().forEach(((productId, quantity) -> {
            PoItemId poItemId = new PoItemId(UUID.randomUUID().toString());
            ProductDTO productDTO = productService.getProduct(productId.getId()).getBody();
            Product product = new Product(productId, productDTO.getCode(), productDTO.getName());
            ProductCategory productCategory = new ProductCategory(
                new ProductCategoryId(productDTO.getProductCategoryId()), productDTO.getProductCategoryCode(),
                productDTO.getProductCategoryName());
            BigDecimal price = productDTO.getPrice();
            poItems.add(new PoItem(poItemId, productCategory, product, price, quantity));
        }));

        repository.newInstance(
            () -> new PurchaseOrderAggregate(command.getId(), command.getCode(), command.getPoTypeId(), customer,
                customerAccount, poItems));
    }

    @CommandHandler
    public void handle(StartPayPurchaseOrderCommand command) {
        Aggregate<PurchaseOrderAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.startPay());
    }

    @CommandHandler
    public void handle(FailPayPurchaseOrderCommand command) {
        Aggregate<PurchaseOrderAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.failPay(command.getPoPaymentId()));
    }

    @CommandHandler
    public void handle(SuccessPayPurchaseOrderCommand command) {
        Aggregate<PurchaseOrderAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.successPay(command.getPoPaymentId()));
    }

    @CommandHandler
    public void handle(DeletePurchaseOrderCommand command) {
        Aggregate<PurchaseOrderAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.delete());
    }
}
