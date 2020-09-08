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
@Table(name = "access_size")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessSize.findAll", query = "SELECT a FROM AccessSize a"),
    @NamedQuery(name = "AccessSize.findById", query = "SELECT a FROM AccessSize a WHERE a.id = :id")})

public class AccessSize implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "Decimal(1,1)")
    private Integer id;
    @JoinColumn(name = "accessary_id", referencedColumnName = "accessary_id")
    @ManyToOne
    private Accessary accessaryId;
    @JoinColumn(name = "framesize_id", referencedColumnName = "framesize_id")
    @ManyToOne
    private Framesize framesizeId;

    public AccessSize() {
    }

    public AccessSize(Integer id) {
        this.id = id;
    }

    public AccessSize(Accessary accessary, Framesize framesize) {
        this.accessaryId = accessary;
        this.framesizeId = framesize;
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

    public Framesize getFramesizeId() {
        return framesizeId;
    }

    public void setFramesizeId(Framesize framesizeId) {
        this.framesizeId = framesizeId;
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
        if (!(object instanceof AccessSize)) {
            return false;
        }
        AccessSize other = (AccessSize) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "danh.db.AccessSize[ id=" + id + " Access " + accessaryId + " Frame " + framesizeId+ " ]";
    }
    
}
