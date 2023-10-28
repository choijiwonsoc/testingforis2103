/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccountTransactionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author choijiwon
 */
@Stateless
public class DepositAccountTransactionEntitySessionBean implements DepositAccountTransactionEntitySessionBeanRemote, DepositAccountTransactionEntitySessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystemJpa-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewDepositAccountTransaction(DepositAccountTransactionEntity newDepositAccountTransaction){
        
        em.persist(newDepositAccountTransaction);
        em.flush();
        
        return newDepositAccountTransaction.getDepositAccountTransactionEntityId();
    }
    
    @Override
    public List<DepositAccountTransactionEntity> retrieveAllDepositAccountTransaction(){
        Query query = em.createQuery("SELECT d FROM DEPOSITACCOUNTTRANSACTIONENTITY d");
        return query.getResultList();
        
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
