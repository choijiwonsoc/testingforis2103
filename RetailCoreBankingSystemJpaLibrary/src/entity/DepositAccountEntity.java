/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import enumeration.DepositAccountTypeEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author choijiwon
 */
@Entity
public class DepositAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;
    @Column(unique = true)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private DepositAccountTypeEnum depositAccountType;
    @Column(precision = 11, scale = 2)
    private BigDecimal availableBalance;
    @Column(precision = 11, scale = 2)
    private BigDecimal holdingBalance;
    @Column(precision = 11, scale = 2)
    private BigDecimal ledgerBalance;
    private Boolean enabled;
    
    @ManyToOne (optional = false)
    @JoinColumn (nullable = true)
    private CustomerEntity customerEntity;
    
    @ManyToOne (optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn (nullable = true)
    private AtmCardEntity atmCardEntity;
    
    @OneToMany (mappedBy = "depositAccountEntity")
    private List<DepositAccountTransactionEntity> depositAccountTransactionEntities;

    public DepositAccountEntity() {
        depositAccountTransactionEntities = new ArrayList<>();
    }

    public DepositAccountEntity(String accountNumber, DepositAccountTypeEnum depositAccountType, BigDecimal availableBalance, BigDecimal holdingBalance, BigDecimal ledgerBalance, Boolean enabled) {
        this.accountNumber = accountNumber;
        this.depositAccountType = depositAccountType;
        this.availableBalance = availableBalance;
        this.holdingBalance = holdingBalance;
        this.ledgerBalance = ledgerBalance;
        this.enabled = enabled;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DepositAccountTypeEnum getDepositAccountType() {
        return depositAccountType;
    }

    public void setDepositAccountType(DepositAccountTypeEnum depositAccountType) {
        this.depositAccountType = depositAccountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldingBalance() {
        return holdingBalance;
    }

    public void setHoldingBalance(BigDecimal holdingBalance) {
        this.holdingBalance = holdingBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    
    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountId != null ? depositAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountId fields are not set
        if (!(object instanceof DepositAccountEntity)) {
            return false;
        }
        DepositAccountEntity other = (DepositAccountEntity) object;
        if ((this.depositAccountId == null && other.depositAccountId != null) || (this.depositAccountId != null && !this.depositAccountId.equals(other.depositAccountId))) {
            return false;
        }
        return true;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        if(this.customerEntity != null)
        {
            this.customerEntity.getDepositAccountEntities().remove(this);
        }
        
        this.customerEntity = customerEntity;
        
        if(this.customerEntity != null)
        {
            if(!this.customerEntity.getDepositAccountEntities().contains(this))
            {
                this.customerEntity.getDepositAccountEntities().add(this);
            }
        }
    }

    public AtmCardEntity getAtmCardEntity() {
        return atmCardEntity;
    }

    public void setAtmCardEntity(AtmCardEntity atmCardEntity) {
      //  if(this.atmCardEntity != null)
    //    {
     //       this.atmCardEntity.getDepositAccountEntities().remove(this);
     //   }
        
        this.atmCardEntity = atmCardEntity;
       /** this.atmCardEntity = atmCardEntity; **/
        
        if(this.atmCardEntity != null)
        {
            if(!this.atmCardEntity.getDepositAccountEntities().contains(this))
            {
                this.atmCardEntity.getDepositAccountEntities().add(this);
            }
        }
       
    }

    public List<DepositAccountTransactionEntity> getDepositAccountTransactionEntities() {
        return depositAccountTransactionEntities;
    }

    public void setDepositAccountTransactionEntities(List<DepositAccountTransactionEntity> depositAccountTransactionEntities) {
        this.depositAccountTransactionEntities = depositAccountTransactionEntities;
    }

    
    
    @Override
    public String toString() {
        return "entity.DepositAccount[ id=" + depositAccountId + " ]";
    }
}
