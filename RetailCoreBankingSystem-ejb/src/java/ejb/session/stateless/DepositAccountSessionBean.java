/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AccountNumberExistException;
import util.exception.DepositAccountNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Stateless
public class DepositAccountSessionBean implements DepositAccountSessionBeanRemote, DepositAccountSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    public DepositAccountSessionBean() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public DepositAccount createNewDepositAccount(DepositAccount newDepositAccount, Long customerId) throws AccountNumberExistException, UnknownPersistenceException
    {
        try
        {
            Customer customer = em.find(Customer.class, customerId);
            newDepositAccount.setCustomer(customer);
            
            em.persist(newDepositAccount);
            em.flush();
            return newDepositAccount;
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new AccountNumberExistException();
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
    public List<DepositAccount> retrieveAllDepositAccounts()
    {
        Query query = em.createQuery("SELECT d FROM DepositAccount d, IN (d.customer) c");
        
        return query.getResultList();
    }
    
    @Override
    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depositAccountId) throws DepositAccountNotFoundException
    {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);
        
        if(depositAccount != null)
        {
            return depositAccount;
        }
        else
        {
            throw new DepositAccountNotFoundException();
        }
    }
    
    @Override
    public DepositAccount retrieveDepositAccountByAccountNum(String accountNum) throws DepositAccountNotFoundException
    {
        DepositAccount depositAccount = em.find(DepositAccount.class, accountNum);
        
        if(depositAccount != null)
        {
            return depositAccount;
        }
        else
        {
            throw new DepositAccountNotFoundException();
        }
    }
    
    @Override
    public void associateAccountAndCustomer(Long depositAccountId, Long customerId) {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);
        Customer customer = em.find(Customer.class, customerId);
        depositAccount.setCustomer(customer);
        customer.getDepositAccounts().add(depositAccount);
    }
    
    @Override
    public void dissociateAccountAndCustomer (Long depositAccountId, Long customerId) {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);
        Customer customer = em.find(Customer.class, customerId);
        
        em.remove(customer);
        customer.getDepositAccounts().remove(depositAccount);
    }

}

