package org.techtown.example2.ui.gallery;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.techtown.example2.HttpRequest;
import org.techtown.example2.R;

import java.util.HashMap;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        EditText title = (EditText) root.findViewById(R.id.editTextTextPersonName6);
        EditText content = (EditText) root.findViewById(R.id.editTextTextMultiLine3);

        Button submit = (Button) root.findViewById(R.id.button4);


        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://hereitcow.ga/board/write";
                HashMap<String, String> map = new HashMap<>();

                map.put("title",title.getText().toString());
                map.put("content",content.getText().toString());

                String result = HttpRequest.postRequest(url,map);
            }
        });


        return root;
    }
}