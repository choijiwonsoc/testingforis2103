/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCardEntity;
import entity.DepositAccountEntity;
import exception.AtmCardNotFoundException;
import exception.CustomerNotFoundException;
import exception.DepositAccountNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author choijiwon
 */
@Remote
public interface AtmCardEntitySessionBeanRemote {
    
   // public Long createNewAtmCard(AtmCardEntity newAtmCard);
    
    public List<AtmCardEntity> retrieveAllAtmCard();
    
    public AtmCardEntity createNewAtmCard(String customerIdNumber, DepositAccountEntity depositAccountEntity, AtmCardEntity newAtmCard) throws CustomerNotFoundException, DepositAccountNotFoundException;
    
    public void associateAtmCard(DepositAccountEntity depositAccountEntity, AtmCardEntity atmCardEntity);
    
    public AtmCardEntity retrieveAtmCardEntityByAtmCardNumber(String cardNumber) throws AtmCardNotFoundException;
    
    public void deleteAtmCardEntity(String customerIdNumber, String atmCardNumber) throws AtmCardNotFoundException, CustomerNotFoundException;
    
    public void changeAtmPin(String atmCardNumber, String atmPin) throws AtmCardNotFoundException;
}
