/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailcorebankingsystemjpatellerclient;

import ejb.session.stateless.AtmCardEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import entity.EmployeeEntity;
import exception.AtmCardExistsException;
import exception.AtmCardNotFoundException;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import exception.InvalidLoginCredentialException;
import exception.UncreatedAtmCardException;
import exception.UncreatedDepositAccountException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author choijiwon
 */
public class MainApp {
    
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;
    
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    
    private AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote;
    
    private EmployeeEntity employeeEntity;
    
    private CreateNewCustomerModule createNewCustomerModule;
    
    private ExistingCustomerFunctionModule existingCustomerFunctionModule;

    public MainApp() {
    }

    public MainApp(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote, AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote) {
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.atmCardEntitySessionBeanRemote = atmCardEntitySessionBeanRemote;
    }
    
    public void runApp(){
    Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Retail Core Banking System (RCBS) ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        createNewCustomerModule = new CreateNewCustomerModule(customerEntitySessionBeanRemote);
                        existingCustomerFunctionModule = new ExistingCustomerFunctionModule(depositAccountSessionBeanRemote, customerEntitySessionBeanRemote, atmCardEntitySessionBeanRemote);
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Invalid Login Credentials.\n");
                    }
                } else if(response == 2){
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option, try again.\n");
                }
            }
            if(response == 2)
            {
                break;
            }
        }
    }
    
    private void doLogin() throws InvalidLoginCredentialException{
        
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** RCBS :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0){
             
            employeeEntity = employeeEntitySessionBeanRemote.employeeLogin(username, password);
            
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
        
    }
    
    
     private void menuMain() 
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Retail Core Banking System (RCBS) ***\n");
            System.out.println("You are login as " + employeeEntity.getFirstName() + " " + employeeEntity.getLastName() + " with " + employeeEntity.getEmployeeAccessRightEnum().toString() + " rights\n");
            System.out.println("1: Create new customer");
            System.out.println("2: Existing customer function");
            System.out.println("3: Logout\n");
            response = 0;
            
            
            while(response < 1 || response > 3)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)               
                {
                    createNewCustomerModule.doCreateNewCustomer();
                }
                else if(response == 2){
                    
                    try {
                        //scanner.nextLine();
                        existingCustomerFunctionModule.execute();
                    } catch (CustomerNotFoundException ex) {
                        System.out.println("Customer not found.\n");
                    } catch (DepositAccountNotFoundException ex){
                        System.out.println("Deposit Account not found.\n");
                    } catch(AtmCardNotFoundException ex){
                        System.out.println("Atm Card not found.\n");
                    } catch(AtmCardExistsException ex){
                        System.out.println("Customer already has ATM card.\n");
                    } catch(UncreatedDepositAccountException ex){
                        System.out.println("Create a deposit account first.\n");
                    } catch(UncreatedAtmCardException ex){
                        System.out.println("Create an ATM card first.\n");
                    }
                    
                }
                else if (response == 3)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            if(response == 3)
            {
                break;
            }
        }
        
    }
    
    
}
