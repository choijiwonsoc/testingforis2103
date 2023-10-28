/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author choijiwon
 */
@Entity
public class AtmCardEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmCardId;
    @Column(unique = true)
    private String cardNumber;
    @Column(length = 50)
    private String nameOnCard;
    private Boolean enabled;
    private String pin;
    
    @OneToOne (optional = false)
    @JoinColumn (nullable = false)
    private CustomerEntity customerEntity;
    
    @OneToMany (mappedBy = "atmCardEntity", fetch = FetchType.EAGER)
    private List<DepositAccountEntity> depositAccountEntities;

    public AtmCardEntity() {
        depositAccountEntities = new ArrayList<>();
    }

    public AtmCardEntity(String cardNumber, String nameOnCard, Boolean enabled, String pin) {
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.enabled = enabled;
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    
    

    public Long getAtmCardId() {
        return atmCardId;
    }

    public void setAtmCardId(Long atmCardId) {
        this.atmCardId = atmCardId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atmCardId != null ? atmCardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the atmCardId fields are not set
        if (!(object instanceof AtmCardEntity)) {
            return false;
        }
        AtmCardEntity other = (AtmCardEntity) object;
        if ((this.atmCardId == null && other.atmCardId != null) || (this.atmCardId != null && !this.atmCardId.equals(other.atmCardId))) {
            return false;
        }
        return true;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public List<DepositAccountEntity> getDepositAccountEntities() {
        return depositAccountEntities;
    }

    public void setDepositAccountEntities(List<DepositAccountEntity> depositAccountEntities) {
        this.depositAccountEntities = depositAccountEntities;
    }
    
    

    @Override
    public String toString() {
        return "entity.AtmCardEntity[ id=" + atmCardId + " ]";
    }
    
}
