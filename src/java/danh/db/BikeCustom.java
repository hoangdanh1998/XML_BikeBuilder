/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apple
 */
@Entity
@Table(name = "bike_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikeCustom.findAll", query = "SELECT b FROM BikeCustom b"),
    @NamedQuery(name = "BikeCustom.findById", query = "SELECT b FROM BikeCustom b WHERE b.id = :id"),
    @NamedQuery(name = "BikeCustom.findByCustomName", query = "SELECT b FROM BikeCustom b WHERE b.customName = :customName")})
public class BikeCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "Decimal(1,1)")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "custom_name")
    private String customName;
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    @ManyToOne(optional = false)
    private Recipe recipeId;

    public BikeCustom() {
    }

    public BikeCustom(Integer id) {
        this.id = id;
    }

    public BikeCustom(Integer id, String customName) {
        this.id = id;
        this.customName = customName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Recipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Recipe recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikeCustom)) {
            return false;
        }
        BikeCustom other = (BikeCustom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "danh.db.BikeCustom[ id=" + id + " ]";
    }
    
}
