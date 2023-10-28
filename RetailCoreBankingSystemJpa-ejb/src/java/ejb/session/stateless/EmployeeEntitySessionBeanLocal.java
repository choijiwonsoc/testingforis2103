/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import exception.EmployeeNotFoundException;
import exception.InvalidLoginCredentialException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author choijiwon
 */
@Local
public interface EmployeeEntitySessionBeanLocal {

    public Long createNewEmployee(EmployeeEntity newEmployee);

    public List<EmployeeEntity> retrieveAllEmployee();

    public EmployeeEntity retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;

    public EmployeeEntity employeeLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
