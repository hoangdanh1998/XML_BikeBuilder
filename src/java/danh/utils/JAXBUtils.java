/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.utils;

import danh.db.Accessary;
import danh.xedap24h.XeDap24hProductCrawler;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author apple
 */
public class JAXBUtils {

    public static boolean validateXml(String filePath, Object object) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
          
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);            
            Schema schema = factory.newSchema(new File(filePath));

            Validator validator = schema.newValidator();
            validator.validate(new JAXBSource(marshaller, object));
            return true;

        } catch (JAXBException e) {
            Logger.getLogger(JAXBUtils.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } catch (SAXException e) {
            Logger.getLogger(JAXBUtils.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } catch (IOException e) {
            Logger.getLogger(JAXBUtils.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
