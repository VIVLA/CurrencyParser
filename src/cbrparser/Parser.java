package cbrparser;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
    
    public static String [][] getRates() throws Exception {

        String [][] rates;
        HashMap<String, NodeList> result = new HashMap<>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String urls = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + dateFormat.format(date);
        
        Document doc = getXMLFile(urls);
        
        NodeList nl = doc.getElementsByTagName("Valute");
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            NodeList nlChild = node.getChildNodes();
            for (int j = 0; j < nlChild.getLength(); j++) {
                if (nlChild.item(j).getNodeName().equals("CharCode"))
                result.put(nlChild.item(j).getTextContent(), nlChild);
            }
        }    
        
        rates = new String[result.size()][2];
        int k = 0;
        
        for (Map.Entry<String, NodeList> entry : result.entrySet()) {
            NodeList temp = entry.getValue();
            double value = 0;
            int nominal = 0;
            for (int i = 0; i < temp.getLength(); i++) {
                if (temp.item(i).getNodeName().equals("Value"))
                    value = Double.parseDouble(temp.item(i).getTextContent().replace(",", "."));
                if (temp.item(i).getNodeName().equals("Nominal"))
                    nominal = Integer.parseInt(temp.item(i).getTextContent());
            }
            
            double amount = value / nominal;
            rates[k][0] = entry.getKey();
            rates[k][1] = (double) (Math.round(amount * 10000)) / 10000 + " rub";
            k++;   
        }
        
        return rates;
    } 

    private static Document getXMLFile(String urls) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder().parse(new URL(urls).openStream());
    }
}
