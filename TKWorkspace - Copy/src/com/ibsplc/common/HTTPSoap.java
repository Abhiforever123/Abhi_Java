package com.ibsplc.common;

import org.testng.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by A-7681 on 5/18/2018.
 */
public class HTTPSoap extends SOAP {

    private String dataFilePath = PropertyHandler.getPropValue("resources\\EnvSetup.properties", "testDataDirectory");
    private String reqPath = dataFilePath + "\\HTTPReq";
    private String resPath = dataFilePath + "\\HTTPRes";
    private String sessionID;

    /**
     * Constructor also fires a login request
     * @param loginURL
     * @param loginAction
     * @param tocken
     * @param dataFileName
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public HTTPSoap(String loginURL, String loginAction, String tocken, String dataFileName) throws ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {

        dataFilePath = dataFilePath + dataFileName;

        sessionID = fireLoginRequest(loginURL,loginAction,tocken);
        PropertyHandler.setPropValue(dataFilePath,"sessionID",sessionID);
    }

    /**
     * Constructor without login request
     * @param dataFileName
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public HTTPSoap(String dataFileName) {

        dataFilePath = dataFilePath + dataFileName;

    }

    private String fireLoginRequest(String loginURL, String loginAction, String token) throws ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {

        String sessionToken = null;
        try {

            loginURL = PropertyHandler.getPropValue(dataFilePath,loginURL);
            loginAction = PropertyHandler.getPropValue(dataFilePath,loginAction);
            token = PropertyHandler.getPropValue(dataFilePath,token);

            String fileName = reqPath + "\\login.xml";
            String response = resPath + "\\loginRes.xml";

            fireRequest(loginURL,loginAction,token,fileName,response);

            sessionToken = getNodeValue("//sessionId",response);

        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sessionToken;

    }

    /**
     * Sends a Http request created from the given sample to the specified endpoint URL and saves the response to the response File.
     * @param endPointURL
     * @param action
     * @param sessionID
     * @param sampleFileName : Not read from datasheet
     * @param resFilename : Not read from datasheet
     * @param nodeList : text file name for the list of nodes to change
     * @param nodeValues : should be given in the order given in the node list
     * @return
     * @author A-7681
     */
    public HTTPSoap sendRequest(String endPointURL, String action, String sessionID, String sampleFileName, String resFilename, String nodeList, String... nodeValues){

        endPointURL = PropertyHandler.getPropValue(dataFilePath,endPointURL);
        action = PropertyHandler.getPropValue(dataFilePath,action);
        sessionID = PropertyHandler.getPropValue(dataFilePath,sessionID);

        sampleFileName = reqPath + "\\" + sampleFileName;
        resFilename = resPath + "\\" + resFilename;
        nodeList = reqPath + "\\" + nodeList;

        for (int i = 0; i<nodeValues.length; i++){
            nodeValues[i] = PropertyHandler.getPropValue(dataFilePath,nodeValues[i]);
        }


        String reqFile = null;

        try {
            reqFile = setNodeValue(sampleFileName,nodeList,nodeValues);
            fireRequest(endPointURL,action,sessionID,reqFile,resFilename);

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
        } catch (SOAPException e) {
            e.printStackTrace();
        }


        return this;
    }

    private void fireRequest(String endPointURL, String action, String sessionID, String reqFile, String resFilename) throws IOException, SOAPException {

        String xml;
        xml = new String(Files.readAllBytes(Paths.get(reqFile))).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage(new MimeHeaders(),new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction",action);
        headers.addHeader("icargo-identitytoken",sessionID);
        soapMessage.saveChanges();
        System.out.println("Request:");
        soapMessage.writeTo(System.out);
        System.out.println("\n\n\n\n");

        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        SOAPMessage resMessage = soapConnection.call(soapMessage,endPointURL);

        System.out.println("Response:");
        FileOutputStream fout = new FileOutputStream(resFilename);
        resMessage.writeTo(fout);
        fout.flush();
        soapConnection.close();
    }

    /**
     * Verifies different nodes in the response file
     * @param resFilename : Not read from datasheet
     * @param nodeList : text file name for the list of nodes to change
     * @param nodeValues : should be given in the order given in the node list
     * @return
     * @author A-7681
     */
    public HTTPSoap verifyResponse(String resFilename, String nodeList, String... nodeValues){

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

}
