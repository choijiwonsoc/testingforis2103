/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import enumeration.EmployeeAccessRightEnum;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author choijiwon
 */
@Singleton
@LocalBean
@Startup
public class DataInitialiseSessionBean {

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @PersistenceContext(unitName = "RetailCoreBankingSystemJpa-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct(){
        if(em.find(EmployeeEntity.class, 1l) == null ){
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("Main", "Teller", "teller", "password", EmployeeAccessRightEnum.TELLER));
        }
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
