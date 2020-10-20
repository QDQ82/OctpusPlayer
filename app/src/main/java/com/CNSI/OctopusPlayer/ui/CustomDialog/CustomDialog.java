package com.CNSI.OctopusPlayer.ui.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.CNSI.OctopusPlayer.R;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.CNSI.OctopusPlayer.ui.home.HomeFragment;


public class CustomDialog extends Dialog {

    private Context context;
    String type;
    ListViewItem item;
    Spinner camera_spinner;


    public CustomDialog(Context _context, ListViewItem _item, String _type)
    {
        // git
        super(_context);
        type = _type;
        context = _context;
        item = _item;
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.

        setContentView(R.layout.custom_dialog);     //다이얼로그에서 사용할 레이아웃입니다.

        camera_spinner = (Spinner) this.findViewById(R.id.edit_camtype);

        String[] str = context.getResources().getStringArray(R.array.cameratype);

        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(),R.layout.spinner_layout,str);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        camera_spinner.setAdapter(adapter);

        final Button okbtn = (Button) this.findViewById(R.id.okButton);
        final Button cancelbtn = (Button) this.findViewById(R.id.cancelButton);
        final EditText camname = (EditText) this.findViewById(R.id.edit_camname);
        final EditText ip = (EditText)this.findViewById(R.id.edit_ip);
        final EditText userid = (EditText)this.findViewById(R.id.edit_userid);
        final EditText userpw = (EditText)this.findViewById(R.id.edit_userpw);
        final EditText rtspport = (EditText)this.findViewById(R.id.edit_rtsp);
        final EditText webport = (EditText)this.findViewById(R.id.edit_web);
        final EditText profile = (EditText)this.findViewById(R.id.edit_profile);
        final EditText rtspurl = (EditText)this.findViewById(R.id.edit_rtspurl);

        if(type.equals("edit"))
        {
            camname.setText(item.getTitle());
            ip.setText(item.getUrl());
            userid.setText(item.getID());
            userpw.setText(item.getPW());
            rtspport.setText(item.getRtspport());
            webport.setText(item.getWebport());
            profile.setText(item.getProfile());
            rtspurl.setText(item.getRtspurl());

            if(item.getCameratype().equals("Hanwha"))
            {
                camera_spinner.setSelection(0);
            }else if(item.getCameratype().equals("D-Max")){
                camera_spinner.setSelection(1);
            }else if(item.getCameratype().equals("Hikvision")){
                camera_spinner.setSelection(2);
            }

        }else{
            camname.setText(null);
            ip.setText(null);
            userid.setText(null);
            userpw.setText(null);
            rtspport.setText("554");
            webport.setText("80");
            profile.setText("/profile2/media.smp");
            rtspurl.setText(null);
            camera_spinner.setSelection(0);
        }


        okbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(camname.getText().toString().length() == 0 )
                {
                    Toast.makeText(context, "카메라 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(ip.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "카메라 IP 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(ip.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "카메라 IP 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(userid.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "사용자 ID를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(userpw.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "사용자 PW를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(rtspport.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "RTSP PORT를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(webport.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "WEB PORT를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(profile.getText().toString().length() == 0)
                {
                    Toast.makeText(context, "카메라 Profile을 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }

                if(type.equals("add"))
                {
                    ((HomeFragment)HomeFragment.mHomeFragment).AddCamList(camname.getText().toString(), rtspurl.getText().toString(), ip.getText().toString(), userid.getText().toString(), userpw.getText().toString(), rtspport.getText().toString(), webport.getText().toString(), profile.getText().toString(),camera_spinner.getSelectedItem().toString());
                    Toast.makeText(context, camname.getText().toString() +  "을 추가하였습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(type.equals("edit"))
                {
                    item.setTitle(camname.getText().toString());
                    item.setRtspurl(rtspurl.getText().toString());
                    item.setUrl(ip.getText().toString());
                    item.setID(userid.getText().toString());
                    item.setPW(userpw.getText().toString());
                    item.setRTSPPort(rtspport.getText().toString());
                    item.setWebport(webport.getText().toString());
                    item.setProfile(profile.getText().toString());
                    item.setCameratype(camera_spinner.getSelectedItem().toString());

                    ((HomeFragment)HomeFragment.mHomeFragment).RefreshListView();
                    Toast.makeText(context, camname.getText().toString() +  "가 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                }

                dismiss();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dismiss();
            }
        });
    }


}


