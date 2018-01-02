# Micro service project structure
1. occ-ms-_name_: Single project for CRUD or query project in CQRS, contains database entity, Repository, EventHandler, etc.
2. occ-ms-_name_-common: Event, Service API, DTO, etc.
3. occ-ms-_name_-command: Aggregate, Command, CommandHandler, etc.

# Entity
1. Customer (CRUD)
    - Customer
    - CustomerAccount
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
5. Create a PoType.
6. Create a SoType.
7. Create a OrderCtrlRule.
8. Create a purchase order failed, because the inventory of the product is not enough.
9. Increase the inventory of the product.
10. Create a purchase order successfully, lock the inventory of the product.
11. Pay the purchase order failed, because the credit of member's account is not enough.
12. Add credit of the member.
13. Pay the purchase order successfully, subtract the credit of member account, then auto generate a sale order.
14. Reject the sale order, then increase the credit and unlock the inventory.
15. Approve the sale order, change the status of the order, then confirm the subtraction of the inventory.
