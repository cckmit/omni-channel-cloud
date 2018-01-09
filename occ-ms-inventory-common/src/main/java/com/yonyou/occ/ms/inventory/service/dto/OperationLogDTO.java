package com.yonyou.occ.ms.inventory.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.Size;

/**
 * A DTO for the OperationLog entity.
 */
public class OperationLogDTO implements Serializable {

    private String id;

    private BigDecimal operationQuantity;

    private Integer version;

    private Integer dr;

    private ZonedDateTime ts;

    @Size(max = 40)
    private String creator;

    private ZonedDateTime timeCreated;

    @Size(max = 40)
    private String modifier;

    private ZonedDateTime timeModified;

    private String operationTypeId;

    private String operationTypeName;

    private String inventoryId;

    private String inventoryProductName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getOperationQuantity() {
        return operationQuantity;
    }

    public void setOperationQuantity(BigDecimal operationQuantity) {
        this.operationQuantity = operationQuantity;
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

    public String getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(String operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getOperationTypeName() {
        return operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName) {
        this.operationTypeName = operationTypeName;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryProductName() {
        return inventoryProductName;
    }

    public void setInventoryProductName(String inventoryProductName) {
        this.inventoryProductName = inventoryProductName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationLogDTO operationLogDTO = (OperationLogDTO) o;
        if(operationLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationLogDTO{" +
            "id=" + getId() +
            ", operationQuantity=" + getOperationQuantity() +
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
