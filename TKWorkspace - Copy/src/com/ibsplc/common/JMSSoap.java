package com.ibsplc.common;

import org.testng.Assert;
import org.xml.sax.SAXException;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

/**
 * Created by A-7681 on 5/18/2018.
 */
public class JMSSoap extends SOAP {

    private String dataFilePath = PropertyHandler.getPropValue("resources\\EnvSetup.properties", "testDataDirectory");
    private String reqPath = dataFilePath + "\\JMSReq";
    private String resPath = dataFilePath + "\\JMSRes";

    private QueueConnectionFactory qconFactory;
    private QueueConnection qcon;
    private QueueSession qsession;
    private QueueSender qsender;
    private QueueReceiver qreceiver;
    private Queue reqQueue;
    private Queue resQueue;
    private TextMessage msg;
    private InitialContext context;

    public JMSSoap(String jndiFactory, String url, String jmsBinding, String dataFileName) throws NamingException, JMSException {

        dataFilePath = dataFilePath + dataFileName;

        jndiFactory = PropertyHandler.getPropValue(dataFilePath,jndiFactory);
        url = PropertyHandler.getPropValue(dataFilePath,url);
        jmsBinding = PropertyHandler.getPropValue(dataFilePath,jmsBinding);

        //initialize JMS
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, jndiFactory);
        env.put(Context.PROVIDER_URL, url);
        context = new  InitialContext(env);
        qconFactory = (QueueConnectionFactory) context.lookup(jmsBinding);
        qcon = qconFactory.createQueueConnection();
        qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    /**
     * Sends a JMS request created from the given sample to the specified request queue and saves the response from the response queue to the response File.
     * @param reqQueueName
     * @param resQueueName
     * @param sampleFileName
     * @param resFilename
     * @param nodeList : text file name for the list of nodes to change
     * @param nodeValues : should be given in the order given in the node list
     * @return
     * @throws JMSException
     * @throws NamingException
     * @author A-7681
     */
    public JMSSoap sendRequest(String reqQueueName, String resQueueName, String sampleFileName, String resFilename, String nodeList, String... nodeValues) throws JMSException, NamingException {

        reqQueueName = PropertyHandler.getPropValue(dataFilePath,reqQueueName);
        resQueueName = PropertyHandler.getPropValue(dataFilePath,resQueueName);

        reqQueue = (Queue) context.lookup(reqQueueName);
        resQueue = (Queue) context.lookup(resQueueName);
        qsender = qsession.createSender(reqQueue);
        qreceiver = qsession.createReceiver(resQueue);
        msg = qsession.createTextMessage();
        qcon.start();

        sampleFileName = reqPath + "\\" + sampleFileName;
        resFilename = resPath + "\\" + resFilename;
        nodeList = reqPath + "\\" + nodeList;

        for (int i = 0; i<nodeValues.length; i++){
            nodeValues[i] = PropertyHandler.getPropValue(dataFilePath,nodeValues[i]);
        }


        String reqFile = null;

        try {
            reqFile = setNodeValue(sampleFileName,nodeList,nodeValues);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        String xml = null;

        String corrID = BizUtility.createFlightNum() + "4";

        try {
            xml = new String(Files.readAllBytes(Paths.get(reqFile))).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            msg.setText(xml);
            msg.setJMSCorrelationID(corrID);
            msg.setJMSReplyTo((Destination) context.lookup(resQueueName));
            msg.setJMSDeliveryMode(1);
            qsender.send(msg);

            msg = (TextMessage)qreceiver.receive();

            Assert.assertEquals(msg.getJMSCorrelationID().trim(),corrID,"The response doesn't have the same correlation ID.");

            File resFile = new File(resFilename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(resFile));
            bw.write(msg.getText());
            bw.flush();
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Verifies dofferent elements in the response file
     * @param resFilename : Not read from datasheet
     * @param nodeList : text file name for the list of nodes to change
     * @param nodeValues : should be given in the order given in the node list
     * @return
     * @author A-7681
     */
    public JMSSoap verifyResponse(String resFilename, String nodeList, String... nodeValues){

        resFilename = resPath + "\\" + resFilename;
        nodeList = resPath + "\\" + nodeList;

        BufferedReader br = null;
        String line = null;
        String nodeValue = null;
        int i = 0;
        File nodeListFile = new File(nodeList);
        try {
            br = new BufferedReader(new FileReader(nodeListFile));
            while ((line = br.readLine()) != null){
                nodeValue = PropertyHandler.getPropValue(dataFilePath,nodeValues[i++]);
                Assert.assertEquals(getNodeValue(line,resFilename).trim().toLowerCase(),nodeValue.toLowerCase(),"The node value doesn't match for the node: " + line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Closes all the connections
     */
    public void close(){
        try {
            qsender.close();
            qreceiver.close();
            qsession.close();
            qcon.close();
        } catch (JMSException | NullPointerException e) {
            e.printStackTrace();
        }
    }


}
