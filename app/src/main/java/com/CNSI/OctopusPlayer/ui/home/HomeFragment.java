package com.CNSI.OctopusPlayer.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.CNSI.OctopusPlayer.MainActivity;
import com.CNSI.OctopusPlayer.R;
import com.CNSI.OctopusPlayer.ui.CustomDialog.CustomDialog_EditSelect;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItemAdapter;
import com.CNSI.OctopusPlayer.ui.HttpRequestData;
import com.CNSI.OctopusPlayer.ui.Video.VideoActivity;

import org.json.JSONArray;
import org.json.JSONObject;


public class HomeFragment extends Fragment {


    public static Context mContext;
    public static Fragment mHomeFragment;
    public ListViewItemAdapter adapter = new ListViewItemAdapter();
    private ListView listview;
    CustomDialog_EditSelect customDialog_editSelect;
    HttpRequestData httpRequestData;
    public String server_camdata;
    NetworkTask networkTask;

    public HomeFragment()
    {
        networkTask = new NetworkTask();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        setHasOptionsMenu(true);

        //networkTask.execute();

        mHomeFragment = this;

        mContext = container.getContext();

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listview = (ListView)root.findViewById(R.id.listView);

        InitCamera();

        //networkTask.execute();

        ((MainActivity) MainActivity.mainactivity).SetVisibleMenu(true);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle();
                String rtspurl = item.getRtspurl();
                String ip = item.getUrl();
                String camid = item.getID();
                String pw = item.getPW();
                String rtsp = item.getRtspport();
                String web = item.getWebport();
                String profile = item.getProfile();
                String cameratype = item.getCameratype();
                String camname = item.getTitle();

                String stream = "rtsp://" + camid + ":" + pw + "@" + ip + ":" + rtsp + profile;

                // Drawable iconDrawable = item.getIcon() ;

                Intent intent = new Intent(getActivity(), VideoActivity.class);

                //intent.putExtra("LitviewItem",item);

                if(rtspurl.contains("rtsp")){
                    intent.putExtra(VideoActivity.RTSP_URL, rtspurl);
                }else{
                    intent.putExtra(VideoActivity.RTSP_URL, stream);
                }

                intent.putExtra(VideoActivity.CAMIP,ip);
                intent.putExtra(VideoActivity.CAMWebPort,web);
                intent.putExtra(VideoActivity.CAMRtspPort,rtsp);
                intent.putExtra(VideoActivity.CAMID,camid);
                intent.putExtra(VideoActivity.CAMPW,pw);
                intent.putExtra(VideoActivity.CameraType,cameratype);
                intent.putExtra(VideoActivity.CameraName,camname);
                intent.putExtra(VideoActivity.Profile,profile);


                startActivity(intent);

                // TODO : use item data.
            }
        }) ;

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                customDialog_editSelect = new CustomDialog_EditSelect(mContext,item.getTitle(),item);

                customDialog_editSelect.show();

                customDialog_editSelect.getWindow().setGravity(Gravity.BOTTOM);

                listview.setAdapter(adapter); // 리스트 뷰에 그리기 적용
                // 이벤트 처리 종료 , 여기만 리스너 적용시키고 싶으면 true , 아니면 false
                return true;
            }
        });
        String addcam = com.CNSI.OctopusPlayer.PreferenceManager.getString(mContext,"addcam"); // 자체 디비

        if(addcam.equals(""))
        {
            Log.d("Addcam","CameraAdd");
            com.CNSI.OctopusPlayer.PreferenceManager.setString(mContext,"addcam","ok");
            // AddCamList("이동식카메라1","rtsp://jangchagun2.iptime.org:554/proxy1","223.171.67.133","admin", "Tldosdptmdk2","554" , "8000" , "/Streaming/channels/101","Hikvision");
            // AddCamList("이동식카메라2","rtsp://jangchagun2.iptime.org:554/proxy2","223.171.67.134","admin", "Tldosdptmdk2","554" , "8000" , "/Streaming/channels/101","Hikvision");
//            AddCamList("울산2","10.43.96.12","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("울산3","10.43.96.13","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("울산4","10.43.96.14","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("울산5","10.43.96.15","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("대산1","10.43.97.11","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("대산2","10.43.97.12","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("대산3","10.43.97.13","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("대산4","10.43.97.14","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//            AddCamList("대산5","10.43.97.15","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
        }else{
            Log.d("addcam",addcam);
        }


//        for(int i = 0; i < 10 ; i++){
//            AddCamList("Camera" + i ,"192.168.0.144","admin", "Tldosdptmdk2","554" , "80" , "/profile2/media.smp","Hanwha");
//        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // destroy all menu and re-call onCreateOptionsMenu
        getActivity().invalidateOptionsMenu();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings :
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("리스트를 전부 삭제하시겠습니까 ?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {        //확인 버튼을 생성하고 클릭시 동작을 구현합니다.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.AllDeleteItem();
                        RefreshListView();
                        Toast.makeText(mContext, "전체 리스트가 삭제되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {       //취소 버튼을 생성하고 클릭시 동작을 구현합니다.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //"NO" Button Click
                        //Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alert = builder.create(); //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();
                break;
            case R.id.action_settings_refresh:
                GetCameraDataExecute();
                listview.setAdapter(adapter); // 리스트 뷰에 그리기 적용
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SetServerData(String _data){
        this.server_camdata = _data;
    }

    public void GetCameraData()
    {
        try {
            String serverip = com.CNSI.OctopusPlayer.PreferenceManager.getString(mContext,"ServerIP"); // 자체 디비
            if(server_camdata != null)
            {
                JSONArray serverarr = new JSONArray(server_camdata);

                for(int i = 0; i< serverarr.length(); i++)
                {
                    JSONObject jsonObject = serverarr.getJSONObject(i);
                    JSONObject proxy = jsonObject.getJSONObject("proxy");

                    String cam = proxy.getString("suffix");
                    String url = proxy.getString("url");
                    String id = proxy.getString("user");
                    String pw = proxy.getString("pass");


                    if(url.contains("@"))
                    {
                        String[] all_url = url.split("@");

                        String[] ip_rtsp_profile = all_url[1].split(":");
                        String ip = ip_rtsp_profile[0]; //정상

                        String[] rtsp_profile = ip_rtsp_profile[1].split("/");
                        String rtsp = rtsp_profile[0]; //정상

                        String profile = "";
                        for(int j = 1; j < rtsp_profile.length ; j++){
                            profile += "/";
                            profile += rtsp_profile[j];
                        }

                        String[] rtsp_id_pw = all_url[0].split("rtsp://");
                        String[] id_pw = rtsp_id_pw[1].split(":");

                        id = id_pw[0]; //정상
                        pw = id_pw[1]; //정상

                        adapter.addItem(cam, url, ip ,id,pw,rtsp,"80",profile,"Hanwha");

                    }else {
                        String[] _split1 = url.split("rtsp://");
                        String[] _split2 = _split1[1].split(":");
                        String camip = _split2[0];
                        String[] _split3 = _split2[1].split("/");
                        String rtspport = _split3[0];
                        String[] _split4 = _split2[1].split(rtspport);
                        String profile = _split4[1];

                        String StreamingServerURL = "rtsp://" + serverip + ":" + rtspport +"/" + cam;

                        if(profile.contains("Streaming")){
                            adapter.addItem(cam, StreamingServerURL, camip ,id,pw,rtspport,"8000",profile,"Hikvision");
                        }else{
                            adapter.addItem(cam, StreamingServerURL, camip ,id, pw,rtspport,"80",profile,"Hanwha");
                        }


                    }
                }

            }
            SaveData();

        }
        catch (Exception e)
        {
            Log.d("InitCameraList",e.toString());
        }
    }

    public void InitCamera()
    {
        try{
            String CamList = com.CNSI.OctopusPlayer.PreferenceManager.getString(mContext,"CameraList"); // 자체 디비
            JSONArray jsonArray = new JSONArray(CamList);
            for(int i = 0; i< jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String cam = jsonObject.getString("cam");
                String rtspurl = jsonObject.getString("rtspurl");
                String url = jsonObject.getString("url");
                String id = jsonObject.getString("id");
                String pw = jsonObject.getString("pw");
                String rtsp = jsonObject.getString("rtsp");
                String web = jsonObject.getString("web");
                String profile = jsonObject.getString("profile");
                String cameratype = jsonObject.getString("cameratype");

                adapter.addItem(cam,rtspurl, url,id,pw,rtsp,web,profile,cameratype);
            }
            listview.setAdapter(adapter); // 리스트 뷰에 그리기 적용

        }catch (Exception e){

        }

    }

    public void RefreshListView()
    {
        SaveData();
        listview.setAdapter(adapter); // 리스트 뷰에 그리기 적용
    }
    public void SaveData()
    {
        adapter.DataUpdate();
        com.CNSI.OctopusPlayer.PreferenceManager.setString(mContext,"CameraList",adapter.get_camlist_json().toString()); // 새로 생성된 JSON 데이터 저장
    }
    public void AddCamList(String _title, String _rtspurl, String _ip, String _id, String _pw, String _rtsp, String _web, String _profile, String _cameratype)
    {
        try
        {
            adapter.addItem(_title , _rtspurl, _ip , _id , _pw , _rtsp , _web , _profile , _cameratype);        // 카메라 아이템 추가 & JSON 생성

            RefreshListView();

        }catch (Exception e){
            Log.d("AddCamList", "AddCamList:"+ e.toString());
        }

    }
    public boolean DeleteItem(ListViewItem _item)
    {
        try
        {
            adapter.DeleteItem(_item);
            RefreshListView();

            return true;
        }catch (Exception e){
            Log.d("AddCamList", "DeleteItem:"+ e.toString());
        }
        return false;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        @Override
        protected String doInBackground(Void... params) {

            httpRequestData = new HttpRequestData();
            String serverip = com.CNSI.OctopusPlayer.PreferenceManager.getString(mContext,"ServerIP"); // 자체 디비
            if(!serverip.equals("")){
                server_camdata = httpRequestData.GetData(serverip);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "서버 정보 갱신", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "서버IP 정보가 없습니다.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return server_camdata;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GetCameraData();
                }
            });

        }
    }

    public void GetCameraDataExecute()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpRequestData = new HttpRequestData();
                String serverip = com.CNSI.OctopusPlayer.PreferenceManager.getString(mContext,"ServerIP"); // 자체 디비
                if(!serverip.equals("")){
                    server_camdata = httpRequestData.GetData(serverip);
                    //
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "서버 정보 갱신", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    //

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "서버IP 정보가 없습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                // runOnUiThread를 추가하고 그 안에 UI작업을 한다.
                GetCameraData();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listview.setAdapter(adapter); // 리스트 뷰에 그리기 적용
                    }
                });

            }
        }).start();
    }

}