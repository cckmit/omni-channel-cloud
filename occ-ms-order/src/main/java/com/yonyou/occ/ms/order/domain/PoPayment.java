package com.yonyou.occ.ms.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PoPayment.
 */
@Entity
@Table(name = "ord_po_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PoPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @Column(name = "payment_successful")
    private Boolean paymentSuccessful;

    @Size(max = 100)
    @Column(name = "failed_reason", length = 100)
    private String failedReason;

    @Column(name = "time_paid")
    private ZonedDateTime timePaid;

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

    @ManyToOne(optional = false)
    @NotNull
    private PurchaseOrder purchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PoPayment amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public PoPayment paymentSuccessful(Boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
        return this;
    }

    public void setPaymentSuccessful(Boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public PoPayment failedReason(String failedReason) {
        this.failedReason = failedReason;
        return this;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public ZonedDateTime getTimePaid() {
        return timePaid;
    }

    public PoPayment timePaid(ZonedDateTime timePaid) {
        this.timePaid = timePaid;
        return this;
    }

    public void setTimePaid(ZonedDateTime timePaid) {
        this.timePaid = timePaid;
    }

    public Integer getVersion() {
        return version;
    }

    public PoPayment version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDr() {
        return dr;
    }

    public PoPayment dr(Integer dr) {
        this.dr = dr;
        return this;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public ZonedDateTime getTs() {
        return ts;
    }

    public PoPayment ts(ZonedDateTime ts) {
        this.ts = ts;
        return this;
    }

    public void setTs(ZonedDateTime ts) {
        this.ts = ts;
    }

    public String getCreator() {
        return creator;
    }

    public PoPayment creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public PoPayment timeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public PoPayment modifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public ZonedDateTime getTimeModified() {
        return timeModified;
    }

    public PoPayment timeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PoPayment purchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
        PoPayment poPayment = (PoPayment) o;
        if (poPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoPayment{" +
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
