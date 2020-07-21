package com.CNSI.OctopusPlayer.ui.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.CNSI.OctopusPlayer.R;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItemAdapter;
import com.CNSI.OctopusPlayer.ui.Video.VideoActivity;

public class CameraListDialog extends Dialog {


    View selectview;
    ListView listView;
    ListViewItemAdapter adapter;

    public CameraListDialog(@NonNull Context context, ListViewItemAdapter _adapter) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.camlist_dialog);     //다이얼로그에서 사용할 레이아웃입니다.

        listView = (ListView)this.findViewById(R.id.listview_dialog);

        listView.setAdapter(_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle();
                String ip = item.getUrl();
                String camid = item.getID();
                String pw = item.getPW();
                String rtsp = item.getRtspport();
                String web = item.getWebport();
                String profile = item.getProfile();
                String cameratype = item.getCameratype();
                String camname = item.getTitle();

                String stream = "rtsp://" + camid + ":" + pw + "@" + ip + ":" + rtsp + profile;

                ((VideoActivity)VideoActivity.mVideoActivity).SelectPlay(item,stream,selectview);

                // TODO : use item data.
            }
        }) ;
    }

    public void SetView(View view){
        selectview = view;
    }

}
