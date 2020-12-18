/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AtmCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    public CustomerSessionBean() {
    }

 
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Customer createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException
    {
        try
        {
            em.persist(newCustomer);
            
            //for(DepositAccount depositAccount:newCustomer.getDepositAccounts())
            //{
		//em.persist(depositAccount); 
            //}
            
            //AtmCard atmCard = newCustomer.getAtmCard();
            //em.persist(atmCard);
            
            em.flush();
            return newCustomer;
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new CustomerExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public List<Customer> retrieveAllCustomers()
    {
        Query query = em.createQuery("SELECT c FROM Customer c");
        List<Customer> customers = query.getResultList();
        
        for (Customer customer:customers)
        {
            customer.getDepositAccounts().size();
            //customer.getCurrentDepositAccount();
        }
        
        return customers;
    }
        
    @Override
    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException
    {
        Customer customer = em.find(Customer.class, customerId);
        
        if(customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException();
        }
    }
    
    @Override
    public Customer retrieveCustomerByIdentificationNum(String customerNRIC) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.identificationNumber = :inIdentificationNumber");
        query.setParameter("inIdentificationNumber", customerNRIC);
        try
        {
            Customer customer = (Customer)query.getSingleResult();
            customer.getDepositAccounts().size();
            return customer;
        }
        catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void associateCustomerAndAccount(Long depositAccountId, Long customerId) {
        Customer customer = em.find(Customer.class, customerId);
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);
    }
    
    @Override
    public void dissociateCustomerAndAccount (Long depositAccountId, Long customerId) {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);
        Customer customer = em.find(Customer.class, customerId);
        
        em.remove(customer);
        customer.getDepositAccounts().remove(depositAccount);
    }
    
}

