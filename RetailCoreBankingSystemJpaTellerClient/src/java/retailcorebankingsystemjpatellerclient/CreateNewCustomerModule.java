/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailcorebankingsystemjpatellerclient;

import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import entity.CustomerEntity;
import exception.CustomerNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author choijiwon
 */
public class CreateNewCustomerModule {
    
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;

    public CreateNewCustomerModule() {
    }

    public CreateNewCustomerModule(CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote) {
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
    }
    
    
    public void doCreateNewCustomer(){
        
        Scanner scanner = new Scanner(System.in);
        CustomerEntity newCustomerEntity = new CustomerEntity();
        System.out.println("*** RCBS :: Create new customer ***\n");
        
        System.out.print("Enter first name> ");
        newCustomerEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter last name> ");
        newCustomerEntity.setLastName(scanner.nextLine().trim());
        while(true)
        {
            System.out.print("Enter identification number> ");
            String id = scanner.nextLine().trim();
            
            if(id.length() == 8)
            {
                newCustomerEntity.setIdentificationNumber(id);
                break;
            }
            else
            {
                System.out.println("Invalid length, please try again!\n");
            }
        }
        //newCustomerEntity.setIdentificationNumber(scanner.nextLine().trim());
        while(true)
        {
            System.out.print("Enter contact number> ");
            String contact = scanner.nextLine().trim();
            
            if(contact.length() == 8)
            {
                newCustomerEntity.setContactNumber(contact);
                break;
            }
            else
            {
                System.out.println("Invalid length, please try again!\n");
            }
        }
        //newCustomerEntity.setContactNumber(scanner.nextLine().trim());
        System.out.print("Enter address line 1> ");
        newCustomerEntity.setAddressLine1(scanner.nextLine().trim());
        System.out.print("Enter address line 2> ");
        newCustomerEntity.setAddressLine2(scanner.nextLine().trim());
        while(true)
        {
            System.out.print("Enter postal code> ");
            String postalCode = scanner.nextLine().trim();
            
            if(postalCode.length() == 6)
            {
                newCustomerEntity.setPostalCode(postalCode);
                break;
            }
            else
            {
                System.out.println("Invalid length, please try again!\n");
            }
        }
      //  newCustomerEntity.setPostalCode(scanner.nextLine().trim());
        
        try {
            Long newCustomerEntityId = customerEntitySessionBeanRemote.createNewCustomer(newCustomerEntity);
            System.out.println("Customer successfully created with ID: " + newCustomerEntityId);
        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer not found.");
        }
        
    }
    
}
