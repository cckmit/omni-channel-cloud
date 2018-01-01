package com.yonyou.occ.ms.customer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CustomerAccount.
 */
@Entity
@Table(name = "cus_customer_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    @Column(name = "code", length = 40)
    private String code;

    @Size(max = 40)
    @Column(name = "name", length = 40)
    private String name;

    @DecimalMin(value = "0")
    @Column(name = "credit", precision=10, scale=2)
    private BigDecimal credit;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

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
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CustomerAccount code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public CustomerAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public CustomerAccount credit(BigDecimal credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public CustomerAccount isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getVersion() {
        return version;
    }

    public CustomerAccount version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDr() {
        return dr;
    }

    public CustomerAccount dr(Integer dr) {
        this.dr = dr;
        return this;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public ZonedDateTime getTs() {
        return ts;
    }

    public CustomerAccount ts(ZonedDateTime ts) {
        this.ts = ts;
        return this;
    }

    public void setTs(ZonedDateTime ts) {
        this.ts = ts;
    }

    public String getCreator() {
        return creator;
    }

    public CustomerAccount creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public CustomerAccount timeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public CustomerAccount modifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public ZonedDateTime getTimeModified() {
        return timeModified;
    }

    public CustomerAccount timeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerAccount customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        CustomerAccount customerAccount = (CustomerAccount) o;
        if (customerAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", credit=" + getCredit() +
            ", isEnabled='" + isIsEnabled() + "'" +
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
