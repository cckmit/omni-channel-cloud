package com.yonyou.occ.ms.order.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PoPayment entity.
 */
public class PoPaymentDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "0")
    private BigDecimal amount;

    private Boolean paymentSuccessful;

    @Size(max = 100)
    private String failedReason;

    private ZonedDateTime timePaid;

    private Integer version;

    private Integer dr;

    private ZonedDateTime ts;

    @Size(max = 40)
    private String creator;

    private ZonedDateTime timeCreated;

    @Size(max = 40)
    private String modifier;

    private ZonedDateTime timeModified;

    private Long purchaseOrderId;

    private String purchaseOrderCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public void setPaymentSuccessful(Boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public ZonedDateTime getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(ZonedDateTime timePaid) {
        this.timePaid = timePaid;
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

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PoPaymentDTO poPaymentDTO = (PoPaymentDTO) o;
        if(poPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoPaymentDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paymentSuccessful='" + isPaymentSuccessful() + "'" +
            ", failedReason='" + getFailedReason() + "'" +
            ", timePaid='" + getTimePaid() + "'" +
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
