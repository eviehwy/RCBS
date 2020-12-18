/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Local;
import util.exception.AtmCardNotFoundException;
import util.exception.CardNumberExistException;
import util.exception.DeleteAtmCardException;
import util.exception.InvalidAtmCardException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Local
public interface AtmCardSessionBeanLocal {

    public AtmCard createNewAtmCard(AtmCard newAtmCard, Long customerId) throws CardNumberExistException, UnknownPersistenceException;

    public AtmCard retrieveAtmCardByAtmCardId(Long atmCardId) throws AtmCardNotFoundException;
    
    public AtmCard retrieveAtmCardByAtmCardNum(String atmCardNum) throws AtmCardNotFoundException;

    public void associateCardAndAccount(Long atmCardId, Long customerId);

    public void associateCardAndCustomer(Long AtmCardId, Long customerId);

    public void disableAtmCard(Long atmCardId);
    
    public void deleteAtmCard(Long atmCardId);

    public void changePin(AtmCard atmCard) throws AtmCardNotFoundException;

}
