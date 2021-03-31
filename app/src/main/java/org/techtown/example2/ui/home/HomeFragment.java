package org.techtown.example2.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.techtown.example2.HttpRequest;
import org.techtown.example2.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;


public class HomeFragment extends Fragment {

    private BluetoothSPP bt;
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


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
        String url = "http://hereitcow.ga/board/notice";
        HashMap<String, String> map = new HashMap<>();

        String result = HttpRequest.postRequest(url,map);
        TextView gongji = root.findViewById(R.id.gongji);
        gongji.setText(result);
        gongji.setSelected(true);

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