package com.yonyou.occ.ms.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A OrderCtrlRule.
 */
@Entity
@Table(name = "ord_order_ctrl_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderCtrlRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auto_po_to_so")
    private Boolean autoPoToSo;

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
    private PoType poType;

    @ManyToOne(optional = false)
    @NotNull
    private SoType soType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAutoPoToSo() {
        return autoPoToSo;
    }

    public OrderCtrlRule autoPoToSo(Boolean autoPoToSo) {
        this.autoPoToSo = autoPoToSo;
        return this;
    }

    public void setAutoPoToSo(Boolean autoPoToSo) {
        this.autoPoToSo = autoPoToSo;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public OrderCtrlRule isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getVersion() {
        return version;
    }

    public OrderCtrlRule version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDr() {
        return dr;
    }

    public OrderCtrlRule dr(Integer dr) {
        this.dr = dr;
        return this;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public ZonedDateTime getTs() {
        return ts;
    }

    public OrderCtrlRule ts(ZonedDateTime ts) {
        this.ts = ts;
        return this;
    }

    public void setTs(ZonedDateTime ts) {
        this.ts = ts;
    }

    public String getCreator() {
        return creator;
    }

    public OrderCtrlRule creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public OrderCtrlRule timeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public OrderCtrlRule modifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public ZonedDateTime getTimeModified() {
        return timeModified;
    }

    public OrderCtrlRule timeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public PoType getPoType() {
        return poType;
    }

    public OrderCtrlRule poType(PoType poType) {
        this.poType = poType;
        return this;
    }

    public void setPoType(PoType poType) {
        this.poType = poType;
    }

    public SoType getSoType() {
        return soType;
    }

    public OrderCtrlRule soType(SoType soType) {
        this.soType = soType;
        return this;
    }

    public void setSoType(SoType soType) {
        this.soType = soType;
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
        OrderCtrlRule orderCtrlRule = (OrderCtrlRule) o;
        if (orderCtrlRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderCtrlRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderCtrlRule{" +
            "id=" + getId() +
            ", autoPoToSo='" + isAutoPoToSo() + "'" +
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
