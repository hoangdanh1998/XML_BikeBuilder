/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.utils;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 *
 * @author apple
 */
class XmlValidatorHandler  implements ValidationEventHandler{

    @Override
    public boolean handleEvent(ValidationEvent event) {
         return true;
    }
    
}