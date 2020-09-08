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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apple
 */
@Entity
@Table(name = "framesize")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Framesize.findAll", query = "SELECT f FROM Framesize f"),
    @NamedQuery(name = "Framesize.findByFramesizeId", query = "SELECT f FROM Framesize f WHERE f.framesizeId = :framesizeId"),
    @NamedQuery(name = "Framesize.findByFramesizeNumber", query = "SELECT f FROM Framesize f WHERE f.framesizeNumber = :framesizeNumber")})
public class Framesize implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "framesize_id", columnDefinition = "Decimal(1,1)")
    private Integer framesizeId;
    @Column(name = "framesize_number")
    private Integer framesizeNumber;

    public Framesize() {
    }

    public Framesize(Integer framesizeNumber) {
        this.framesizeNumber = framesizeNumber;
    }

    public Integer getFramesizeId() {
        return framesizeId;
    }

    public void setFramesizeId(Integer framesizeId) {
        this.framesizeId = framesizeId;
    }

    public Integer getFramesizeNumber() {
        return framesizeNumber;
    }

    public void setFramesizeNumber(Integer framesizeNumber) {
        this.framesizeNumber = framesizeNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (framesizeId != null ? framesizeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Framesize)) {
            return false;
        }
        Framesize other = (Framesize) object;
        if ((this.framesizeId == null && other.framesizeId != null) || (this.framesizeId != null && !this.framesizeId.equals(other.framesizeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "danh.db.Framesize[ framesizeId=" + framesizeId + " framesizeNumber=" + framesizeNumber + "   ]";
    }
    
}
