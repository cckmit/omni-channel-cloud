package com.yonyou.occ.ms.order.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SaleOrder entity.
 */
public class SaleOrderDTO implements Serializable {

    private String id;

    @Size(max = 40)
    private String code;

    private ZonedDateTime orderDate;

    @DecimalMin(value = "0")
    private BigDecimal totalAmount;

    @Size(max = 40)
    private String customerId;

    @Size(max = 40)
    private String customerCode;

    @Size(max = 40)
    private String customerName;

    @Size(max = 40)
    private String accountId;

    @Size(max = 40)
    private String accountCode;

    @Size(max = 40)
    private String accountName;

    private Integer version;

    private Integer dr;

    private ZonedDateTime ts;

    @Size(max = 40)
    private String creator;

    private ZonedDateTime timeCreated;

    @Size(max = 40)
    private String modifier;

    private ZonedDateTime timeModified;

    private String soTypeId;

    private String soTypeName;

    private String soStateId;

    private String soStateName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public ZonedDateTime getTs() {
        return ts;
    }

    public void setTs(ZonedDateTime ts) {
        this.ts = ts;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public ZonedDateTime getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public String getSoTypeId() {
        return soTypeId;
    }

    public void setSoTypeId(String soTypeId) {
        this.soTypeId = soTypeId;
    }

    public String getSoTypeName() {
        return soTypeName;
    }

    public void setSoTypeName(String soTypeName) {
        this.soTypeName = soTypeName;
    }

    public String getSoStateId() {
        return soStateId;
    }

    public void setSoStateId(String soStateId) {
        this.soStateId = soStateId;
    }

    public String getSoStateName() {
        return soStateName;
    }

    public void setSoStateName(String soStateName) {
        this.soStateName = soStateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleOrderDTO saleOrderDTO = (SaleOrderDTO) o;
        if(saleOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleOrderDTO{" +
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
