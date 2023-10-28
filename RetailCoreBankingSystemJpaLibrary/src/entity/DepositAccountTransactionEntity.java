/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enumeration.TransactionStatusEnum;
import enumeration.TransactionTypeEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author choijiwon
 */
@Entity
public class DepositAccountTransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long depositAccountTransactionEntityId;
    private Date transactionTimeDate;
    private TransactionTypeEnum type;
    private String code;
    private String reference;
    private BigDecimal amount;
    private TransactionStatusEnum status;
    
    @ManyToOne (optional = false)
    @JoinColumn (nullable = false)
    private DepositAccountEntity depositAccountEntity;

    public DepositAccountTransactionEntity() {
    }

    public DepositAccountTransactionEntity(Date transactionTimeDate, TransactionTypeEnum type, String code, String reference, BigDecimal amount, TransactionStatusEnum status) {
        this.transactionTimeDate = transactionTimeDate;
        this.type = type;
        this.code = code;
        this.reference = reference;
        this.amount = amount;
        this.status = status;
    }

    public Date getTransactionTimeDate() {
        return transactionTimeDate;
    }

    public void setTransactionTimeDate(Date transactionTimeDate) {
        this.transactionTimeDate = transactionTimeDate;
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    public void setType(TransactionTypeEnum type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }
    
    

    public Long getDepositAccountTransactionEntityId() {
        return depositAccountTransactionEntityId;
    }

    public void setDepositAccountTransactionEntityId(Long depositAccountTransactionEntityId) {
        this.depositAccountTransactionEntityId = depositAccountTransactionEntityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountTransactionEntityId != null ? depositAccountTransactionEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountTransactionEntityId fields are not set
        if (!(object instanceof DepositAccountTransactionEntity)) {
            return false;
        }
        DepositAccountTransactionEntity other = (DepositAccountTransactionEntity) object;
        if ((this.depositAccountTransactionEntityId == null && other.depositAccountTransactionEntityId != null) || (this.depositAccountTransactionEntityId != null && !this.depositAccountTransactionEntityId.equals(other.depositAccountTransactionEntityId))) {
            return false;
        }
        return true;
    }

    public DepositAccountEntity getDepositAccountEntity() {
        return depositAccountEntity;
    }

    public void setDepositAccountEntity(DepositAccountEntity depositAccountEntity) {
        this.depositAccountEntity = depositAccountEntity;
    }

    
    
    @Override
    public String toString() {
        return "entity.DepositAccountTransactionEntity[ id=" + depositAccountTransactionEntityId + " ]";
    }
    
}
