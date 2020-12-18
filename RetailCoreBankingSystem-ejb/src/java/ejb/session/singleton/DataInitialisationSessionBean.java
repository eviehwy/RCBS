/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author eviehwy
 */

@Singleton
@LocalBean
@Startup

public class DataInitialisationSessionBean {
    
    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    

    public DataInitialisationSessionBean()
    {
    }   
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("teller");
        }
        catch(EmployeeNotFoundException ex)
        {
            initialiseData();
        }
    }
   
    
    private void initialiseData()
    {
        try
        {
            employeeSessionBeanLocal.createNewEmployee(new Employee("Default", "Teller", "teller", "password", EmployeeAccessRightEnum.TELLER));
        }
        catch(EmployeeUsernameExistException | UnknownPersistenceException ex)
        {
            ex.printStackTrace();
        }
    }
}
