/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCardEntity;
import entity.CustomerEntity;
import entity.DepositAccountEntity;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author choijiwon
 */
@Stateless
public class DepositAccountSessionBean implements DepositAccountSessionBeanRemote, DepositAccountSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystemJpa-ejbPU")
    private EntityManager em;
    
    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    public DepositAccountSessionBean() {
    }
    
    

    @Override
    public DepositAccountEntity createNewDepositAccount(String customerIdentificationNumber, DepositAccountEntity newDepositAccount) throws CustomerNotFoundException{
        
        CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerIdentificationNumber(customerIdentificationNumber);
        newDepositAccount.setCustomerEntity(customerEntity);
        customerEntity.getDepositAccountEntities().add(newDepositAccount);
        System.out.println(customerEntity.getDepositAccountEntities().size());
        
        //AtmCardEntity atmCardEntity = new AtmCardEntity();
        //newDepositAccount.setAtmCardEntity(atmCardEntity);
                
        em.persist(newDepositAccount);
        
        em.merge(customerEntity);
        em.flush();
        
        return newDepositAccount;
    }
    
    @Override
    public List<DepositAccountEntity> retrieveAllDepositAccount(){
        Query query = em.createQuery("SELECT d FROM DepositAccountEntity d");
        return query.getResultList();
        
    }
    
    @Override
    public DepositAccountEntity retrieveDepositAccountByDepositAccountNumber(String accountNumber) throws DepositAccountNotFoundException{
        Query query = em.createQuery("SELECT d FROM DepositAccountEntity d WHERE d.accountNumber = :inAccountNumber");
        query.setParameter("inAccountNumber", accountNumber);
        
        try
        {
            return (DepositAccountEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new DepositAccountNotFoundException("DepositAccount " + accountNumber + " does not exist!");
        }
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
