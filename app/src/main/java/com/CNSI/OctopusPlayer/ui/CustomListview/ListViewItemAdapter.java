package com.CNSI.OctopusPlayer.ui.CustomListview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.CNSI.OctopusPlayer.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewItemAdapter extends BaseAdapter {

    String stream = "";

    private ArrayList<ListViewItem> listViewitemList = new ArrayList<ListViewItem>();

    private JSONArray listViewitemList_json;

    public ListViewItemAdapter(){ }

    @Override
    public int getCount() {
        return listViewitemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewitemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos  = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);

        }
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView urlTextView = (TextView) convertView.findViewById((R.id.textView2));

        ListViewItem listViewItem = listViewitemList.get(position);

        Drawable icon = context.getResources().getDrawable(R.drawable.cctv_2);
        iconImageView.setImageDrawable(icon);
        titleTextView.setText(listViewItem.getTitle());
        if(listViewItem.getRtspurl().contains("rtsp")){
            stream = listViewItem.getRtspurl();
        }else{
            stream = "rtsp://" + listViewItem.getID() + ":" + listViewItem.getPW() + "@" + listViewItem.getUrl() + ":" + listViewItem.getRtspport() + listViewItem.getProfile();
        }

        urlTextView.setText(stream);

        return convertView;
    }

    public boolean addItem(String _title, String _rtspurl, String _url, String _id, String _pw, String _rtsp, String _web, String _profile, String _cameratype)  // 카메라 아이템 추가
    {

        for(int i = 0; i < listViewitemList.size(); i++)
        {
            if(_title.equals(listViewitemList.get(i).getTitle())){
                return false;
            }
        }

        ListViewItem item = new ListViewItem();

        //item.setIcon(_icon);
        item.setTitle(_title);
        item.setUrl(_url);
        item.setID(_id);
        item.setPW(_pw);
        item.setRTSPPort(_rtsp);
        item.setWebport(_web);
        item.setProfile(_profile);
        item.setCameratype(_cameratype);
        item.setRtspurl(_rtspurl);

        //stream = "rtsp://" + _id + ":" + _pw + "@" + _url + ":" + _rtsp + _profile;
        listViewitemList.add(item);

        DataUpdate();

        return true;
    }

    public void DataUpdate()  // 카메라 아이템이 추가 될 때 JSON 다시 생성
    {
        try
        {
            listViewitemList_json = new JSONArray();
            JSONObject sObject;
            if(listViewitemList.size() > 0)
            {
                for(int i = 0; i < listViewitemList.size(); i++)
                {
                    sObject = new JSONObject();
                    sObject.put("cam",listViewitemList.get(i).getTitle());
                    sObject.put("rtspurl",listViewitemList.get(i).getRtspurl());
                    sObject.put("url",listViewitemList.get(i).getUrl());
                    sObject.put("id",listViewitemList.get(i).getID());
                    sObject.put("pw",listViewitemList.get(i).getPW());
                    sObject.put("rtsp",listViewitemList.get(i).getRtspport());
                    sObject.put("web",listViewitemList.get(i).getWebport());
                    sObject.put("profile",listViewitemList.get(i).getProfile());
                    sObject.put("cameratype",listViewitemList.get(i).getCameratype());

                    listViewitemList_json.put(sObject);
                }
                sObject = null;
            }
        }catch (Exception e)
        {
            Log.d("MakeJson",e.toString());
        }

    }
    public JSONArray get_camlist_json()
    {
        return  listViewitemList_json;
    }
    public boolean DeleteItem(ListViewItem _item)
    {
        try
        {
            for(int i = 0; i < listViewitemList.size(); i++)
            {
                if(listViewitemList.get(i) == _item)
                {
                    listViewitemList.remove(i);
                    return true;
                }
            }
        }catch (Exception e) {
            Log.d("DataItem", e.toString());
            return false;
        }
        return false;
    }
    public void AllDeleteItem()
    {
        listViewitemList.clear();
    }


}
