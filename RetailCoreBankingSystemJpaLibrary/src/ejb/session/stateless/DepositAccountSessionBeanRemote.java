/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccountEntity;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author choijiwon
 */
@Remote
public interface DepositAccountSessionBeanRemote {
    
 //   public Long createNewDepositAccount(DepositAccountEntity newDepositAccount);
    
    public List<DepositAccountEntity> retrieveAllDepositAccount();
    
     public DepositAccountEntity createNewDepositAccount(String customerIdentificationNumber, DepositAccountEntity newDepositAccount) throws CustomerNotFoundException;
     
     public DepositAccountEntity retrieveDepositAccountByDepositAccountNumber(String accountNumber) throws DepositAccountNotFoundException;
}
