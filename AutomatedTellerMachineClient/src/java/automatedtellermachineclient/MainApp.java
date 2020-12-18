package automatedtellermachineclient;


import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import util.exception.AtmCardNotFoundException;
import util.exception.InvalidAtmCardException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eviehwy
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    
    private Customer currentCustomer;
    private AtmCard currentAtmCard;
    private List<DepositAccount> depositAccounts;

    public MainApp() {
        currentCustomer = null;
        currentAtmCard = null;
    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this();
        
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }
    
    
    
    public void run() {
     
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Automated Teller Machine ***\n");
            System.out.println("1: Insert ATM CARD");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = sc.nextInt();

                if(response == 1)
                {
                    try
                    {
                        insertAtmCard();
                        System.out.println("Welcome " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + "!");
                        menuMain();
                    }
                    catch(AtmCardNotFoundException ex) 
                    {
                        System.out.println("Invalid ATM card!\n");
                    }
                }
                else if (response == 2)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2)
            {
                break;
            }
        }
    }
          
    private void insertAtmCard() throws AtmCardNotFoundException
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Automated Teller Machine :: Login ***\n");
        System.out.print("Enter ATM Card Number> ");
        String atmCardNum = sc.nextLine().trim();
        try {
            AtmCard atmCard = atmCardSessionBeanRemote.retrieveAtmCardByAtmCardNum(atmCardNum);
            System.out.print("Enter PIN> ");
            String pin = sc.nextLine().trim();
            if (pin.equals(atmCard.getPin()) && atmCard.getEnabled()) {
                currentAtmCard = atmCardSessionBeanRemote.retrieveAtmCardByAtmCardNum(atmCardNum);     
                currentCustomer = currentAtmCard.getCustomer();
            }
            else 
            {
                throw new AtmCardNotFoundException();
            }
        }
        catch (AtmCardNotFoundException ex)
        {
            System.out.println("Invalid ATM card!");
        }
    }
    
    public void menuMain() 
    {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Automated Teller Machine ***\n");
            System.out.println("1: Change PIN");
            System.out.println("2: Enquire Available Balance");
            System.out.println("3: Back\n");
            
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = sc.nextInt();

                if(response == 1)
                {
                    doChangePIN(currentAtmCard);
                }
                
                else if(response == 2)
                {
                    doEnquireAvailableBalance(currentAtmCard);
                }
                
                else if(response == 3)
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
    
    private void doChangePIN(AtmCard atmCard) 
    {
        Scanner sc = new Scanner(System.in);
        String currentPin;
        String newPin;
        String newPin2;
        
        System.out.println("*** Automated Teller Machine :: Change PIN ***\n");
        System.out.print("Enter Current PIN> ");
        currentPin = sc.nextLine().trim();
        if(currentPin.equals(atmCard.getPin()))
        {
            while(true)
            {
                System.out.print("Enter New PIN> ");
                newPin = sc.nextLine().trim();
                System.out.print("Confirm New PIN> ");
                newPin2 = sc.nextLine().trim();
                if(newPin.equals(newPin2))
                {
                    atmCard.setPin(newPin);
                    break;
                } 
                else
                { 
                    System.out.println("PINs do not match, try again!\n");
                }
            } 
            
            try
            {
                atmCardSessionBeanRemote.changePin(atmCard);
                System.out.println("PIN updated successfully!\n");
            }
            catch (AtmCardNotFoundException ex)
            {
                System.out.println("Atm Card has been disabledt!\n");
            }
        }
        else 
        {
            System.out.println("Current Pin is invalid!\n");
        }
    }

    
    private void doEnquireAvailableBalance(AtmCard atmCard)
    {
        System.out.println("*** Automated Teller Machine :: Enquire Available Balance ***\n");
        depositAccounts = atmCard.getDepositAccounts();
        if(depositAccounts.size() == 1)
        {
            DepositAccount depositAccount = depositAccounts.get(0);
            String currentAccountNum = depositAccount.getAccountNumber();
            BigDecimal availBalance = depositAccount.getAvailableBalance();
            System.out.println("Available balance for Deposit Account " + currentAccountNum + ": " + availBalance +"\n");
        } else {
            //if there are more than one deposit accounts associated to current atm card
            //choose deposit account to enquire available balance from
        }
    }
}

