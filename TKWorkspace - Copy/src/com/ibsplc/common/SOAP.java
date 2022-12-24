package com.ibsplc.common;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;

/**
 * Created by A-7681 on 5/18/2018.
 */
public abstract class SOAP {

    protected String  setNodeValue(String sampleDocName, String nodeList, String [] nodeValues)
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException, TransformerFactoryConfigurationError,
            TransformerException {

        String fileName = sampleDocName.split("\\.")[0];
        try {

            String xml;
            Node node = null;
//            xml = new String(Files.readAllBytes(Paths.get(sampleDocName)));
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            Document doc = builder.parse(sampleDocName);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            File nodeListFile = new File(nodeList);
            BufferedReader br = new BufferedReader(new FileReader(nodeListFile));

            String line = null;
            int i = 0;

            while ((line = br.readLine()) != null){
                node = (Node) xpath.evaluate(line,doc, XPathConstants.NODE);
                node.setTextContent(nodeValues[i++]);
            }

            DOMSource source = new DOMSource(doc);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();

            fileName = fileName + "JMSReq.xml";

            FileWriter fw = new FileWriter(fileName);

            StreamResult result = new StreamResult(fw);

            transformer.transform(source,result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    protected String getNodeValue(String xpathValue, String xmlDoc)
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException, TransformerFactoryConfigurationError,
            TransformerException {
        String tkn = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlDoc);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            Node node = (Node) xpath.evaluate(xpathValue, doc,
                    XPathConstants.NODE);
            tkn =  node.getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The Token is: "+tkn);
        return tkn;
    }

}
