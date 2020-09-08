/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apple
 */
@Entity
        @Table(name = "accessary")
        @XmlRootElement
        @NamedQueries({
    @NamedQuery(name = "Accessary.findAll", query = "SELECT a FROM Accessary a"),
    @NamedQuery(name = "Accessary.findByAccessaryId", query = "SELECT a FROM Accessary a WHERE a.accessaryId = :accessaryId"),
    @NamedQuery(name = "Accessary.findByAccessaryName", query = "SELECT a FROM Accessary a WHERE a.accessaryName = :accessaryName"),
    @NamedQuery(name = "Accessary.findByCategoryName", query = "SELECT a FROM Accessary a WHERE a.categoryName = :categoryName"),
    @NamedQuery(name = "Accessary.findByPrice", query = "SELECT a FROM Accessary a WHERE a.price = :price"),
    @NamedQuery(name = "Accessary.findByImgLink", query = "SELECT a FROM Accessary a WHERE a.imgLink = :imgLink"),
//    @NamedQuery(name = "Accessary.findByIsAvailable", ),
    @NamedQuery(name = "Accessary.findByDetailLink", query = "SELECT a FROM Accessary a WHERE a.detailLink = :detailLink")})
public class Accessary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessary_id", columnDefinition = "Decimal(1,1)")
    private Integer accessaryId;
    @Basic(optional = false)
    @Column(name = "accessary_name")
    private String accessaryName;
    @Basic(optional = false)
    @Column(name = "category_name")
    private String categoryName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Basic(optional = false)
    @Column(name = "imgLink")
    private String imgLink;
    @Basic(optional = false)
    @Column(name = "detailLink")
    private String detailLink;
    @Basic(optional = false)
    @Column(name = "isAvailable")
    private boolean isAvailable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessaryId")
    private Collection<RecipeAccessary> recipeAccessaryCollection;
    @OneToMany(mappedBy = "accessaryId")
    private Collection<AccessSize> accessSizeCollection;

    public Accessary() {
    }

    public Accessary(Integer accessaryId) {
        this.accessaryId = accessaryId;
    }

    public Accessary(Integer accessaryId, String accessaryName, String categoryName, Double price, String imgLink, String detailLink, boolean isAvailable) {
        this.accessaryId = accessaryId;
        this.accessaryName = accessaryName;
        this.categoryName = categoryName;
        this.price = price;
        this.imgLink = imgLink;
        this.detailLink = detailLink;
        this.isAvailable = isAvailable;
    }

    public Integer getAccessaryId() {
        return accessaryId;
    }

    public void setAccessaryId(Integer accessaryId) {
        this.accessaryId = accessaryId;
    }

    public String getAccessaryName() {
        return accessaryName;
    }

    public void setAccessaryName(String accessaryName) {
        this.accessaryName = accessaryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @XmlTransient
    public Collection<RecipeAccessary> getRecipeAccessaryCollection() {
        return recipeAccessaryCollection;
    }

    public void setRecipeAccessaryCollection(Collection<RecipeAccessary> recipeAccessaryCollection) {
        this.recipeAccessaryCollection = recipeAccessaryCollection;
    }

    @XmlTransient
    public Collection<AccessSize> getAccessSizeCollection() {
        return accessSizeCollection;
    }

    public void setAccessSizeCollection(Collection<AccessSize> accessSizeCollection) {
        this.accessSizeCollection = accessSizeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessaryId != null ? accessaryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessary)) {
            return false;
        }
        Accessary other = (Accessary) object;
        if ((this.accessaryId == null && other.accessaryId != null) || (this.accessaryId != null && !this.accessaryId.equals(other.accessaryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "danh.db.Accessary[ accessaryId=" + accessaryId + " name " + accessaryName + " ]";
    }

}
