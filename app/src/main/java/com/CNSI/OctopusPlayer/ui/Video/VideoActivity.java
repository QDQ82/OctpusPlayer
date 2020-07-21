package com.CNSI.OctopusPlayer.ui.Video;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.CNSI.OctopusPlayer.R;
import com.CNSI.OctopusPlayer.ui.CustomDialog.CameraListDialog;
import com.CNSI.OctopusPlayer.ui.CustomDialog.VideoControlDialog;
import com.CNSI.OctopusPlayer.ui.CustomDialog.VideoControlTp;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItemAdapter;
import com.CNSI.OctopusPlayer.ui.home.HomeFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.MediaPlayer;


public class VideoActivity extends Activity implements IVLCVout.Callback {

    public final static String TAG = "VideoActivity";
    public static final String RTSP_URL = "rtspUrl";
    public static final String CAMIP = "IP";
    public static final String CAMWebPort = "WebPort";
    public static final String CAMID = "ID";
    public static final String CAMPW = "Password";
    public static final String CameraType = "CamType";
    public static final String CameraName = "CamName";

    ListViewItem camitem;
    public static Activity mVideoActivity;
    ListViewItemAdapter adapter;

    // display surface

    private SurfaceHolder holder;
    public IVLCVout vout;
    private LibVLC libvlc;
    private MediaPlayer mMediaPlayer = null;

    public int BtnView;
    private SurfaceView mSurface1;
    private SurfaceView mSurface2;
    private SurfaceView mSurface3;
    private SurfaceView mSurface4;
    private SurfaceView mSurface5;
    private SurfaceView mSurface6;
    private SurfaceView mSurface7;
    private SurfaceView mSurface8;
    private SurfaceView mSurface9;

    LinearLayout ch123,ch456,ch789;

    LinearLayout view1,view2,view3,view4,view5,view6,view7,view8,view9;

    boolean isSingle;
    boolean window1, window4, window9;



    private MediaPlayer.EventListener mPlayerListener = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener2 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener3 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener4 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener5 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener6 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener7 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener8 = new PlayerListener(this);
    private MediaPlayer.EventListener mPlayerListener9 = new PlayerListener(this);

    //Camera Info
    private String rtspUrl;
    private String IP;
    private String WebPort;
    private String ID;
    private String Password;
    private String CamType;
    private String CamName;

    boolean pausechecker;

    public int mHeight;
    public int mWidth;


    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Player player6;
    Player player7;
    Player player8;
    Player player9;

    DisplayMetrics metrics = new DisplayMetrics();

    VideoControlDialog  vd;
    VideoControlTp vdtop;
    CameraListDialog cameraListDialog;
    Button PTZLeft;
    Button PTZRight;
    Button PTZUp;
    Button PTZDown;

    private final static int NOTIFY_IMG_NONE = 0;
    private final static int NOTIFY_IMG_BASIC = 1;
    private final static int NOTIFY_IMG_FAIL = 2;
    private final static int NOTIFY_IMG_LOADING = 3;

    private int doubleClickFlag = 0;
    private final long  CLICK_DELAY = 250;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVideoActivity = this;

        player1 = new Player(getApplicationContext(),this, mPlayerListener);
        player2 = new Player(getApplicationContext(),this, mPlayerListener2);
        player3 = new Player(getApplicationContext(),this, mPlayerListener3);
        player4 = new Player(getApplicationContext(),this, mPlayerListener4);
        player5 = new Player(getApplicationContext(),this, mPlayerListener5);
        player6 = new Player(getApplicationContext(),this, mPlayerListener6);
        player7 = new Player(getApplicationContext(),this, mPlayerListener7);
        player8 = new Player(getApplicationContext(),this, mPlayerListener8);
        player9 = new Player(getApplicationContext(),this, mPlayerListener9);


        mSurface1 = (SurfaceView) findViewById(R.id.surface1);
        mSurface2 = (SurfaceView) findViewById(R.id.surface2);
        mSurface3 = (SurfaceView) findViewById(R.id.surface3);
        mSurface4 = (SurfaceView) findViewById(R.id.surface4);
        mSurface5 = (SurfaceView) findViewById(R.id.surface5);
        mSurface6 = (SurfaceView) findViewById(R.id.surface6);
        mSurface7 = (SurfaceView) findViewById(R.id.surface7);
        mSurface8 = (SurfaceView) findViewById(R.id.surface8);
        mSurface9 = (SurfaceView) findViewById(R.id.surface9);


        view1 = (LinearLayout) findViewById(R.id.view1_Linear);
        view2 = (LinearLayout) findViewById(R.id.view2_Linear);
        view3 = (LinearLayout) findViewById(R.id.view3_Linear);
        view4 = (LinearLayout) findViewById(R.id.view4_Linear);
        view5 = (LinearLayout) findViewById(R.id.view5_Linear);
        view6 = (LinearLayout) findViewById(R.id.view6_Linear);
        view7 = (LinearLayout) findViewById(R.id.view7_Linear);
        view8 = (LinearLayout) findViewById(R.id.view8_Linear);
        view9 = (LinearLayout) findViewById(R.id.view9_Linear);



        ch123 = (LinearLayout) findViewById(R.id.ch123_Linear);
        ch456 = (LinearLayout) findViewById(R.id.ch456_Linear);
        ch789 = (LinearLayout) findViewById(R.id.ch789_Linear);

        GetIntent();

        window1 = true;
        window4 = false;
        window9 = false;
        BtnView = 1;

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        player1.SetSurfaceView(mSurface1);
        player2.SetSurfaceView(mSurface2);
        player3.SetSurfaceView(mSurface3);
        player4.SetSurfaceView(mSurface4);
        player5.SetSurfaceView(mSurface5);
        player6.SetSurfaceView(mSurface6);
        player7.SetSurfaceView(mSurface7);
        player8.SetSurfaceView(mSurface8);
        player9.SetSurfaceView(mSurface9);

        if(player1.vout != null){
            player1.SetViewSize(mWidth,mHeight);
        }
        if(player2.vout != null){
            player2.SetViewSize(mWidth,mHeight);
        }
        if(player3.vout != null){
            player3.SetViewSize(mWidth,mHeight);
        }
        if(player4.vout != null){
            player4.SetViewSize(mWidth,mHeight);
        }
        if(player5.vout != null){
            player5.SetViewSize(mWidth,mHeight);
        }
        if(player6.vout != null){
            player6.SetViewSize(mWidth,mHeight);
        }
        if(player7.vout != null){
            player7.SetViewSize(mWidth,mHeight);
        }
        if(player8.vout != null){
            player8.SetViewSize(mWidth,mHeight);
        }
        if(player9.vout != null){
            player9.SetViewSize(mWidth,mHeight);
        }
        InitControlWindow();

        player1.rtsp_play(mSurface1,camitem,rtspUrl,mWidth ,mHeight);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
    }

    private void InitControlWindow() {
        cameraListDialog = new CameraListDialog(this,((HomeFragment)HomeFragment.mHomeFragment).adapter);

        InitVideoController();

        drawnotify(mSurface1,1);
        drawnotify(mSurface2,1);
        drawnotify(mSurface3,1);
        drawnotify(mSurface4,1);
        drawnotify(mSurface5,1);
        drawnotify(mSurface6,1);
        drawnotify(mSurface7,1);
        drawnotify(mSurface8,1);
        drawnotify(mSurface9,1);

        mSurface1.setOnClickListener(SurfaceClick);
        mSurface2.setOnClickListener(SurfaceClick);
        mSurface3.setOnClickListener(SurfaceClick);
        mSurface4.setOnClickListener(SurfaceClick);
        mSurface5.setOnClickListener(SurfaceClick);
        mSurface6.setOnClickListener(SurfaceClick);
        mSurface7.setOnClickListener(SurfaceClick);
        mSurface8.setOnClickListener(SurfaceClick);
        mSurface9.setOnClickListener(SurfaceClick);

        mSurface1.setOnLongClickListener(SurfaceLongClick);
        mSurface2.setOnLongClickListener(SurfaceLongClick);
        mSurface3.setOnLongClickListener(SurfaceLongClick);
        mSurface4.setOnLongClickListener(SurfaceLongClick);
        mSurface5.setOnLongClickListener(SurfaceLongClick);
        mSurface6.setOnLongClickListener(SurfaceLongClick);
        mSurface7.setOnLongClickListener(SurfaceLongClick);
        mSurface8.setOnLongClickListener(SurfaceLongClick);
        mSurface9.setOnLongClickListener(SurfaceLongClick);

    }

    View ClickView;
    View.OnClickListener SurfaceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SurfaceView surfaceView = null;

            view1.setBackground(null);
            view2.setBackground(null);
            view3.setBackground(null);
            view4.setBackground(null);
            view5.setBackground(null);
            view6.setBackground(null);
            view7.setBackground(null);
            view8.setBackground(null);
            view9.setBackground(null);


            doubleClickFlag++;

            switch (v.getId())
            {
                case R.id.surface1:
                    surfaceView = mSurface1;
                    view1.setBackground(getDrawable(R.color.selectview));
                    if(player1.getlibvlc() != null)
                    {
                        vd.SetSelectView(mSurface1,player1.cameraitem);
                        vd.show();
                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }

                    break;
                case R.id.surface2:
                    surfaceView = mSurface2;
                    view2.setBackground(getDrawable(R.color.selectview));

                    if(player2.getlibvlc() != null){
                        vd.SetSelectView(mSurface2, player2.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }

                    break;
                case R.id.surface3:
                    surfaceView = mSurface3;
                    view3.setBackground(getDrawable(R.color.selectview));
                    if(player3.getlibvlc() != null){
                        vd.SetSelectView(mSurface3, player3.cameraitem);
                        vd.show();
                    }else{
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }

                    break;
                case R.id.surface4:
                    surfaceView = mSurface4;
                    view4.setBackground(getDrawable(R.color.selectview));
                    if(player4.getlibvlc() != null){
                        vd.SetSelectView(mSurface4, player4.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }
                    break;
                case R.id.surface5:
                    surfaceView = mSurface5;
                    view5.setBackground(getDrawable(R.color.selectview));
                    if(player5.getlibvlc() != null){
                        vd.SetSelectView(mSurface5, player5.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }
                    break;
                case R.id.surface6:
                    surfaceView = mSurface6;
                    view6.setBackground(getDrawable(R.color.selectview));
                    if(player6.getlibvlc() != null){
                        vd.SetSelectView(mSurface6, player6.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }
                    break;
                case R.id.surface7:
                    surfaceView = mSurface4;
                    view7.setBackground(getDrawable(R.color.selectview));
                    if(player7.getlibvlc() != null){
                        vd.SetSelectView(mSurface7, player7.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }
                    break;
                case R.id.surface8:
                    surfaceView = mSurface8;
                    view8.setBackground(getDrawable(R.color.selectview));
                    if(player8.getlibvlc() != null){
                        vd.SetSelectView(mSurface8, player8.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }
                    break;
                case R.id.surface9:
                    surfaceView = mSurface9;
                    view9.setBackground(getDrawable(R.color.selectview));
                    if(player9.getlibvlc() != null){
                        vd.SetSelectView(mSurface9, player9.cameraitem);
                        vd.show();

                    }else {
                        removecontroler();
                        cameraListDialog.show();
                        cameraListDialog.SetView(v);
                    }
                    break;
            }


            Handler handler = new Handler();
            Runnable clickRunnable = new Runnable(){
                @Override
                public void run(){
                    if(doubleClickFlag == 1)
                    {
                        doubleClickFlag = 0;
                        // todo 클릭 이벤트
                    }
                }
            };

            if( doubleClickFlag == 1 ) {
                ClickView = v;
                handler.postDelayed( clickRunnable, CLICK_DELAY );
            }else if( doubleClickFlag == 2) {
                doubleClickFlag = 0;

                if(ClickView != v){
                    return;
                }
                if(window4 || window9){
                    ViewSelect(surfaceView,1);
                    return;
                }
                if(BtnView == 4 && window1){
                    ViewSelect(surfaceView,4);
                    return;
                }
                if(BtnView == 9 && window1){
                    ViewSelect(surfaceView,9);
                    return;
                }

                // todo 더블클릭 이벤트
            }

        }
    };

    View.OnLongClickListener SurfaceLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.surface1:
                    if(player1.getlibvlc() != null){
                        deleteDialog(player1,mSurface1);
                        //mSurface.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface2:
                    if(player2.getlibvlc() != null){
                        deleteDialog(player2,mSurface2);
                        //mSurface2.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface3:
                    if(player3.getlibvlc() != null){
                        deleteDialog(player3,mSurface3);
                        //mSurface3.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface4:
                    if(player4.getlibvlc() != null){
                        deleteDialog(player4,mSurface4);
                        //mSurface4.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface5:
                    if(player5.getlibvlc() != null){
                        deleteDialog(player5,mSurface5);
                        //mSurface4.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface6:
                    if(player6.getlibvlc() != null){
                        deleteDialog(player6,mSurface6);
                        //mSurface4.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface7:
                    if(player7.getlibvlc() != null){
                        deleteDialog(player7,mSurface7);
                        //mSurface4.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface8:
                    if(player8.getlibvlc() != null){
                        deleteDialog(player8,mSurface8);
                        //mSurface4.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
                case R.id.surface9:
                    if(player9.getlibvlc() != null){
                        deleteDialog(player9,mSurface9);
                        //mSurface4.setBackground(getDrawable(R.drawable.back));
                        return true;
                    }
                    break;
            }
            return true;
        }
    };

    private void deleteDialog(Player _player, SurfaceView _surfaceView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this,R.style.AlertDialog);
        final Player player = _player;
        final SurfaceView surfaceView = _surfaceView;
        builder.setTitle("종료");
        builder.setMessage("카메라를 종료하시겠습니까 ?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                player.releasePlayer();
                removecontroler();
                unselectview();
            }
        });

        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
        }

    public void SelectPlay(ListViewItem item, String url, View v) {
        switch (v.getId())
        {
            case R.id.surface1:
                mSurface1.setBackground(null);
                player1.rtsp_play(mSurface1,item,url,mWidth,mHeight);
                player1.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface2:
                mSurface2.setBackground(null);
                player2.rtsp_play(mSurface2,item,url,mWidth,mHeight);
                player2.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface3:
                mSurface3.setBackground(null);
                player3.rtsp_play(mSurface3,item,url,mWidth,mHeight);
                player3.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface4:
                mSurface4.setBackground(null);
                player4.rtsp_play(mSurface4,item,url,mWidth,mHeight);
                player4.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface5:
                mSurface5.setBackground(null);
                player5.rtsp_play(mSurface5,item,url,mWidth,mHeight);
                player5.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface6:
                mSurface6.setBackground(null);
                player6.rtsp_play(mSurface6,item,url,mWidth,mHeight);
                player6.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface7:
                mSurface7.setBackground(null);
                player7.rtsp_play(mSurface7,item,url,mWidth,mHeight);
                player7.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface8:
                mSurface8.setBackground(null);
                player8.rtsp_play(mSurface8,item,url,mWidth,mHeight);
                player8.SetViewSize(mWidth,mHeight);
                break;
            case R.id.surface9:
                mSurface9.setBackground(null);
                player9.rtsp_play(mSurface9,item,url,mWidth,mHeight);
                player9.SetViewSize(mWidth,mHeight);
                break;
        }
        cameraListDialog.dismiss();
    }

    public void ViewSelect(SurfaceView _surface, int view)
    {
        try{

            LinearLayout.LayoutParams ch123_layoutParams = (LinearLayout.LayoutParams) ch123.getLayoutParams();
            LinearLayout.LayoutParams ch456_layoutParams = (LinearLayout.LayoutParams) ch456.getLayoutParams();
            LinearLayout.LayoutParams ch789_layoutParams = (LinearLayout.LayoutParams) ch789.getLayoutParams();
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) view1.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) view4.getLayoutParams();
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) view5.getLayoutParams();
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) view6.getLayoutParams();
            LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) view7.getLayoutParams();
            LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) view8.getLayoutParams();
            LinearLayout.LayoutParams layoutParams9 = (LinearLayout.LayoutParams) view9.getLayoutParams();

            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            if(view == 1){
                window1 = true;
                window4 = false;
                window9 = false;
                mHeight = metrics.heightPixels;
                mWidth = metrics.widthPixels;

                if(_surface == mSurface1 || _surface == mSurface2 || _surface == mSurface3)
                {
                    ch123_layoutParams.weight = 1;
                    ch456_layoutParams.weight = 0;
                    ch789_layoutParams.weight = 0;

                    if(_surface == mSurface1)
                    {
                        layoutParams1.weight = 1;
                        layoutParams2.weight = 0;
                        layoutParams3.weight = 0;
                    }
                    if(_surface == mSurface2){
                        layoutParams1.weight = 0;
                        layoutParams2.weight = 1;
                        layoutParams3.weight = 0;
                    }
                    if(_surface == mSurface3){
                        layoutParams1.weight = 0;
                        layoutParams2.weight = 0;
                        layoutParams3.weight = 1;
                    }

                    if(_surface == mSurface1){
                        if(player1.vout != null){
                            player1.SetViewSize(mWidth,mHeight);
                        }
                    }
                    if(_surface == mSurface2){
                        if(player2.vout != null){
                            player2.SetViewSize(mWidth,mHeight);
                        }
                    }
                    if(_surface == mSurface3){
                        if(player3.vout != null){
                            player3.SetViewSize(mWidth,mHeight);
                        }
                    }
                }

                if(_surface == mSurface4 || _surface == mSurface5 || _surface == mSurface6)
                {
                    ch123_layoutParams.weight = 0;
                    ch456_layoutParams.weight = 1;
                    ch789_layoutParams.weight = 0;

                    if(_surface == mSurface4)
                    {
                        layoutParams4.weight = 1;
                        layoutParams5.weight = 0;
                        layoutParams6.weight = 0;
                    }
                    if(_surface == mSurface5){
                        layoutParams4.weight = 0;
                        layoutParams5.weight = 1;
                        layoutParams6.weight = 0;
                    }
                    if(_surface == mSurface6){
                        layoutParams4.weight = 0;
                        layoutParams5.weight = 0;
                        layoutParams6.weight = 1;
                    }

                    if(_surface == mSurface4){
                        if(player4.vout != null){
                            player4.SetViewSize(mWidth,mHeight);
                        }
                    }
                    if(_surface == mSurface5){
                        if(player5.vout != null){
                            player5.SetViewSize(mWidth,mHeight);
                        }
                    }
                    if(_surface == mSurface6){
                        if(player6.vout != null){
                            player6.SetViewSize(mWidth,mHeight);
                        }
                    }
                }

                if(_surface == mSurface7 || _surface == mSurface8 || _surface == mSurface9)
                {
                    ch123_layoutParams.weight = 0;
                    ch456_layoutParams.weight = 0;
                    ch789_layoutParams.weight = 1;

                    if(_surface == mSurface7)
                    {
                        layoutParams7.weight = 1;
                        layoutParams8.weight = 0;
                        layoutParams9.weight = 0;
                    }
                    if(_surface == mSurface8){
                        layoutParams7.weight = 0;
                        layoutParams8.weight = 1;
                        layoutParams9.weight = 0;
                    }
                    if(_surface == mSurface9){
                        layoutParams7.weight = 0;
                        layoutParams8.weight = 0;
                        layoutParams9.weight = 1;
                    }

                    if(_surface == mSurface7){
                        if(player7.vout != null){
                            player7.SetViewSize(mWidth,mHeight);
                        }
                    }
                    if(_surface == mSurface8){
                        if(player8.vout != null){
                            player8.SetViewSize(mWidth,mHeight);
                        }
                    }
                    if(_surface == mSurface9){
                        if(player9.vout != null){
                            player9.SetViewSize(mWidth,mHeight);
                        }
                    }
                }

            } // view 1

            if( view == 4){

                window1 = false;
                window4 = true;
                window9 = false;

                layoutParams1.weight = 1;
                layoutParams2.weight = 1;
                layoutParams3.weight = 0;

                layoutParams4.weight = 1;
                layoutParams5.weight = 1;
                layoutParams6.weight = 0;

                layoutParams7.weight = 0;
                layoutParams8.weight = 0;
                layoutParams9.weight = 0;

                ch123_layoutParams.weight = 1;
                ch456_layoutParams.weight = 1;
                ch789_layoutParams.weight = 0;

                mHeight = metrics.heightPixels / 2;
                mWidth = metrics.widthPixels / 2;
            }
            // view 4

            if( view == 9){

                window1 = false;
                window4 = false;
                window9 = true;

                layoutParams1.weight = 1;
                layoutParams2.weight = 1;
                layoutParams3.weight = 1;

                layoutParams4.weight = 1;
                layoutParams5.weight = 1;
                layoutParams6.weight = 1;

                layoutParams7.weight = 1;
                layoutParams8.weight = 1;
                layoutParams9.weight = 1;

                ch123_layoutParams.weight = 1;
                ch456_layoutParams.weight = 1;
                ch789_layoutParams.weight = 1;

                mHeight = metrics.heightPixels / 3;
                mWidth = metrics.widthPixels / 3;
            }
            // view 9

            if(view == 4 || view == 9)
            {
                if(player1.vout != null){
                    player1.SetViewSize(mWidth,mHeight);
                }
                if(player2.vout != null){
                    player2.SetViewSize(mWidth,mHeight);
                }
                if(player3.vout != null){
                    player3.SetViewSize(mWidth,mHeight);
                }
                if(player4.vout != null){
                    player4.SetViewSize(mWidth,mHeight);
                }
                if(player5.vout != null){
                    player5.SetViewSize(mWidth,mHeight);
                }
                if(player6.vout != null){
                    player6.SetViewSize(mWidth,mHeight);
                }
                if(player7.vout != null){
                    player7.SetViewSize(mWidth,mHeight);
                }
                if(player8.vout != null){
                    player8.SetViewSize(mWidth,mHeight);
                }
                if(player9.vout != null){
                    player9.SetViewSize(mWidth,mHeight);
                }
            }

            ch123.setLayoutParams(ch123_layoutParams);
            ch456.setLayoutParams(ch456_layoutParams);
            ch789.setLayoutParams(ch789_layoutParams);
            view1.setLayoutParams(layoutParams1);
            view2.setLayoutParams(layoutParams2);
            view3.setLayoutParams(layoutParams3);
            view4.setLayoutParams(layoutParams4);
            view5.setLayoutParams(layoutParams5);
            view6.setLayoutParams(layoutParams6);
            view7.setLayoutParams(layoutParams7);
            view8.setLayoutParams(layoutParams8);
            view9.setLayoutParams(layoutParams9);

        }catch (Exception e){

        }

    }

    private void GetIntent(){
        // Get URL

        Intent intent = getIntent();
        camitem = (ListViewItem) intent.getSerializableExtra("LitviewItem");

        rtspUrl = intent.getExtras().getString(RTSP_URL);
        IP = camitem.getUrl();
        WebPort = camitem.getWebport();
        ID = camitem.getID();
        Password = camitem.getPW();
        CamType = camitem.getCameratype();
        CamName = camitem.getTitle();
    }

    public void PTZEvent(final String Command, final ListViewItem item) {
        new Thread(){
            @Override
            public void run() {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                try {
                    String CGIQuery = "";

                    if(item.getCameratype().equals("Hanwha"))
                    {
                        // 인증
                        httpclient.getCredentialsProvider().setCredentials(new AuthScope(item.getUrl(), Integer.parseInt(item.getWebport())), new UsernamePasswordCredentials(item.getID(), item.getPW()));

                        switch (Command) {
                            case "Left":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=continuous&action=control&Pan=-2&Duration=10";
                                break;
                            case "Right":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=continuous&action=control&Pan=2&Duration=10";
                                break;
                            case "Up":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=continuous&action=control&Tilt=2&Duration=10";
                                break;
                            case "Down":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=continuous&action=control&Tilt=-2&Duration=10";
                                break;
                            case "ZoomIN":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=continuous&action=control&Zoom=1";
                                break;
                            case "ZoomOut":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=continuous&action=control&Zoom=-1";
                                break;
                            case "Stop":
                                CGIQuery = "/stw-cgi/ptzcontrol.cgi?msubmenu=stop&action=control&OperationType=All";
                                break;
                            default:
                                return;
                        }
                    }else if(item.getCameratype().equals("D-Max"))
                    {
                        switch (Command) {
                            case "Left":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x00000103&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            case "Right":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x00000105&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            case "Up":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x00000107&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            case "Down":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x00000101&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            case "ZoomIN":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x0000010B&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            case "ZoomOut":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x0000010C&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            case "Stop":
                                CGIQuery = "/cgi-bin/fwptzctr.cgi?FwModId=0&PortId=0&PtzCode=0x00000200&PtzParm=0x0000000A&RcvData=NO&FwCgiVer=0x0001";
                                break;
                            default:
                                return;
                        }
                    }

                    HttpGet httpget = new HttpGet("http://"+item.getUrl()+":"+item.getWebport()+CGIQuery);

                    HttpResponse response = httpclient.execute(httpget);

                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        //System.out.println("Response content length: " + entity.getContentLength());
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }finally {
                    // When HttpClient instance is no longer needed,
                    // shut down the connection manager to ensure
                    // immediate deallocation of all system resources
                    httpclient.getConnectionManager().shutdown();
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(pausechecker){
            if(player1.getlibvlc() != null){
                player1.replay(mWidth,mHeight);
            }
            if(player2.getlibvlc() != null){
                player2.replay(mWidth,mHeight);
            }
            if(player3.getlibvlc() != null){
                player3.replay(mWidth,mHeight);
            }
            if(player4.getlibvlc() != null){
                player4.replay(mWidth,mHeight);
            }
            if(player5.getlibvlc() != null){
                player5.replay(mWidth,mHeight);
            }
            if(player6.getlibvlc() != null){
                player6.replay(mWidth,mHeight);
            }
            if(player7.getlibvlc() != null){
                player7.replay(mWidth,mHeight);
            }
            if(player8.getlibvlc() != null){
                player8.replay(mWidth,mHeight);
            }
            if(player9.getlibvlc() != null){
                player9.replay(mWidth,mHeight);
            }
            pausechecker = false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        pausechecker = true;
        //releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void InitVideoController()
    {
        vd = new VideoControlDialog(this);
        vd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        vd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        vd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        vd.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        vd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        vd.getWindow().setGravity(Gravity.BOTTOM);

        vd.SetView(window1);
        vd.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                    vd.showtop();
            }
        });
        vd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                unselectview();
            }
        });
    }

    // 가로 세로 변경
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if(window1){
            mHeight = metrics.heightPixels;
            mWidth = metrics.widthPixels;
        }
        if(window4){
            mHeight = metrics.heightPixels / 2;
            mWidth = metrics.widthPixels / 2;
        }
        if(window9){
            mHeight = metrics.heightPixels / 3;
            mWidth = metrics.widthPixels / 3;
        }

        if(player1.vout != null){
            player1.SetViewSize(mWidth,mHeight);
        }
        if(player2.vout != null){
            player2.SetViewSize(mWidth,mHeight);
        }
        if(player3.vout != null){
            player3.SetViewSize(mWidth,mHeight);
        }
        if(player4.vout != null){
            player4.SetViewSize(mWidth,mHeight);
        }
        if(player5.vout != null){
            player5.SetViewSize(mWidth,mHeight);
        }
        if(player6.vout != null){
            player6.SetViewSize(mWidth,mHeight);
        }
        if(player7.vout != null){
            player7.SetViewSize(mWidth,mHeight);
        }
        if(player8.vout != null){
            player8.SetViewSize(mWidth,mHeight);
        }
        if(player9.vout != null){
            player9.SetViewSize(mWidth,mHeight);
        }

        playstatus_draw(player1);
        playstatus_draw(player2);
        playstatus_draw(player3);
        playstatus_draw(player4);
        playstatus_draw(player5);
        playstatus_draw(player6);
        playstatus_draw(player7);
        playstatus_draw(player8);
        playstatus_draw(player9);

        removecontroler();

        InitVideoController();

    }


    public void playstatus_draw(Player _player){
        if(_player.playstatus == _player.STREAMING_END){
            drawnotify(_player.GetSurfaceView(), 1);
        }
        if(_player.playstatus == _player.STREAMING_FAIL){
            drawnotify(_player.GetSurfaceView(), 2);
        }
        if(_player.playstatus == _player.STREAMING_LOADING){
            drawnotify(_player.GetSurfaceView(), 3);
        }
    }

    public void releasePlayer() {
        if (libvlc == null)
            return;
        mMediaPlayer.stop();
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.removeCallback(this);
        vout.detachViews();
        holder = null;
        libvlc.release();
        libvlc = null;
    }

    public void unselectview(){
        view1.setBackground(null);
        view2.setBackground(null);
        view3.setBackground(null);
        view4.setBackground(null);
        view5.setBackground(null);
        view6.setBackground(null);
        view7.setBackground(null);
        view8.setBackground(null);
        view9.setBackground(null);
    }

    public void drawnotify(SurfaceView _surfaceview, int noti){
        if(getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90)
        {
            switch (noti){
                case NOTIFY_IMG_NONE:
                    _surfaceview.setBackground(null);
                    break;
                case NOTIFY_IMG_BASIC:
                    _surfaceview.setBackground(getDrawable(R.drawable.landback));
                    break;
                case NOTIFY_IMG_FAIL:
                    _surfaceview.setBackground(getDrawable(R.drawable.netfail_land));
                    break;
                case NOTIFY_IMG_LOADING:
                    _surfaceview.setBackground(getDrawable(R.drawable.loading_land));

            }
            //가로 모드 일 때
        }else{
            switch (noti){
                case NOTIFY_IMG_NONE:
                    _surfaceview.setBackground(null);
                    break;
                case NOTIFY_IMG_BASIC:
                    _surfaceview.setBackground(getDrawable(R.drawable.back));
                    break;
                case NOTIFY_IMG_FAIL:
                    _surfaceview.setBackground(getDrawable(R.drawable.netfail));
                    break;
                case NOTIFY_IMG_LOADING:
                    _surfaceview.setBackground(getDrawable(R.drawable.loading2));

            }
            //세로 모드 일 때
        }
    }

    public void removecontroler()
    {
        vd.removetop();
        vd.dismiss();
        unselectview();
    }




}