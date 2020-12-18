/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AtmCardNotFoundException;
import util.exception.CardNumberExistException;
import util.exception.DeleteAtmCardException;
import util.exception.InvalidAtmCardException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */
@Stateless
public class AtmCardSessionBean implements AtmCardSessionBeanRemote, AtmCardSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    public AtmCardSessionBean() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public AtmCard createNewAtmCard(AtmCard newAtmCard, Long customerId) throws CardNumberExistException, UnknownPersistenceException
    {
        try
        { 
            
            em.persist(newAtmCard);
            em.flush();
            return newAtmCard;
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new CardNumberExistException();
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
    public AtmCard retrieveAtmCardByAtmCardId(Long atmCardId) throws AtmCardNotFoundException
    {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        
        if(atmCard != null)
        {
            return atmCard;
        }
        else
        {
            throw new AtmCardNotFoundException();
        }
    }
    
    @Override
    public AtmCard retrieveAtmCardByAtmCardNum(String atmCardNum) throws AtmCardNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM AtmCard c WHERE c.cardNumber = :inCardNumber");
        query.setParameter("inCardNumber", atmCardNum);
        try
        {
            AtmCard atmCard = (AtmCard)query.getSingleResult();
            atmCard.getDepositAccounts().size();
            return atmCard;
        }
        catch (NoResultException | NonUniqueResultException ex) {
            throw new AtmCardNotFoundException();
        }
    }
    
    
    @Override
    public void associateCardAndCustomer(Long atmCardId, Long customerId) {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        Customer customer = em.find(Customer.class, customerId);
        atmCard.setCustomer(customer);
        customer.setAtmCard(atmCard);
    }
    
    @Override
    public void associateCardAndAccount(Long atmCardId, Long customerId) {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        Customer customer = em.find(Customer.class, customerId);
        List<DepositAccount> depositAccounts = customer.getDepositAccounts();
        for (DepositAccount depositAccount : depositAccounts) {
            depositAccount.setAtmCard(atmCard);
        }
        atmCard.setDepositAccounts(depositAccounts); 
    }
    
    @Override
    public void disableAtmCard (Long atmCardId) {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        atmCard.setEnabled(false);
    }
    
    @Override
    public void deleteAtmCard(Long atmCardId)
    {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        if (!atmCard.getEnabled()) 
        {
            em.remove(atmCard);
            em.flush();
        }   
    }
        
    
    @Override
    public void changePin(AtmCard atmCard) throws AtmCardNotFoundException
    {
        if (atmCard != null && atmCard.getAtmCardId() != null && atmCard.getEnabled())
        {
            AtmCard atmCardToUpdate = retrieveAtmCardByAtmCardId(atmCard.getAtmCardId());
            
            if(atmCardToUpdate.getCardNumber().equals(atmCard.getCardNumber()))
                atmCardToUpdate.setPin(atmCard.getPin());
        }
        else 
        {
            throw new AtmCardNotFoundException();
        }
    }
}




