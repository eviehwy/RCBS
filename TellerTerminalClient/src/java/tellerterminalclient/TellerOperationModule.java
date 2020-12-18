/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import entity.Employee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.DepositAccountType;
import util.exception.AccountNumberExistException;
import util.exception.AtmCardNotFoundException;
import util.exception.CardNumberExistException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerExistException;
import util.exception.DeleteAtmCardException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
public class TellerOperationModule {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;

    public TellerOperationModule() {
    }

    public TellerOperationModule(CustomerSessionBeanRemote customerSessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, AtmCardSessionBeanRemote atmCardSessionBeanRemote, Employee currentEmployee) {
        this();
        
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }
    
    
    public void menuTellerOperation() 
    {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Teller Terminal :: Teller Operation ***\n");
            System.out.println("1: Create Customer");
            System.out.println("2: Open Deposit Account");
            System.out.println("3: Issue Atm Card");
            System.out.println("4: Issue Replacement Atm Card");
            System.out.println("5: Back\n");

            response = 0;
            
            while(response < 1 || response > 4)
            {
                System.out.print("> ");

                response = sc.nextInt();

                if(response == 1)
                {
                    doCreateNewCustomer();
                }
                
                else if(response == 2)
                {
                    doOpenDepositAccount();
                }
                
                else if(response == 3)
                {
                    doIssueAtmCard();
                }
                
                else if(response == 4)
                {
                    doIssueReplacementAtmCard();
                }
                
                else if(response == 5)
                {
                    break;
                }
                
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 4)
            {
                break;
            }
        }
    }
    
    private void doCreateNewCustomer()
    {
        Scanner sc = new Scanner(System.in);
        Customer newCustomer = new Customer();
        newCustomer.setDepositAccounts(new ArrayList<>());

        System.out.println("*** Teller Terminal :: Teller Operation :: Create New Customer ***\n");
        System.out.print("Enter First Name> ");
        newCustomer.setFirstName(sc.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newCustomer.setLastName(sc.nextLine().trim());
        System.out.print("Enter Identification Number> ");
        newCustomer.setIdentificationNumber(sc.nextLine().trim());
        System.out.print("Enter Contact Number> ");
        newCustomer.setContactNumber(sc.nextLine().trim());
        System.out.print("Enter Address Line 1> ");
        newCustomer.setAddressLine1(sc.nextLine().trim());
        System.out.print("Enter Address Line 2> ");
        newCustomer.setAddressLine2(sc.nextLine().trim());
        System.out.print("Enter Postal Code> ");
        newCustomer.setPostalCode(sc.nextLine().trim());
       
        try
        {
            newCustomer = customerSessionBeanRemote.createNewCustomer(newCustomer);
            
            System.out.println("New customer created successfully!: " + newCustomer.getCustomerId() + "\n");
        }
        catch(CustomerExistException ex)
        {
            System.out.println("An error has occurred while creating the new customer!: The customer already exist\n");
        }
        catch(UnknownPersistenceException ex)
        {
            System.out.println("An unknown error has occurred while creating the new customer!: " + ex.getMessage() + "\n");
        }
        //catch(AccountNumberExistException ex)
        //{
        //    System.out.println("An error has occurred while creating the new customer!\n");
        //}
    }

    private void doOpenDepositAccount() 
    {
        Scanner sc = new Scanner(System.in);
        DepositAccount newDepositAccount = new DepositAccount();
        newDepositAccount.setTransactions(new ArrayList<>());
        Customer customer;
                
        System.out.println("*** Teller Terminal :: Teller Operation :: Open Deposit Account ***\n");
        System.out.print("Enter Customer Identification Number> ");
        String customerNRIC = sc.nextLine().trim();
        try {
            customer = customerSessionBeanRemote.retrieveCustomerByIdentificationNum(customerNRIC);
            System.out.print("Enter Account Number> ");
            newDepositAccount.setAccountNumber(sc.nextLine().trim());
            while (true) {
                System.out.print("Enter Account Type (1: Savings, 2: Current)> ");
                Integer depositAccountType = sc.nextInt();
                if (depositAccountType == 1 || depositAccountType == 2) {
                    newDepositAccount.setAccountType(DepositAccountType.values()[depositAccountType-1]);
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            System.out.print("Enter Initial Deposit Amount> ");
            newDepositAccount.setAvailableBalance(sc.nextBigDecimal());   
            newDepositAccount.setHoldBalance(new BigDecimal("0.00"));
            newDepositAccount.setLedgerBalance(new BigDecimal("0.00"));
            newDepositAccount.setEnabled(true);
            newDepositAccount.setAtmCard(null);
            
            newDepositAccount = depositAccountSessionBeanRemote.createNewDepositAccount(newDepositAccount, customer.getCustomerId());
            depositAccountSessionBeanRemote.associateAccountAndCustomer(newDepositAccount.getDepositAccountId(), customer.getCustomerId());
            System.out.println("New deposit account opened successfully!: " + newDepositAccount.getDepositAccountId() + "\n");
            }
            catch(CustomerNotFoundException ex)
            {
                System.out.println("Customer is not registered!\n");
                doCreateNewCustomer();
            }
            catch(AccountNumberExistException ex)
            {
                System.out.println("An error has occurred while creating the new deposit account!: The account number already exist\n");
            } 
            catch (UnknownPersistenceException ex) 
            {
                System.out.println("An unknown error has occurred while creating the new deposit account!: " + ex.getMessage() + "\n");
            }
    }
    
    private void doIssueAtmCard() 
    {
        Scanner sc = new Scanner(System.in);
        AtmCard newAtmCard= new AtmCard();
        Customer customer;
                
        System.out.println("*** Teller Terminal :: Teller Operation :: Issue Atm Card ***\n");
        System.out.print("Enter Customer Identification Number> ");
        String customerNRIC = sc.nextLine().trim();
        try {
            customer = customerSessionBeanRemote.retrieveCustomerByIdentificationNum(customerNRIC);
            if (customer.getAtmCard() == null || !customer.getAtmCard().getEnabled()) {
                System.out.print("Enter Card Number> ");
                newAtmCard.setCardNumber(sc.nextLine().trim());
                System.out.print("Enter Name On Card> ");
                newAtmCard.setNameOnCard(sc.nextLine().trim());
                newAtmCard.setEnabled(true);
                newAtmCard.setPin("000000");

                newAtmCard = atmCardSessionBeanRemote.createNewAtmCard(newAtmCard, customer.getCustomerId());
                atmCardSessionBeanRemote.associateCardAndCustomer(newAtmCard.getAtmCardId(), customer.getCustomerId());
                atmCardSessionBeanRemote.associateCardAndAccount(newAtmCard.getAtmCardId(), customer.getCustomerId());
                System.out.println("New ATM issued successfully!: " + newAtmCard.getAtmCardId() + "\n");
            }
            else
            {
                System.out.println("Customer is already issued an ATM card! If card is lost or damaged, issue replacement ATM card.\n");
            }
        }
        catch(CustomerNotFoundException ex)
        {
            System.out.println("Customer is not registered!\n");
        }
        catch(CardNumberExistException ex)
        {
            System.out.println("An error has occurred while creating the new customer!: The account number already exist\n");
        } 
        catch (UnknownPersistenceException ex) 
        {
            System.out.println("An unknown error has occurred while creating the new deposit account!: " + ex.getMessage() + "\n");
        }
        
    }
    
    private void doIssueReplacementAtmCard()
    {
        Scanner sc = new Scanner(System.in);
        AtmCard atmCardToReplace;
        
        System.out.println("*** Teller Terminal :: Teller Operation :: Issue Replacement Atm Card ***\n");
        System.out.print("Enter ATM Card Number to Replace> ");
        String atmCardNum = sc.next();
        
        try {
            atmCardToReplace = atmCardSessionBeanRemote.retrieveAtmCardByAtmCardNum(atmCardNum);
            Long atmCardId = atmCardToReplace.getAtmCardId();
            atmCardSessionBeanRemote.disableAtmCard(atmCardId);
            System.out.println("ATM Card " + atmCardToReplace.getCardNumber() + " disabled, issue a replacement card!\n");
            doIssueAtmCard();
            atmCardSessionBeanRemote.deleteAtmCard(atmCardId);
            System.out.println("Disabled ATM Card has been deleted.\n");
        } 
        catch(AtmCardNotFoundException ex)
        {
            System.out.println("Atm Card " + atmCardNum + " does not exist!\n");
        }
    }
}
