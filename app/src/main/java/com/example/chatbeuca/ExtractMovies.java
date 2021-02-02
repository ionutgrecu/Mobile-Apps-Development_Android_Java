package com.example.chatbeuca;

import android.os.AsyncTask;

import com.example.chatbeuca.database.model.Movie;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractMovies extends AsyncTask<URL, Void, InputStream> {

    InputStream ist = null;

    public static List<Movie> listaFilme;

    public static String sbuf = null;

    @Override
    protected InputStream doInBackground(URL... urls) {

        HttpURLConnection conn = null;

        try{
            conn = (HttpURLConnection)urls[0].openConnection();
            conn.setRequestMethod("GET");
            ist = conn.getInputStream();

            listaFilme = Parsing(ist);

            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = null;
            while ((linie = br.readLine())!=null)
            {
                sbuf +=linie;
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    public List<Movie> Parsing(InputStream isr)
    {
        List<Movie> lista = new ArrayList<Movie>();

        try {
            //creare parser care genereaza obiecte DOM din fisiere XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //creare instanta DOM din fisier XML
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parsare
            Document domDoc = db.parse(isr);
            //structurare continut fisier
            domDoc.getDocumentElement().normalize();

            //accesare nod cu ratele de schimb
            Node parinte = getNodeByName("Movies", domDoc.getDocumentElement());
            if (parinte != null) {

                //parcurgere lista de fii (tag-urile Rate)
                NodeList listaCopiiParinte = parinte.getChildNodes();
                for (int j = 0; j < listaCopiiParinte.getLength(); j++) {

                    Node copil = listaCopiiParinte.item(j);

                    if (copil != null && copil.getNodeName().equals("Movie")) {
                        Movie movie = new Movie();

                        NodeList taguri = copil.getChildNodes();

                        for (int i = 0; i < taguri.getLength(); i++) {
                            //extrag fiecare nod din lista
                            Node node = taguri.item(i);
                            //extrag atribut nod
                            String attribute = getAttributeValue(node, "atribut");

                            if (attribute.equals("Titlu"))
                                movie.setTitle(node.getTextContent());

                            if (attribute.equals("Data"))
                                movie.setData(new Date(node.getTextContent()));

                            if (attribute.equals("Regizor"))
                                movie.setRegizor(node.getTextContent());

                            if (attribute.equals("Profit"))
                                movie.setProfit(Integer.parseInt(node.getTextContent()));

                            if (attribute.equals("GenFilm"))
                                //movie.setGenFilm(Gen.valueOf(node.getTextContent()));
                                movie.setGenFilm(node.getTextContent());

                            if (attribute.equals("Platforma"))
                                //movie.setPlatforma(Platforma.valueOf(node.getTextContent()));
                                movie.setPlatforma(node.getTextContent());
                        }

                        lista.add(movie);
                    }


                }
            }
            return lista;
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

        return null;
    }
}
