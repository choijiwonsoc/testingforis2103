/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import exception.CustomerNotFoundException;
import java.util.List;
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
public class CustomerEntitySessionBean implements CustomerEntitySessionBeanRemote, CustomerEntitySessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystemJpa-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCustomer(CustomerEntity newCustomer) throws CustomerNotFoundException{
        
        if(!(newCustomer == null)){
        
        em.persist(newCustomer);
        em.flush();
        
        return newCustomer.getCustomerId();
        }else{
            throw new CustomerNotFoundException("Customer not found.");
        }
        
    }
    
    @Override
    public List<CustomerEntity> retrieveAllCustomer(){
        Query query = em.createQuery("SELECT c FROM CustomerEntity c");
        return query.getResultList();
        
    }
    
    @Override
    public CustomerEntity retrieveCustomerByCustomerId(Long id) throws CustomerNotFoundException{
        
        CustomerEntity customerEntity = em.find(CustomerEntity.class, id);
        
        if(customerEntity != null){
            return customerEntity;
        }else{
            throw new CustomerNotFoundException("Customer " + id + " does not exist!");
        }
        
    }
    
    @Override
    public CustomerEntity retrieveCustomerByCustomerIdentificationNumber(String number) throws CustomerNotFoundException{
        
        Query query = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.identificationNumber = :inIdentificationNumber");
        query.setParameter("inIdentificationNumber", number);
        
        try
        {
            return (CustomerEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer " + number + " does not exist!");
        }
        
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
