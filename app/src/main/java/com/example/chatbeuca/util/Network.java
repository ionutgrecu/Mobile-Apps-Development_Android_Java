package com.example.chatbeuca.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.chatbeuca.database.model.CursValutar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Network extends AsyncTask<URL, Void, InputStream> {

    InputStream ist = null;

    public CursValutar cv;

    public static String sbuf;

    @Override
    protected InputStream doInBackground(URL... urls) {

        HttpURLConnection conn = null;
        try
        {
            conn = (HttpURLConnection)urls[0].openConnection();
            conn.setRequestMethod("GET");
            ist = conn.getInputStream();

            Parsing(ist);

            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = "";
            while((linie = br.readLine())!=null)
            {
                sbuf+=linie + "\n";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (conn!=null)
                conn.disconnect();
        }
        return ist;
    }

    public static Node getNodeByName(String nodeName, Node parentNode) throws Exception {

        if (parentNode.getNodeName().equals(nodeName)) {
            return parentNode;
        }

        NodeList list = parentNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            Node node = getNodeByName(nodeName, list.item(i));
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    public static String getAttributeValue(Node node, String attrName) {
        try {
            return ((Element)node).getAttribute(attrName);
        }
        catch (Exception e) {
            return "";
        }
    }

    public void Parsing(InputStream isr)
    {
        try {
            //creare parser care genereaza obiecte DOM din fisiere XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //creare instanta DOM din fisier XML
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parsare
            Document domDoc = db.parse(isr);
            //structurare continut fisier
            domDoc.getDocumentElement().normalize();

            cv = new CursValutar();

            //accesare nod cu ratele de schimb
            Node cube = getNodeByName("Cube", domDoc.getDocumentElement());
            if (cube != null) {
                String data = getAttributeValue(cube, "date");

                cv.setData(data);

                //parcurgere lista de fii (tag-urile Rate)
                NodeList childList = cube.getChildNodes();

                for (int i = 0; i < childList.getLength(); i++) {
                    //extrag fiecare nod din lista
                    Node node = childList.item(i);
                    //extrag atribut nod
                    String attribute = getAttributeValue(node, "currency");

                    if(attribute.equals("EUR"))
                        cv.setEuro(node.getTextContent());

                    if(attribute.equals("GBP"))
                        cv.setGbp(node.getTextContent());

                    if(attribute.equals("USD"))
                        cv.setDolar(node.getTextContent());

                    if(attribute.equals("XAU"))
                        cv.setAur(node.getTextContent());
                }
            }
            else Log.e("eroare","Eroare parsare! Nodul Cube este null!");
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
        catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
