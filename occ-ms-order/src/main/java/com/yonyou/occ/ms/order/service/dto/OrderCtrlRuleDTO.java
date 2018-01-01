package com.yonyou.occ.ms.order.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OrderCtrlRule entity.
 */
public class OrderCtrlRuleDTO implements Serializable {

    private Long id;

    private Boolean autoPoToSo;

    private Boolean isEnabled;

    private Integer version;

    private Integer dr;

    private ZonedDateTime ts;

    @Size(max = 40)
    private String creator;

    private ZonedDateTime timeCreated;

    @Size(max = 40)
    private String modifier;

    private ZonedDateTime timeModified;

    private Long poTypeId;

    private String poTypeName;

    private Long soTypeId;

    private String soTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAutoPoToSo() {
        return autoPoToSo;
    }

    public void setAutoPoToSo(Boolean autoPoToSo) {
        this.autoPoToSo = autoPoToSo;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
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

    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }

    public String getPoTypeName() {
        return poTypeName;
    }

    public void setPoTypeName(String poTypeName) {
        this.poTypeName = poTypeName;
    }

    public Long getSoTypeId() {
        return soTypeId;
    }

    public void setSoTypeId(Long soTypeId) {
        this.soTypeId = soTypeId;
    }

    public String getSoTypeName() {
        return soTypeName;
    }

    public void setSoTypeName(String soTypeName) {
        this.soTypeName = soTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderCtrlRuleDTO orderCtrlRuleDTO = (OrderCtrlRuleDTO) o;
        if(orderCtrlRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderCtrlRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderCtrlRuleDTO{" +
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
