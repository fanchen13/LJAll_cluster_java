package com.fanchen;

import com.fanchen.cluster.ClibraryCluster;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by fanchen on 2017/4/23.
 */
public class ClusterThread implements Runnable {

    public static Logger LOG = Logger.getLogger(ClusterThread.class);
    private boolean flag = true;
    private int nMaxClus = 2000;
    private int nMaxDoc = 2000;
    private String inFilePath = "path/to/inFile";
    private String outFilePath = "path/to/outFile";//format XML

    @Override
    public void run() {
        LOG.info("start run cluster thread");
        if(flag){
            LOG.info("CLUSTERLauncher application starts");
            boolean flag = ClibraryCluster.Instance.CLUS_Init("", "", 1);
            if (flag) {
                LOG.info("init success");
            } else {
                LOG.info("init failed");
                LOG.error(ClibraryCluster.Instance.CLUS_GetLastErrMsg());
                System.exit(1);
            }


            LOG.info("set function params");
            ClibraryCluster.Instance.CLUS_SetParameter(nMaxClus, nMaxDoc);

            LOG.info("add source file " + inFilePath);

            /*read file content by line*/
            File sourceFile = new File(inFilePath);
            try {
                Scanner in = new Scanner(sourceFile);
                while (in.hasNext()) {
                    String line = in.nextLine();
                    String[] line2arr = line.split("\\s+");
                    LOG.info("line2arr : " + Arrays.toString(line2arr));
                    LOG.info("fetch message");
                    if (line2arr.length > 6) {
                        StringBuilder msg = new StringBuilder();
                        boolean blank = true;
                        LOG.info("append message");
                        for (int i = 6; i < line2arr.length;i++) {
                            if(line2arr[i].startsWith("[")){
                                LOG.info("message only contains expression token");
                                LOG.info("purge some words: "+ line2arr[i]);
                                blank = false;
                            }else if(line2arr[i].contains("[")){
                                LOG.info("message contains expression token");
                                LOG.info("message : " + line2arr[i]);
                                LOG.info("purge expression token from message");

                                //TODO mutil expression token how to purge

                                String tmp = line2arr[i];
                                while (tmp.contains("[")&&tmp.contains("]")){
                                    int index_bracket_left = tmp.indexOf("[");
                                    LOG.info("index of left bracket is : "+ index_bracket_left);
                                    int index_bracket_right = tmp.indexOf("]");
                                    LOG.info("index of right bracket is : "+ index_bracket_right);
                                    tmp = tmp.substring(0,index_bracket_left)+tmp.substring(index_bracket_right+1);
                                }

                                msg.append(tmp + " ");
                                LOG.info("purge expression token message : " + msg.toString());
                            }else {
                                LOG.info("word : " +line2arr[i]);
                                msg.append(line2arr[i] + " ");
                            }
                        }

                        if(blank){
                            LOG.info("message : " + msg.toString());
                            LOG.info("CLUSTERLauncher application add content string");
                            UUID index = UUID.randomUUID();
                            ClibraryCluster.Instance.CLUS_AddContent(msg.toString(), index.toString());
                        }else {
                            LOG.info("msg is not text format");
                        }

                    }
                }

            } catch (FileNotFoundException e) {
                LOG.error(e.getMessage(), e);
            }

            LOG.info("result out file : " + outFilePath);
            ClibraryCluster.Instance.CLUS_GetLatestResult(outFilePath);

            ClibraryCluster.Instance.CLUS_Exit();
            LOG.info("cluster application exit");
        }
    }

    public void stop(){
        flag = false;
    }

    public int getnMaxClus() {
        return nMaxClus;
    }

    public void setnMaxClus(int nMaxClus) {
        this.nMaxClus = nMaxClus;
    }

    public int getnMaxDoc() {
        return nMaxDoc;
    }

    public void setnMaxDoc(int nMaxDoc) {
        this.nMaxDoc = nMaxDoc;
    }

    public String getInFilePath() {
        return inFilePath;
    }

    public void setInFilePath(String inFilePath) {
        this.inFilePath = inFilePath;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }
}
