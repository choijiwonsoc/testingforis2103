/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCardEntity;
import entity.CustomerEntity;
import entity.DepositAccountEntity;
import exception.AtmCardNotFoundException;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AtmCardEntitySessionBean implements AtmCardEntitySessionBeanRemote, AtmCardEntitySessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystemJpa-ejbPU")
    private EntityManager em;

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    
    @EJB
    private DepositAccountSessionBeanLocal depositAccountSessionBeanLocal;

    public AtmCardEntitySessionBean() {
    }
    
    
    
    @Override
    public AtmCardEntity createNewAtmCard(String customerIdNumber, DepositAccountEntity depositAccountEntity, AtmCardEntity newAtmCard) throws CustomerNotFoundException, DepositAccountNotFoundException{
        
        CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerIdentificationNumber(customerIdNumber);
        newAtmCard.setCustomerEntity(customerEntity);
        customerEntity.setAtmCardEntities(newAtmCard);
        
       // DepositAccountEntity depositAccountEntity = depositAccountSessionBeanLocal.retrieveDepositAccountByDepositAccountNumber(depositAccountNumber);
       newAtmCard.getDepositAccountEntities().add(depositAccountEntity); 
       depositAccountEntity.setAtmCardEntity(newAtmCard);
       /**for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
           if(customerEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(depositAccountEntity.getAccountNumber())){
               customerEntity.getDepositAccountEntities().set(i, depositAccountEntity);
           }
       }
       * **/
        
        em.persist(newAtmCard);
        em.merge(depositAccountEntity);
        em.merge(customerEntity);
        
        em.flush();
        
        return newAtmCard;
    }
    
    @Override
    public void associateAtmCard(DepositAccountEntity depositAccountEntity, AtmCardEntity atmCardEntity){
        
        depositAccountEntity.setAtmCardEntity(atmCardEntity);
        atmCardEntity.getDepositAccountEntities().add(depositAccountEntity);
        
        em.merge(atmCardEntity);
        em.merge(depositAccountEntity);
        
        em.flush();
    }
    
    @Override
    public List<AtmCardEntity> retrieveAllAtmCard(){
        Query query = em.createQuery("SELECT a FROM AtmCardEntity a");
        return query.getResultList();
        
    }
    
    @Override
    public AtmCardEntity retrieveAtmCardEntityByAtmCardNumber(String cardNumber) throws AtmCardNotFoundException{
        Query query = em.createQuery("SELECT a FROM AtmCardEntity a WHERE a.cardNumber = :inCardNumber");
        query.setParameter("inCardNumber", cardNumber);
        
        try
        {
            return (AtmCardEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new AtmCardNotFoundException("Atm Card " + cardNumber + " does not exist!");
        }
    }
    
    @Override
    public void changeAtmPin(String atmCardNumber, String atmPin) throws AtmCardNotFoundException{
        AtmCardEntity atmCardEntity = retrieveAtmCardEntityByAtmCardNumber(atmCardNumber);
        atmCardEntity.setPin(atmPin);
        em.merge(atmCardEntity);
        em.flush();
    }
    
    @Override
    public void deleteAtmCardEntity(String customerIdNumber, String atmCardNumber) throws AtmCardNotFoundException, CustomerNotFoundException{
        AtmCardEntity atmCardEntity = retrieveAtmCardEntityByAtmCardNumber(atmCardNumber);
        
        
        
        CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerIdentificationNumber(customerIdNumber);
        customerEntity.setAtmCardEntities(null);
        
        em.merge(customerEntity);
        em.flush();
        
        for(int i = 0; i < atmCardEntity.getDepositAccountEntities().size(); i++){
            DepositAccountEntity depositAccountEntity = atmCardEntity.getDepositAccountEntities().get(i);
            depositAccountEntity.setAtmCardEntity(null);
            
            em.merge(depositAccountEntity);
            em.flush();
        }
        
        em.remove(atmCardEntity);
        em.flush();
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
