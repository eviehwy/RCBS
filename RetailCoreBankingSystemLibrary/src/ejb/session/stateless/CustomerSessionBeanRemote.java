/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Remote;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Remote
public interface CustomerSessionBeanRemote {
    
    public Customer createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException;
    
    public List<Customer> retrieveAllCustomers();
    
    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException;
    
    public Customer retrieveCustomerByIdentificationNum(String customerNRIC) throws CustomerNotFoundException;
    
    public void associateCustomerAndAccount(Long depositAccountId, Long customerId);

    public void dissociateCustomerAndAccount(Long depositAccountId, Long customerId);
    
}
