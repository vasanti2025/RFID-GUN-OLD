package com.loyalstring.Excels.reader;

//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLInputFactory;


import com.fasterxml.aalto.stax.InputFactoryImpl;

import javax.xml.stream.XMLInputFactory;

public class DefaultXMLInputFactory {
    static final XMLInputFactory factory = defaultXmlInputFactory();

    private static XMLInputFactory defaultXmlInputFactory() {
        XMLInputFactory factory  = new InputFactoryImpl();
        // To prevent XML External Entity (XXE) attacks
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        return factory;
    }

}
