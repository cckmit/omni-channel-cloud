package com.yonyou.occ.ms.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "ord_purchase_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 40)
    @Column(name = "code", length = 40)
    private String code;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @DecimalMin(value = "0")
    @Column(name = "total_amount", precision=10, scale=2)
    private BigDecimal totalAmount;

    @Size(max = 40)
    @Column(name = "customer_id", length = 40)
    private String customerId;

    @Size(max = 40)
    @Column(name = "customer_code", length = 40)
    private String customerCode;

    @Size(max = 40)
    @Column(name = "customer_name", length = 40)
    private String customerName;

    @Size(max = 40)
    @Column(name = "account_id", length = 40)
    private String accountId;

    @Size(max = 40)
    @Column(name = "account_code", length = 40)
    private String accountCode;

    @Size(max = 40)
    @Column(name = "account_name", length = 40)
    private String accountName;

    @Column(name = "version")
    private Integer version;

    @Column(name = "dr")
    private Integer dr;

    @Column(name = "ts")
    private ZonedDateTime ts;

    @Size(max = 40)
    @Column(name = "creator", length = 40)
    private String creator;

    @Column(name = "time_created")
    private ZonedDateTime timeCreated;

    @Size(max = 40)
    @Column(name = "modifier", length = 40)
    private String modifier;

    @Column(name = "time_modified")
    private ZonedDateTime timeModified;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PoItem> poItems = new HashSet<>();

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PoPayment> poPayments = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private PoType poType;

    @ManyToOne(optional = false)
    @NotNull
    private PoState poState;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public PurchaseOrder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public PurchaseOrder orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public PurchaseOrder totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public PurchaseOrder customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public PurchaseOrder customerCode(String customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public PurchaseOrder customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public PurchaseOrder accountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public PurchaseOrder accountCode(String accountCode) {
        this.accountCode = accountCode;
        return this;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public PurchaseOrder accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getVersion() {
        return version;
    }

    public PurchaseOrder version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDr() {
        return dr;
    }

    public PurchaseOrder dr(Integer dr) {
        this.dr = dr;
        return this;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public ZonedDateTime getTs() {
        return ts;
    }

    public PurchaseOrder ts(ZonedDateTime ts) {
        this.ts = ts;
        return this;
    }

    public void setTs(ZonedDateTime ts) {
        this.ts = ts;
    }

    public String getCreator() {
        return creator;
    }

    public PurchaseOrder creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public PurchaseOrder timeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public PurchaseOrder modifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public ZonedDateTime getTimeModified() {
        return timeModified;
    }

    public PurchaseOrder timeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public Set<PoItem> getPoItems() {
        return poItems;
    }

    public PurchaseOrder poItems(Set<PoItem> poItems) {
        this.poItems = poItems;
        return this;
    }

    public PurchaseOrder addPoItem(PoItem poItem) {
        this.poItems.add(poItem);
        poItem.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removePoItem(PoItem poItem) {
        this.poItems.remove(poItem);
        poItem.setPurchaseOrder(null);
        return this;
    }

    public void setPoItems(Set<PoItem> poItems) {
        this.poItems = poItems;
    }

    public Set<PoPayment> getPoPayments() {
        return poPayments;
    }

    public PurchaseOrder poPayments(Set<PoPayment> poPayments) {
        this.poPayments = poPayments;
        return this;
    }

    public PurchaseOrder addPoPayment(PoPayment poPayment) {
        this.poPayments.add(poPayment);
        poPayment.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removePoPayment(PoPayment poPayment) {
        this.poPayments.remove(poPayment);
        poPayment.setPurchaseOrder(null);
        return this;
    }

    public void setPoPayments(Set<PoPayment> poPayments) {
        this.poPayments = poPayments;
    }

    public PoType getPoType() {
        return poType;
    }

    public PurchaseOrder poType(PoType poType) {
        this.poType = poType;
        return this;
    }

    public void setPoType(PoType poType) {
        this.poType = poType;
    }

    public PoState getPoState() {
        return poState;
    }

    public PurchaseOrder poState(PoState poState) {
        this.poState = poState;
        return this;
    }

    public void setPoState(PoState poState) {
        this.poState = poState;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        if (purchaseOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", customerId='" + getCustomerId() + "'" +
            ", customerCode='" + getCustomerCode() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", accountCode='" + getAccountCode() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", version=" + getVersion() +
            ", dr=" + getDr() +
            ", ts='" + getTs() + "'" +
            ", creator='" + getCreator() + "'" +
            ", timeCreated='" + getTimeCreated() + "'" +
            ", modifier='" + getModifier() + "'" +
            ", timeModified='" + getTimeModified() + "'" +
            "}";
    }
}
