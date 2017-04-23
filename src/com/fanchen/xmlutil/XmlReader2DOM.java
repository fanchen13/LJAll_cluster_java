package com.fanchen.xmlutil;

import com.fanchen.sqliteutil.ISQLite;
import com.fanchen.sqliteutil.SQLiteImpl;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by fanchen on 2017/4/23.
 */
public class XmlReader2DOM {

    public static Logger LOG = Logger.getLogger(XmlReader2DOM.class);
    private static ISQLite isqLite = new SQLiteImpl();

    public static void getDataFromXML(String fliePath) {

        LOG.info("read data from xml file : " + fliePath);
        File file = new File(fliePath);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();

            Element foo ;
            for (Iterator it = root.elementIterator("clus"); it.hasNext(); ) {
                foo = (Element) it.next();
                /*get elements and attributes*/

                String clusId ;
                String feature;
                String docsNum;
                LOG.info("fetch attribute clus's id");
                Attribute clusId_attr = foo.attribute("id");
                clusId = clusId_attr.getValue();
                LOG.info("clusId : " + clusId);

                LOG.info("fetch element feature");
                feature = foo.elementText("feature");
                LOG.info("feature : " + feature);

                LOG.info("fetch attribute docs's Num");
                Element docs = foo.element("docs");
                Attribute docsNum_arrt = docs.attribute("num");
                docsNum = docsNum_arrt.getValue();
                LOG.info("docsNum : " + docsNum);

                /*restore data to SQLite*/

                LOG.info("insert into SQLite");
                isqLite.insert("message",clusId,feature,docsNum);

            }

        } catch (DocumentException e) {
            LOG.error(e.getMessage(), e);
        }

    }
}
