package com.CNSI.OctopusPlayer.ui.tools;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.CNSI.OctopusPlayer.MainActivity;
import com.CNSI.OctopusPlayer.R;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    Context mContext;
    EditText editText;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = container.getContext();
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((MainActivity) MainActivity.mainactivity).SetVisibleMenu(false);

        Button save_btn = (Button) root.findViewById(R.id.tools_save_btn);

        editText = (EditText) root.findViewById(R.id.server_EditText);

        String serverip = com.CNSI.OctopusPlayer.PreferenceManager.getString(mContext,"ServerIP"); // 자체 디비

        if(!serverip.equals("")){
            editText.setText(serverip);
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.CNSI.OctopusPlayer.PreferenceManager.setString(mContext,"ServerIP",editText.getText().toString()); // 새로 생성된 JSON 데이터 저장
                Toast.makeText(mContext, "설정 정보가 저장되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}