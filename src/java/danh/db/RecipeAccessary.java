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
@Table(name = "Recipe_Accessary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecipeAccessary.findAll", query = "SELECT r FROM RecipeAccessary r"),
    @NamedQuery(name = "RecipeAccessary.findById", query = "SELECT r FROM RecipeAccessary r WHERE r.id = :id")})
public class RecipeAccessary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "Decimal(1,1)")
    private Integer id;
    @JoinColumn(name = "Accessary_Id", referencedColumnName = "accessary_id")
    @ManyToOne(optional = false)
    private Accessary accessaryId;
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    @ManyToOne(optional = false)
    private Recipe recipeId;

    public RecipeAccessary() {
    }

    public RecipeAccessary(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Accessary getAccessaryId() {
        return accessaryId;
    }

    public void setAccessaryId(Accessary accessaryId) {
        this.accessaryId = accessaryId;
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
        if (!(object instanceof RecipeAccessary)) {
            return false;
        }
        RecipeAccessary other = (RecipeAccessary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "danh.db.RecipeAccessary[ id=" + id + " ]";
    }
    
}
