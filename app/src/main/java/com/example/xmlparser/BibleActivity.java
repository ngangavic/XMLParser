package com.example.xmlparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BibleActivity extends AppCompatActivity {

    TextView textViewBible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible);
        textViewBible = findViewById(R.id.textViewBible);

        try{
            InputStream inputStream = getAssets().open("esv.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName("CHAPTER");

            for (int i=0;i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    Log.d("VNO: ",element2.getAttribute("vnumber"));
                    textViewBible.setText(textViewBible.getText()+element2.getAttribute("vnumber")+getValue("VERS",element2)+"\n");
                }
            }

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
