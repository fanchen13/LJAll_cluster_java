package com.fanchen;

import com.fanchen.xmlutil.XmlReader2DOM;
import org.apache.log4j.Logger;


/**
 * Created by fanchen on 2017/4/22.
 */
public class CLUSTERLauncher {
    public static Logger LOG = Logger.getLogger(CLUSTERLauncher.class);

    public static void main(String... args) {

        String inFilePath = "D:/TestData/EmperorQin.txt";//wechat source data
        String outFilePath = "test/out_EmperorQin.xml";//after cluster out path

        ClusterThread clusterThread = new ClusterThread();

        /*set function params*/
        clusterThread.setnMaxClus(2000);
        clusterThread.setnMaxDoc(2000);
        clusterThread.setInFilePath(inFilePath);
        clusterThread.setOutFilePath(outFilePath);

        Thread thread = new Thread(clusterThread);
        thread.setName("clusterThread");
        thread.start();

        try {
            LOG.info("start another thread to insert into SQLite");
            thread.join();

            LOG.info("start insert action");
            XmlReader2DOM.getDataFromXML(outFilePath);

        } catch (InterruptedException e) {
            LOG.info(e.getMessage(),e);
        }
    }
}
