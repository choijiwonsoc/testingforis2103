/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailcorebankingsystemjpaautomatedclient;

import ejb.session.stateless.AtmCardEntitySessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCardEntity;
import exception.AtmCardNotFoundException;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import exception.WrongPinException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author choijiwon
 */
public class MainApp {
    
    private AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote;
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    private InsertAtmCardModule insertAtmCardModule;
    private AtmCardEntity atmCardEntity;

    public MainApp() {
    }

    public MainApp(AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote) {
        this.atmCardEntitySessionBeanRemote = atmCardEntitySessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
    }
    
    public void runApp(){
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Retail Core Banking System (RCBS) ***\n");
            System.out.println("1: Insert ATM Card");
            System.out.println("2: Exit\n");
            response = 0;
            String cardNo = "";
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    try {
                        cardNo = insertCard();
                        AtmCardEntity selected = atmCardEntitySessionBeanRemote.retrieveAtmCardEntityByAtmCardNumber(cardNo);
                        String cust = selected.getCustomerEntity().getFirstName().concat(" ").concat(selected.getCustomerEntity().getLastName());
                        System.out.println("Currently serving ATM card from " + cust + "\n");
                        insertAtmCardModule = new InsertAtmCardModule(atmCardEntitySessionBeanRemote, depositAccountSessionBeanRemote);
                        execute(cardNo);
                    } catch (WrongPinException ex) {
                        System.out.println("Wrong PIN entered.\n");
                    } catch (AtmCardNotFoundException ex) {
                        System.out.println("ATM card not found.\n");
                    }
                    
                } else if(response == 2){
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option, try again.\n");
                }
            } if(response == 2)
            {
                break;
            }
        }
    }
    
    public String insertCard() throws WrongPinException, AtmCardNotFoundException{
        Scanner scanner = new Scanner(System.in);
        AtmCardEntity atmCardEntity;
        Integer response = 0;
        
        System.out.print("Enter ATM card number> ");
        String cardNumber = scanner.nextLine().trim();
            
        atmCardEntity = atmCardEntitySessionBeanRemote.retrieveAtmCardEntityByAtmCardNumber(cardNumber);
            
        System.out.print("Enter ATM card PIN> ");
        String atmPin = scanner.nextLine().trim();
        
        if(!atmCardEntity.getPin().equals(atmPin)){
            throw new WrongPinException("Wrong PIN!\n");
    }
        //System.out.println("ATM Card PIN successfully updated.");
        return atmCardEntity.getCardNumber();
    } 
    
    public void execute(String cardNumber){
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Retail Core Banking System (RCBS) ***\n");
//            System.out.println("Current ATM card is " + atmCardEntity.getCardNumber() + "\n");
            System.out.println("1: Change ATM card PIN");
            System.out.println("2: Enquire Available Balance for associated deposit account");
            System.out.println("3: Back\n");
            response = 0;
            
            while(response < 1 || response > 3)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)               
                {
                    try {
                        insertAtmCardModule.changeAtmPin(cardNumber);
                    } catch (AtmCardNotFoundException ex) {
                        System.out.println("ATM Card not found.\n");
                    }
                }
                else if(response == 2){
                    try {
                        insertAtmCardModule.enquireAvailBalance(cardNumber);
                    } catch (AtmCardNotFoundException ex) {
                        System.out.println("ATM Card not found.\n");
             //       } catch (DepositAccountNotFoundException ex) {
              //          System.out.println("Deposit Account not found.\n");
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
