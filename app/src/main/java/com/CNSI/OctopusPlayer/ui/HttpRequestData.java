package com.CNSI.OctopusPlayer.ui;

import com.CNSI.OctopusPlayer.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestData {

    public HttpRequestData(){

    }
    public String GetData(String _ip){
        try{
            URL url = new URL("http://" + _ip + "/webstream/HTML/common/api/get_data.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setReadTimeout(1000);
            http.setConnectTimeout(1000);
            http.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
            http.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            http.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            StringBuffer buffer = new StringBuffer();
            buffer.append("type").append("=").append("proxies");
            String strParams = buffer.toString();

            OutputStream os = http.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); // 출력 스트림에 출력.
            os.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
            os.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.
            if (http.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));

            // 출력물의 라인과 그 합에 대한 변수.
            String line;
            String page = "";

            // 라인을 받아와 합친다.
            while ((line = reader.readLine()) != null){
                page += line;
            }

                JSONArray jsonArray = new JSONArray(page);
                for(int i = 0; i< jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject proxy = jsonObject.getJSONObject("proxy");
                    String suffix = proxy.getString("suffix");
                    String camurl = proxy.getString("url");
                }
            ((HomeFragment)HomeFragment.mHomeFragment).SetServerData(page);
            ((HomeFragment)HomeFragment.mHomeFragment).GetCameraData();
            return page;

        }catch (Exception e){
            String a = e.toString();
        }
        return  null;
    }



}
