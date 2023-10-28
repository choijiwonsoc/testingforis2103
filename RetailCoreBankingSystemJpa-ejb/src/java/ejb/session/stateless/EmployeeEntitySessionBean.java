/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;


import entity.EmployeeEntity;
import exception.EmployeeNotFoundException;
import exception.InvalidLoginCredentialException;

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
public class EmployeeEntitySessionBean implements EmployeeEntitySessionBeanRemote, EmployeeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystemJpa-ejbPU")
    private EntityManager em;


    @Override
    public Long createNewEmployee(EmployeeEntity newEmployee){
        
        em.persist(newEmployee);
        em.flush();
        
        return newEmployee.getEmployeeId();
    }
    
    @Override
    public List<EmployeeEntity> retrieveAllEmployee(){
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e");
        return query.getResultList();
        
    }
    
    @Override
    public EmployeeEntity retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException{
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.userName = :inUserName");
        query.setParameter("inUserName", username);
        
        try
        {
            return (EmployeeEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee " + username + " does not exist!");
        }
    }
    
    @Override
    public EmployeeEntity employeeLogin (String username, String password) throws InvalidLoginCredentialException{
    
        try
        {
            EmployeeEntity employeeEntity = retrieveEmployeeByUsername(username);
            
            if(employeeEntity.getPassword().equals(password))
            {
                //staffEntity.getSaleTransactionEntities().size();                
                return employeeEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
