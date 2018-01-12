# Micro service project structure
1. occ-ms-_name_: Single project for CRUD or query project in CQRS, contains database entity, Repository, EventHandler, etc.
2. occ-ms-_name_-common: Event, Command, Service API, DTO, etc.
3. occ-ms-_name_-command: Aggregate, CommandHandler, etc.

# Entity
1. Customer (Hybrid)
    - Customer (CRUD)
    - CustomerAccount (DDD - CQRS)
2. Product (CRUD)
    - ProductCategory
    - Product
3. Inventory (Hybrid)
    - OperationType (CRUD)
    - Inventory (DDD - CQRS)
        * LockLog
        * OperationLog
4. Order (Hybrid)
    - PoType (CRUD)
    - PoState (CRUD)
    - SoType (CRUD)
    - SoState (CRUD)
    - OrderCtrlRule (CRUD)
    - PurchaseOrder (DDD - CQRS)
        * PoItem
        * PoPayment
    - SaleOrder
        * SoItem

# Use case
1. Create a customer.
2. Create a account for the customer.
3. Create a product category.
4. Create two products in the category.
5. Create inventory OperationType.
6. Create a PoType.
7. Create a PoState.
8. Create a SoType.
9. Create a SoState.
10. Create a OrderCtrlRule.
11. Create a purchase order.
12. Pay the purchase order failed, because the credit of customer's account is not enough.
13. Increase credit of the customer's account.
14. Pay the purchase order successfully, subtract the credit of customer account.
15. Submit the purchase order. Lock the inventory of the product failed, because the inventory of the product is not enough.
16. Increase the inventory of the product.
17. Submit the purchase order. Lock the inventory of the product successfully, then auto generate a sale order.
18. Reject the sale order, then increase the credit and unlock the inventory.
19. Approve the sale order, change the state of the sale order, then confirm the subtraction of the inventory.
