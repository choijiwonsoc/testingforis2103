/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailcorebankingsystemjpatellerclient;


import ejb.session.stateless.AtmCardEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCardEntity;
import entity.CustomerEntity;
import entity.DepositAccountEntity;
import enumeration.DepositAccountTypeEnum;
import exception.AtmCardExistsException;
import exception.AtmCardNotFoundException;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import exception.UncreatedAtmCardException;
import exception.UncreatedDepositAccountException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author choijiwon
 */
public class ExistingCustomerFunctionModule {
    
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    
    private AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote;

    public ExistingCustomerFunctionModule() {
    }

    public ExistingCustomerFunctionModule(DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote, AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote) {
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.atmCardEntitySessionBeanRemote = atmCardEntitySessionBeanRemote;
    }
    
    
    
    public void execute() throws CustomerNotFoundException, DepositAccountNotFoundException, AtmCardNotFoundException, AtmCardExistsException, UncreatedDepositAccountException, UncreatedAtmCardException{
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
   //     while(true)
   //     {
            System.out.println("*** RCBS :: Existing customer function ***\n");
            System.out.println("Enter customer identification number> ");
            CustomerEntity customerEntity;
            String custId = scanner.nextLine().trim();
            
            customerEntity = customerEntitySessionBeanRemote.retrieveCustomerByCustomerIdentificationNumber(custId);
            
            System.out.println("Serving customer " + customerEntity.getCustomerId() + ", " + customerEntity.getFirstName() + " " + customerEntity.getLastName() +"\n");
            
            System.out.println("1: Open Deposit Account");
            System.out.println("2: Issue ATM Card");
            System.out.println("3: Issue Replacement ATM Card");
            System.out.println("4: Back\n");
            response = 0;
            
            while(response < 1 || response > 7)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    openDepositAccount(custId);
                }
                else if(response == 2)
                {
                    issueAtmCard(custId);
                }
                else if(response == 3)
                {
                    issueReplacementAtmCard(custId);
                }
                else if (response == 4)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
        
    }
    
    public void openDepositAccount(String customerIdentificationNumber) throws CustomerNotFoundException{
        Scanner scanner = new Scanner(System.in);
        DepositAccountEntity depositAccountEntity = new DepositAccountEntity();
       // try {
            CustomerEntity customerEntity = customerEntitySessionBeanRemote.retrieveCustomerByCustomerIdentificationNumber(customerIdentificationNumber);
     //   } catch (CustomerNotFoundException ex) {
      //      System.out.println("Customer not found.\n");
      //  }
        
        System.out.println("Opening new Deposit Account.\n");
        
        while(true)
        {
            System.out.print("Enter deposit account type, 1 for SAVINGS and 2 for CURRENT> ");
            Integer depositAccountTypeInt = scanner.nextInt();
            
            if(depositAccountTypeInt == 1)
            {
                depositAccountEntity.setDepositAccountType(DepositAccountTypeEnum.values()[depositAccountTypeInt-1]);
                break;
            }
            else
            {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        //Integer accessRightInt = scanner.nextInt();
        //depositAccountEntity.setDepositAccountType(DepositAccountTypeEnum.values()[accessRightInt-1]);
        while(true)
        {
            System.out.print("Enter deposit amount> ");
            BigDecimal depositAmount = scanner.nextBigDecimal();
            if(depositAmount.compareTo(BigDecimal.ZERO) >=0){
                depositAccountEntity.setLedgerBalance(depositAmount);
                break;
            } else {
                System.out.println("Enter an amount => 0.\n");
            }
        }
        while(true)
        {
            System.out.print("Enter holding balance> ");
            BigDecimal holdingAmount = scanner.nextBigDecimal();
            if(holdingAmount.compareTo(BigDecimal.ZERO) >=0 && holdingAmount.compareTo(depositAccountEntity.getLedgerBalance()) <= 0){
                depositAccountEntity.setHoldingBalance(holdingAmount);
                break;
            } else {
                if(holdingAmount.compareTo(depositAccountEntity.getLedgerBalance()) > 0){
                    System.out.println("Enter an amount <= deposit amount.\n");
                }else{
                    System.out.println("Enter an amount => 0.\n");
                }
            }
        }
        
        depositAccountEntity.setAvailableBalance(depositAccountEntity.getLedgerBalance().subtract(depositAccountEntity.getHoldingBalance()));
        depositAccountEntity.setEnabled(Boolean.TRUE);

        String randomAccountNumber = generateRandomString(10);
        depositAccountEntity.setAccountNumber(randomAccountNumber);
        
        depositAccountEntity = depositAccountSessionBeanRemote.createNewDepositAccount(customerIdentificationNumber, depositAccountEntity);
        String depositAccountEntityId = depositAccountEntity.getAccountNumber();
        System.out.println("Deposit Account successfully created with ID: " + depositAccountEntityId + "\n");
       // CustomerEntity newCustomerEntity = customerEntitySessionBeanRemote.retrieveCustomerByCustomerIdentificationNumber(customerIdentificationNumber);
      //  System.out.println(newCustomerEntity.getDepositAccountEntities().size());
    }
    
    public void issueAtmCard(String customerIdentificationNumber) throws CustomerNotFoundException, DepositAccountNotFoundException, AtmCardExistsException, UncreatedDepositAccountException{
        Scanner scanner = new Scanner(System.in);
        AtmCardEntity atmCardEntity = new AtmCardEntity();
        String depositAccountNo = "";
        int completed = 0;
        String moreDepositAccount = "";
        
        CustomerEntity customerEntity = customerEntitySessionBeanRemote.retrieveCustomerByCustomerIdentificationNumber(customerIdentificationNumber);
        if(customerEntity.getAtmCardEntities() == null && !customerEntity.getDepositAccountEntities().isEmpty()){
            //customerEntity = customerEntity;
            System.out.println("Creating new ATM Card.\n");
            
            for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                System.out.println("Deposit Account No: " + customerEntity.getDepositAccountEntities().get(i).getAccountNumber());
            }
            
            while(true){
                System.out.print("Enter deposit account number> ");
                depositAccountNo = scanner.nextLine().trim();
                for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                    if(customerEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(depositAccountNo)){
                        System.out.println("Deposit Account " + depositAccountNo + " selected\n");
                        completed++;
                        break;
                    }
                }
                if(completed == 0){
                    System.out.println("Deposit Account not found for customer.\n");
                }else{
                    break;
                }
                
            }
            DepositAccountEntity depositAccountEntity = depositAccountSessionBeanRemote.retrieveDepositAccountByDepositAccountNumber(depositAccountNo);
            System.out.print("Enter name on card> ");
            atmCardEntity.setNameOnCard(scanner.nextLine().trim());
            while(true)
            {
                System.out.print("Enter pin> ");
                String newPin = scanner.nextLine().trim();
                if(newPin.length() == 6){
                    atmCardEntity.setPin(newPin);
                    break;
                } else {
                    System.out.println("Length of PIN must be 6 chars.\n");
                }
            }   
            atmCardEntity.setEnabled(Boolean.TRUE);
            String randomAccountNumber = generateRandomString(10);
            atmCardEntity.setCardNumber(randomAccountNumber);
            
            atmCardEntity = atmCardEntitySessionBeanRemote.createNewAtmCard(customerIdentificationNumber, depositAccountEntity, atmCardEntity);
            String atmCardEntityId = atmCardEntity.getCardNumber();
            System.out.println("ATM card successfully created with number: " + atmCardEntityId + "\n");
            
           // CustomerEntity newCustomerEntity = customerEntitySessionBeanRemote.retrieveCustomerByCustomerIdentificationNumber(customerIdentificationNumber);
          //  System.out.println(newCustomerEntity.getAtmCardEntities().size());
            if (customerEntity.getDepositAccountEntities().size() > 1){
                System.out.print("Associate with more deposit accounts? Enter 'N' if no and any key if yes.> ");
                moreDepositAccount = scanner.nextLine().trim();
                //int counter = 0;
                String associateAccount = "";

              //  String depositAccountAssoc = "";
                while(true){
                    if(moreDepositAccount.equals("N")){
                        break;
                    }
                    for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                        for(int j = 0; j < atmCardEntity.getDepositAccountEntities().size(); j++){
                            if(!customerEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(atmCardEntity.getDepositAccountEntities().get(j).getAccountNumber())){
                            //counter++;
                            System.out.println("Deposit Account No: " + customerEntity.getDepositAccountEntities().get(i).getAccountNumber());
                            }
                        }
                    }
                    do{
                        while(true){
                            int spotter = 0;
                            System.out.print("Enter deposit account number> ");
                            associateAccount = scanner.nextLine().trim();
                            for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                                if(customerEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(associateAccount)){
                                    spotter++;
                                    break;
                                }
                            }
                            if(spotter == 0){
                                System.out.println("Customer does not have this deposit account.\n");
                            }else{
                                int counter = 0;
                           // for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                                for(int j = 0; j<atmCardEntity.getDepositAccountEntities().size(); j++){
                                    if(atmCardEntity.getDepositAccountEntities().get(j).getAccountNumber().equals(associateAccount)){
                                        counter++;
                                        break;
                                    }
                                }
                                if(counter != 0){
                                    System.out.println("Enter a valid account number.\n");
                                } else {
                                    System.out.println("Deposit Account " + associateAccount + " selected.\n");
                                    break;
                                }
                            }
                        }
                        DepositAccountEntity newAccount = depositAccountSessionBeanRemote.retrieveDepositAccountByDepositAccountNumber(associateAccount);
                        atmCardEntitySessionBeanRemote.associateAtmCard(newAccount, atmCardEntity);
                        atmCardEntity.getDepositAccountEntities().add(newAccount);
                        System.out.println("ATM card successfully linked with deposit account.");
                        System.out.print("Associate with more deposit accounts? Enter 'N' if no and any key if yes.> ");
                        moreDepositAccount = scanner.nextLine().trim();
                    } while(!moreDepositAccount.equals("N"));

                }
            }
            
        }else{
            if(customerEntity.getDepositAccountEntities().isEmpty()){
                throw new UncreatedDepositAccountException("Create deposit account first.\n");
                
            } else {
                throw new AtmCardExistsException("Customer already has an ATM card.\n");
                
            }
            
        }
        
    }
    
    public void issueReplacementAtmCard(String customerIdentificationNumber) throws AtmCardNotFoundException, CustomerNotFoundException, DepositAccountNotFoundException, AtmCardExistsException, UncreatedDepositAccountException, UncreatedAtmCardException{
        Scanner scanner = new Scanner(System.in);
        CustomerEntity customerEntity = customerEntitySessionBeanRemote.retrieveCustomerByCustomerIdentificationNumber(customerIdentificationNumber);
        if(customerEntity.getAtmCardEntities()!=null&&!customerEntity.getDepositAccountEntities().isEmpty()){
        
            System.out.println("Issuing replacement ATM Card.\n");
            System.out.print("Enter ATM card number to replace> ");
            String atmCardNumber = scanner.nextLine().trim();
            
            if(!customerEntity.getAtmCardEntities().getCardNumber().equals(atmCardNumber)){
                throw new AtmCardNotFoundException("ATM card not found.\n");
            }

            AtmCardEntity oldAtmCardEntity = atmCardEntitySessionBeanRemote.retrieveAtmCardEntityByAtmCardNumber(atmCardNumber);
            atmCardEntitySessionBeanRemote.deleteAtmCardEntity(customerIdentificationNumber, oldAtmCardEntity.getCardNumber());
            issueAtmCard(customerIdentificationNumber);
        } else {
            if(customerEntity.getDepositAccountEntities().isEmpty()){
                throw new UncreatedDepositAccountException("Create deposit account first.\n");
            }else{
                throw new UncreatedAtmCardException("Create ATM card first.\n");
            }
        }
        
    }
    
    public static String generateRandomString(int length) {
        String characterPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterPool.length());
            char randomChar = characterPool.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
    
}

//depositAccountNo = depositAccountNumber;
                //DepositAccountEntity depositAccountEntity;
               /** if(depositAccountNumber<=0 || depositAccountNumber>customerEntity.getDepositAccountEntities().size()){
                    System.out.println("Enter a valid number");
                }else{
                    depositAccountNo = customerEntity.getDepositAccountEntities().get(depositAccountNumber-1).getAccountNumber();
                    System.out.println("Deposit Account " + depositAccountNo + " selected.");
                    break;
                }
                **/
                /**for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                    if(customerEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(depositAccountNumber)){
                        //depositAccountNo = depositAccountNumber;
                        completed++;
                        break;
                    }
                }
                if(completed == 0){
                    System.out.println("Deposit Account not found for customer.\n");
                }else{
                    break;
                }
                * **/
/**
 * 
                            if(associateAccount<=0 || associateAccount > counter){
                                System.out.println("Enter a valid number");
                            }else {
                                depositAccountAssoc = 
                            }
                            //DepositAccountEntity depositAccountEntity;
                            for(int i = 0; i < customerEntity.getDepositAccountEntities().size(); i++){
                                if(customerEntity.getDepositAccountEntities().get(i).getAccountNumber().equals(depositAccountAssoc)){
                                    //depositAccountNo = depositAccountNumber;
                                    completed2++;
                                    break;
                                }
                            }
                            if(completed2 == 0){
                                System.out.println("Deposit Account not found for customer.\n");
                            }else{
                                break;
                            }
 **/
