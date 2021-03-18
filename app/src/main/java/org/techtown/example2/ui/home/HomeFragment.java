package org.techtown.example2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.techtown.example2.MainActivity;
import org.techtown.example2.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView textView;
    TextView textView2;
    String str;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        int count = 1;
        textView2 = root.findViewById(R.id.infor);
        try{
            String url = "http://data.ex.co.kr/openapi/burstInfo/realTimeSms?key=1931974769&type=xml&numOfRows=12&pageNo=3&sortType=desc&pagingYn=Y";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);
            String info = "";
            while(true)
            {
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("realTimeSMSList");
                        for(int temp = 0; temp <nList.getLength(); temp++)
                        {
                            Node nNode = nList.item(temp);
                            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                                Element eElement = (Element) nNode;
                                info += getTagValue("accPointNM",eElement);
                                info += "  ";
                                info += getTagValue("roadNM",eElement);
                                info += "  ";
                                info += getTagValue("startEndTypeCode",eElement);
                                info += "  ";
                                info += getTagValue("smsText",eElement);
                                info += "  ";
                                info += getTagValue("accDate",eElement);
                                info += "  ";
                                info += getTagValue("accHour",eElement);
                                info += "\n";
                                info += "\n";
                                //textView2.setText(getTagValue("accPointNM",eElement) + " " +getTagValue("roadNM",eElement) + " " +getTagValue("startEndTypeCode",eElement) + " " +getTagValue("smsText",eElement));
                            }

                        }

                            textView2.setText(info);
                        break;


            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return root;
    }
    private static String getTagValue(String tag, Element eElement){
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node)nlList.item(0);
        if(nValue == null)
        {
            return null;
        }
        return nValue.getNodeValue();
    }

}