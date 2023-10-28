/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package retailcorebankingsystemjpaautomatedclient;

import ejb.session.stateless.AtmCardEntitySessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author choijiwon
 */
public class Main {
    
    @EJB
    private static AtmCardEntitySessionBeanRemote atmCardEntitySessionBeanRemote;
    
    @EJB
    private static DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(atmCardEntitySessionBeanRemote, depositAccountSessionBeanRemote);
        mainApp.runApp();
        
        // TODO code application logic here
    }
    
}
