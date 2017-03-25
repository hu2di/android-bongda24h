package vn.edu.hust.dinhhuyhung.bongda24h;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Dinh Huy Hung on 5/16/2015.
 */
public class RssParser {
    public List<RssItem> getNewsList(String link) {
       try {
           URL url = new URL(link);
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser parser = factory.newSAXParser();
           XMLReader reader = parser.getXMLReader();
           RssHandler handler = new RssHandler();
           reader.setContentHandler(handler);
           InputSource source = new InputSource(url.openStream());
           reader.parse(source);
           //Log.d("myLog", " Handler: " + handler.getItemList().size());
           return handler.getItemList();
       } catch (Exception e) {
           //Log.d("myLog", "Parser: " + e.getMessage());
           e.printStackTrace();
           return null;
       }
    }
}
