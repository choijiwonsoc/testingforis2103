/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package retailcorebankingsystemjpatellerclient;

import ejb.session.stateless.AtmCardEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author choijiwon
 */
public class Main {
    
    @EJB
    private static EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;
    @EJB
    private static DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    @EJB
    private static CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    @EJB
    private static AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MainApp mainApp = new MainApp(employeeEntitySessionBeanRemote, depositAccountSessionBeanRemote, customerEntitySessionBeanRemote, atmCardEntitySessionBeanRemote);
        mainApp.runApp();
        // TODO code application logic here
    }
    
}
