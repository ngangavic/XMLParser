package com.example.xmlparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BibleActivity extends AppCompatActivity {

   // TextView textViewBible;
    ListView listView;
    List<Chapter> chapters = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible);
        listView = findViewById(R.id.listView);
        //textViewBible = findViewById(R.id.textViewBible);
        dom();
    }


    private void xmlParser(){
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("esv.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
        
    }

    private void processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Chapter> chapterArrayList = new ArrayList<>();
        int eventType = parser.getEventType();
        Chapter currentPlayer = null;
        //parser.require(XmlPullParser.START_TAG, ns, "feed");

        while (eventType != XmlPullParser.END_DOCUMENT) {


            while (eventType !=XmlPullParser.END_TAG){
                while (eventType !=XmlPullParser.END_TAG){
                    String eltName = parser.getName();
                    if ("VERS".equals(eltName)) {
                      //  currentPlayer = new Chapter();
                        chapterArrayList.add(currentPlayer);
                    } else if (currentPlayer != null) {
                        if ("VERS".equals(eltName)) {
                            currentPlayer.verse = parser.nextText();
                        }
                    }
                }
            }



//            switch (eventType) {
//                case XmlPullParser.START_TAG:
//                    eltName = parser.getName();
//                    if ("VERS".equals(eltName)) {
//                        currentPlayer = new Chapter();
//                        chapterArrayList.add(currentPlayer);
//                    } else if (currentPlayer != null) {
//                        if ("VERS".equals(eltName)) {
//                            currentPlayer.verse = parser.nextText();
//                        }
//                    }
//                    break;
//            }

            eventType = parser.next();
        }

        printPlayers(chapterArrayList);

    }

    private void printPlayers(ArrayList<Chapter> chapters) {
        StringBuilder builder = new StringBuilder();

        for (Chapter chapter : chapters) {
            builder.append(chapter.verse).append("\n");
        }

       // textViewBible.setText(builder.toString());
    }


    private void dom(){
        try{
            ///XMLDOMParser parser = new XMLDOMParser();
            InputStream inputStream = getAssets().open("esv.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName("CHAPTER");
            Log.d("NODE LENGTH: ", String.valueOf(nodeList.getLength()));
            //NodeList nodeList1 = document.getElementsByTagName("VERS");
            //NodeList nodeList1 = nodeList;

            chapters = new ArrayList<Chapter>();

            for (int i=0;i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                //Node node1 = nodeList1.item(i);
               // node.getNodeName().equals("")
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                   // if (node.getNodeName().equals("VERS")){
                    Element element2 = (Element) node;
                  //  Element element3 = (Element) node1;
                    //if (element2.getNodeName().equals("VERS")) {
                        String verse = element2.getElementsByTagName("VERS").item(0).getTextContent();
                        //Chapter chapter = new Chapter(getValue("VERS", element2));
                        Chapter chapter = new Chapter(verse);

//chapters.add(getValue("VERS",element2));
                        // listView.setOnItemClickListener(this);

//                   textViewBible.setText(String.format("%s\n%s%s\n", textViewBible.getText(),
//                            element2.getAttribute("cnumber"),
//                            getValue("VERS", element2)));
                        chapters.add(chapter);
                   // }
               }

            }
            ArrayAdapter<Chapter> adapter = new ArrayAdapter<>(this,
                    R.layout.verse_row, chapters);
            listView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
