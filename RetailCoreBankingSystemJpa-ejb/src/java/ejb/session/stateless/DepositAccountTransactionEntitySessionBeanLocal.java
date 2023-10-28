/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccountTransactionEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author choijiwon
 */
@Local
public interface DepositAccountTransactionEntitySessionBeanLocal {

    public Long createNewDepositAccountTransaction(DepositAccountTransactionEntity newDepositAccountTransaction);

    public List<DepositAccountTransactionEntity> retrieveAllDepositAccountTransaction();
    
}
