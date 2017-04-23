package com.fanchen.xmlutil;

import org.junit.Test;

/**
 * Created by fanchen on 2017/4/23.
 */
public class XmlReader2DOMTest {
    @Test
    public void testGetDataFromXML(){
        String filePath="test/out_EmperorQin.txt_2000_2000.xml";
        XmlReader2DOM.getDataFromXML(filePath);
    }
}
