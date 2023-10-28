/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailcorebankingsystemjpaautomatedclient;

import ejb.session.stateless.AtmCardEntitySessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCardEntity;
import entity.DepositAccountEntity;
import exception.AtmCardNotFoundException;
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
public class InsertAtmCardModule {
    
    private AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote;
    
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;


    public InsertAtmCardModule() {
    }

    public InsertAtmCardModule(AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote) {
        this.atmCardEntitySessionBeanRemote = atmCardEntitySessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
    }
    
    public void changeAtmPin(String atmCardNumber) throws AtmCardNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new PIN> ");
        String atmCardPin = scanner.nextLine().trim();
        AtmCardEntity atmCardEntity = atmCardEntitySessionBeanRemote.retrieveAtmCardEntityByAtmCardNumber(atmCardNumber);
        atmCardEntitySessionBeanRemote.changeAtmPin(atmCardEntity.getCardNumber(), atmCardPin);
        System.out.println("ATM PIN successfully changed.\n");
    }
    
    public void enquireAvailBalance(String atmCardNumber) throws AtmCardNotFoundException{
        Scanner scanner = new Scanner(System.in);
        AtmCardEntity atmCardEntity = atmCardEntitySessionBeanRemote.retrieveAtmCardEntityByAtmCardNumber(atmCardNumber);
//        System.out.println(atmCardEntity.getDepositAccountEntities().get(0).getAccountNumber());
        while(true){
            for(int i = 0; i < atmCardEntity.getDepositAccountEntities().size(); i++){
                System.out.println("Deposit Account No: " + atmCardEntity.getDepositAccountEntities().get(i).getAccountNumber());
            }
            System.out.print("Enter deposit account number you want to enquire, 'Q' to exit> ");
            String accountNumber = scanner.nextLine().trim();

            if (accountNumber.equals("Q")) {
                break;  // User wants to quit.
            }

            DepositAccountEntity depositAccountEntity;
            try {
                depositAccountEntity = depositAccountSessionBeanRemote.retrieveDepositAccountByDepositAccountNumber(accountNumber);
               // System.out.println(depositAccountEntity.getAccountNumber());
               // System.out.println(atmCardEntity.getDepositAccountEntities().size());
                boolean found = false;

                for(int i = 0; i < atmCardEntity.getDepositAccountEntities().size(); i++){
                    if(atmCardEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(accountNumber)){
                    found = true;
                    System.out.println("Available balance is " + depositAccountEntity.getAvailableBalance());
                    break;
                    } 
                }
                if(found == false){
                    System.out.println("No such deposit account associated with this card.");
                }
            } catch (DepositAccountNotFoundException ex) {
                System.out.println("Deposit Account not found.");
            }
        }
    }
    
}
