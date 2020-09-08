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
@Table(name = "recipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r"),
    @NamedQuery(name = "Recipe.findByRecipeId", query = "SELECT r FROM Recipe r WHERE r.recipeId = :recipeId"),
    @NamedQuery(name = "Recipe.findByRecipeName", query = "SELECT r FROM Recipe r WHERE r.recipeName = :recipeName")})
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "recipe_id")
    private Integer recipeId;
    @Basic(optional = false)
    @Column(name = "recipe_name")
    private String recipeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId")
    private Collection<RecipeAccessary> recipeAccessaryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId")
    private Collection<BikeCustom> bikeCustomCollection;

    public Recipe() {
    }

    public Recipe(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Recipe(Integer recipeId, String recipeName) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @XmlTransient
    public Collection<RecipeAccessary> getRecipeAccessaryCollection() {
        return recipeAccessaryCollection;
    }

    public void setRecipeAccessaryCollection(Collection<RecipeAccessary> recipeAccessaryCollection) {
        this.recipeAccessaryCollection = recipeAccessaryCollection;
    }

    @XmlTransient
    public Collection<BikeCustom> getBikeCustomCollection() {
        return bikeCustomCollection;
    }

    public void setBikeCustomCollection(Collection<BikeCustom> bikeCustomCollection) {
        this.bikeCustomCollection = bikeCustomCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recipeId != null ? recipeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.recipeId == null && other.recipeId != null) || (this.recipeId != null && !this.recipeId.equals(other.recipeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "danh.db.Recipe[ recipeId=" + recipeId + " ]";
    }
    
}
