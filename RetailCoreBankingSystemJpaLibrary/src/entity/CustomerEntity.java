/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author choijiwon
 */
@Entity
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(unique = true)
    private String identificationNumber;
    private String contactNumber;
    @Column(length = 100, nullable = false)
    private String addressLine1;
    @Column(length = 100)
    private String addressLine2;
    private String postalCode;
    
    @OneToMany (mappedBy = "customerEntity", fetch = FetchType.EAGER)
    private List<DepositAccountEntity> depositAccountEntities;
    
    @OneToOne (mappedBy = "customerEntity", fetch = FetchType.EAGER)
    private AtmCardEntity atmCardEntities;


    public CustomerEntity() {
        depositAccountEntities = new ArrayList<>();
        atmCardEntities = null;
    }

    public CustomerEntity(String firstName, String lastName, String identificationNumber, String contactNumber, String addressLine1, String addressLine2, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.contactNumber = contactNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
    }
    
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof CustomerEntity)) {
            return false;
        }
        CustomerEntity other = (CustomerEntity) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    public List<DepositAccountEntity> getDepositAccountEntities() {
        return depositAccountEntities;
    }

    public void setDepositAccountEntities(List<DepositAccountEntity> depositAccountEntities) {
        this.depositAccountEntities = depositAccountEntities;
    }

    public AtmCardEntity getAtmCardEntities() {
        return atmCardEntities;
    }

    public void setAtmCardEntities(AtmCardEntity atmCardEntities) {
        this.atmCardEntities = atmCardEntities;
    }
    
    @Override
    public String toString() {
        return "entity.CustomerEntity[ id=" + customerId + " ]";
    }
}
