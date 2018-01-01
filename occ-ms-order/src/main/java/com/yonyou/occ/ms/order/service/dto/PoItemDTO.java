package com.yonyou.occ.ms.order.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PoItem entity.
 */
public class PoItemDTO implements Serializable {

    private Long id;

    @Size(max = 40)
    private String productCategoryId;

    @Size(max = 40)
    private String productCategoryCode;

    @Size(max = 40)
    private String productCategoryName;

    @Size(max = 40)
    private String productId;

    @Size(max = 40)
    private String productCode;

    @Size(max = 40)
    private String productName;

    @DecimalMin(value = "0")
    private BigDecimal price;

    @DecimalMin(value = "0")
    private BigDecimal quantity;

    private Integer version;

    private Integer dr;

    private ZonedDateTime ts;

    @Size(max = 40)
    private String creator;

    private ZonedDateTime timeCreated;

    @Size(max = 40)
    private String modifier;

    private ZonedDateTime timeModified;

    private Long poItemStateId;

    private String poItemStateName;

    private Long purchaseOrderId;

    private String purchaseOrderCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public Long getPoItemStateId() {
        return poItemStateId;
    }

    public void setPoItemStateId(Long poStateId) {
        this.poItemStateId = poStateId;
    }

    public String getPoItemStateName() {
        return poItemStateName;
    }

    public void setPoItemStateName(String poStateName) {
        this.poItemStateName = poStateName;
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

        PoItemDTO poItemDTO = (PoItemDTO) o;
        if(poItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoItemDTO{" +
            "id=" + getId() +
            ", productCategoryId='" + getProductCategoryId() + "'" +
            ", productCategoryCode='" + getProductCategoryCode() + "'" +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            ", productId='" + getProductId() + "'" +
            ", productCode='" + getProductCode() + "'" +
            ", productName='" + getProductName() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
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
