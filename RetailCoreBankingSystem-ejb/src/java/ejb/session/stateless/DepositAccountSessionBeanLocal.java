/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccountNumberExistException;
import util.exception.DepositAccountNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Local
public interface DepositAccountSessionBeanLocal {

    public DepositAccount createNewDepositAccount(DepositAccount newDepositAccount, Long customerId) throws AccountNumberExistException, UnknownPersistenceException;

    public List<DepositAccount> retrieveAllDepositAccounts();
    
    public DepositAccount retrieveDepositAccountByAccountNum(String accountNum) throws DepositAccountNotFoundException;

    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depositAccountId) throws DepositAccountNotFoundException;

    public void associateAccountAndCustomer(Long depositAccountId, Long customerId);

    public void dissociateAccountAndCustomer(Long depositAccountId, Long customerId);
}
