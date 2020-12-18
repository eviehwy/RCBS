/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Local
public interface CustomerSessionBeanLocal {

    public Customer createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException;

    public List<Customer> retrieveAllCustomers();
    
    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException;
    
    public Customer retrieveCustomerByIdentificationNum(String customerNRIC) throws CustomerNotFoundException;

    public void associateCustomerAndAccount(Long depositAccountId, Long customerId);

    public void dissociateCustomerAndAccount(Long depositAccountId, Long customerId);
  
}
