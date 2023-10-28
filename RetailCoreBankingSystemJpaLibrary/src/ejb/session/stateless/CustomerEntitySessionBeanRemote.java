/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import exception.CustomerNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author choijiwon
 */
@Remote
public interface CustomerEntitySessionBeanRemote {
    
    public Long createNewCustomer(CustomerEntity newCustomer) throws CustomerNotFoundException;
    
    public List<CustomerEntity> retrieveAllCustomer();
    
    public CustomerEntity retrieveCustomerByCustomerId(Long id) throws CustomerNotFoundException;
    
    public CustomerEntity retrieveCustomerByCustomerIdentificationNumber(String number) throws CustomerNotFoundException;
    
    
}
