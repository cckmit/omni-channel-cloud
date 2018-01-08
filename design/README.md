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
    - Inventory (DDD - CQRS)
    - LockLog (DDD - CQRS)
    - OperationType (CRUD)
    - OperationLog (DDD - CQRS)
4. Order (Hybrid)
    - PoType (CRUD)
    - PoState (CRUD)
    - SoType (CRUD)
    - SoState (CRUD)
    - OrderCtrlRule (CRUD)
    - PurchaseOrder (DDD - CQRS)
    - PoItem (DDD - CQRS)
    - PoPayment (DDD - CQRS)
    - SaleOrder (DDD - CQRS)
    - SoItem (DDD - CQRS)

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
11. Create a purchase order failed, because the inventory of the product is not enough.
12. Increase the inventory of the product (in-stock).
13. Create a purchase order successfully, lock the inventory of the product.
14. Pay the purchase order failed, because the credit of member's account is not enough.
15. Add credit of the member.
16. Pay the purchase order successfully, subtract the credit of member account, then auto generate a sale order.
17. Reject the sale order, then increase the credit and unlock the inventory.
18. Approve the sale order, change the status of the order, then confirm the subtraction of the inventory(out-stock).
