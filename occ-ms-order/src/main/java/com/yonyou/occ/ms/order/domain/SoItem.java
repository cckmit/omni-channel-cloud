package com.yonyou.occ.ms.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SoItem.
 */
@Entity
@Table(name = "ord_so_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 40)
    @Column(name = "product_category_id", length = 40)
    private String productCategoryId;

    @Size(max = 40)
    @Column(name = "product_category_code", length = 40)
    private String productCategoryCode;

    @Size(max = 40)
    @Column(name = "product_category_name", length = 40)
    private String productCategoryName;

    @Size(max = 40)
    @Column(name = "product_id", length = 40)
    private String productId;

    @Size(max = 40)
    @Column(name = "product_code", length = 40)
    private String productCode;

    @Size(max = 40)
    @Column(name = "product_name", length = 40)
    private String productName;

    @DecimalMin(value = "0")
    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @DecimalMin(value = "0")
    @Column(name = "quantity", precision=10, scale=2)
    private BigDecimal quantity;

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
    private SoState soItemState;

    @ManyToOne(optional = false)
    @NotNull
    private SaleOrder saleOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public SoItem productCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
        return this;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public SoItem productCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
        return this;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public SoItem productCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
        return this;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductId() {
        return productId;
    }

    public SoItem productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public SoItem productCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public SoItem productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SoItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public SoItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getVersion() {
        return version;
    }

    public SoItem version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDr() {
        return dr;
    }

    public SoItem dr(Integer dr) {
        this.dr = dr;
        return this;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public ZonedDateTime getTs() {
        return ts;
    }

    public SoItem ts(ZonedDateTime ts) {
        this.ts = ts;
        return this;
    }

    public void setTs(ZonedDateTime ts) {
        this.ts = ts;
    }

    public String getCreator() {
        return creator;
    }

    public SoItem creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public SoItem timeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public SoItem modifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public ZonedDateTime getTimeModified() {
        return timeModified;
    }

    public SoItem timeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(ZonedDateTime timeModified) {
        this.timeModified = timeModified;
    }

    public SoState getSoItemState() {
        return soItemState;
    }

    public SoItem soItemState(SoState soState) {
        this.soItemState = soState;
        return this;
    }

    public void setSoItemState(SoState soState) {
        this.soItemState = soState;
    }

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public SoItem saleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
        return this;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
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
        SoItem soItem = (SoItem) o;
        if (soItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), soItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SoItem{" +
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
