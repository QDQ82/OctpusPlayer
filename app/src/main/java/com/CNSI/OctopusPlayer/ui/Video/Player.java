package com.CNSI.OctopusPlayer.ui.Video;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player  {

    private SurfaceView mSurface;
    private SurfaceHolder holder;
    public IVLCVout vout;
    private LibVLC libvlc;
    private MediaPlayer mMediaPlayer = null;
    private Context mContext;

    public int mHeight;
    public int mWidth;

    public int playstatus = 0;
    private int m_iLogID;
    public final static int STREAMING_END = 0;
    public final static int STREAMING_PLAYING = 1;
    public final static int STREAMING_FAIL = 2;
    public final static int STREAMING_LOADING = 3;
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private IVLCVout.Callback callback;

    ArrayList<String> options = new ArrayList<String>();

    ListViewItem cameraitem;
    private MediaPlayer.EventListener mPlayerListener;

    private String name;
    private String rtspUrl;
    private String IP;
    private String WebPort;
    private String ID;
    private String Password;
    private String CamType;
    Timer tmr;
    TimerTask task;


    public Player(Context _context, IVLCVout.Callback _callback, MediaPlayer.EventListener _mPlayerListener){

        mContext = _context;

        mPlayerListener = _mPlayerListener;

        callback = _callback;

        options.add("--aout=opensles");

        options.add("--no-audio");

        options.add("--avcodec-hw=any");

        options.add("--network-caching=600");

        options.add("--avcodec-hurry-up");

        options.add("--avcodec-fast");

        options.add("--avcodec-skip-frame=0");

        //options.add("--avcodec-skip-idct=4");

        //options.add("--avcodec-skiploopfilter=4");

        options.add("--audio-time-stretch"); // time stretching

        options.add("-vvv"); // verbosity

    }



    public void rtsp_play(SurfaceView _surfaceView, ListViewItem _item, String _url, int width, int height)
    {

        this.cameraitem = _item;

        this.name = _item.getTitle();

        mSurface = _surfaceView;

        if(_item.getRtspurl().contains("rtsp")){
            rtspUrl = _item.getRtspurl();
        }else{
            rtspUrl = _url;
        }
        mWidth = width;

        mHeight = height;

        libvlc = new LibVLC(mContext, options);

        mSurface.setKeepScreenOn(true);

        holder = mSurface.getHolder();

        holder.setKeepScreenOn(true);

        mMediaPlayer = new MediaPlayer(libvlc);

        mMediaPlayer.setEventListener(mPlayerListener);

        vout = mMediaPlayer.getVLCVout();

        if (mSurface != null) {
            vout.setVideoView(mSurface);
        }
        if (mWidth != 0 && mHeight != 0) {
           vout.setWindowSize(mWidth, mHeight);
        }
        vout.addCallback(callback);
        vout.attachViews();

        Media m = new Media(libvlc, Uri.parse(rtspUrl));
        m.setHWDecoderEnabled(true, false);
        m.addOption(":network-caching=150");
        m.addOption(":clock-jitter=0");
        m.addOption(":clock-synchro=0");
        m.addOption(":fullscreen");

        //mMediaPlayer.setAspectRatio("16:9");
        //mMediaPlayer.setScale(1f);

        mMediaPlayer.setMedia(m);
        mMediaPlayer.play();

        tempTask();

        ((VideoActivity)VideoActivity.mVideoActivity).drawnotify(mSurface,3);

        playstatus = STREAMING_LOADING;

        if(_item.getCameratype().equals("Hikvision")){
            if (!HCNetSDK.getInstance().NET_DVR_Init()) {
                Log.d("Hikvision TEST", "HCNetSDK init is failed!");
            }
            m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
            if (null == m_oNetDvrDeviceInfoV30) {
                Log.d("Hikvision TEST", "HKNetDvrDeviceInfoV30 new is failed!");
            }
            // call NET_DVR_Login_v30 to login on, port 8000 as default
            int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(_item.getUrl(), 8000, _item.getID(), _item.getPW(), m_oNetDvrDeviceInfoV30);
            if (iLogID < 0) {
                Log.d("Hikvision TEST", "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            }else{
                m_iLogID = iLogID;
                _item.setHcNetSDK(HCNetSDK.getInstance());
                _item.setLoginid(iLogID);
            }
        }

    }


    public ListViewItem GetCamera(){
        return cameraitem;
    }

    public void SetViewSize(int Width, int Height)
    {
        vout.setWindowSize(Width, Height);
    }

    public void releasePlayer() {
        try {
            if(cameraitem.getCameratype().equals("Hikvision")) {
                HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID);
            }
            this.name = "";
            this.cameraitem = null;
            vout.setWindowSize(0,0);

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable()  {
                public void run() {
                    if (libvlc == null)
                        return;
                    mMediaPlayer.stop();
                    final IVLCVout vout = mMediaPlayer.getVLCVout();
                    vout.removeCallback(callback);
                    vout.detachViews();
                    holder = null;
                    libvlc.release();
                    libvlc = null;

                    ((VideoActivity)VideoActivity.mVideoActivity).drawnotify(mSurface,1);
                    playstatus = STREAMING_END;
                    m_iLogID = -1;

                    tmr.cancel();

                }
            }, 500);

        }catch (Exception e){

        }
    }
    public LibVLC getlibvlc(){return libvlc;}

    public void replay(int width, int height)
    {
        mWidth = width;

        mHeight = height;

        libvlc = new LibVLC(mContext, options);

        mSurface.setKeepScreenOn(true);

        holder = mSurface.getHolder();

        holder.setKeepScreenOn(true);

        mMediaPlayer = new MediaPlayer(libvlc);

        mMediaPlayer.setEventListener(mPlayerListener);

        vout = mMediaPlayer.getVLCVout();

        if (mSurface != null) {
            vout.setVideoView(mSurface);
        }
        if (mWidth != 0 && mHeight != 0) {
            vout.setWindowSize(mWidth, mHeight);
        }
        vout.addCallback(callback);
        vout.attachViews();

        Media m = new Media(libvlc, Uri.parse(rtspUrl));
        m.setHWDecoderEnabled(true, false);
        m.addOption(":network-caching=150");
        m.addOption(":clock-jitter=0");
        m.addOption(":clock-synchro=0");
        m.addOption(":fullscreen");

        //mMediaPlayer.setAspectRatio("16:9");
        //mMediaPlayer.setScale(1f);
        mMediaPlayer.setMedia(m);
        mMediaPlayer.play();
    }
    public SurfaceView GetSurfaceView(){
        return mSurface;
    }
    public void SetSurfaceView(SurfaceView _surfaceview){
        this.mSurface =_surfaceview;
    }

    public void tempTask() {
        task = new TimerTask() {
            int cnt = 0;
            long time;
            boolean play = false;
            @Override
            public void run() {
                time = mMediaPlayer.getTime();
                if(time <= 0){
                    cnt++;
                }else{
                    if(!play)
                    {
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                ((VideoActivity)VideoActivity.mVideoActivity).drawnotify(mSurface,0);
                                playstatus = STREAMING_PLAYING;
                            }
                        }, 0);
                    }
                    play = true;
                    cnt = 0;
                }
                if(mMediaPlayer.isPlaying()){
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            ((VideoActivity)VideoActivity.mVideoActivity).drawnotify(mSurface,0);
                            playstatus = STREAMING_PLAYING;
                        }
                    }, 0);
                    play = true;
                    cnt = 0;
                }else{
                    cnt++;
                }

                if(cnt > 30)
                {
                    Log.d("Log", "NOPLAY");
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            //Toast.makeText(mContext, "네트워크 스트림 재생에 실패하였습니다.", Toast.LENGTH_LONG).show();
                            ((VideoActivity)VideoActivity.mVideoActivity).drawnotify(mSurface,2);
                            playstatus = STREAMING_FAIL;
                        }
                    }, 0);
                    cancel();
                }
                //todo
            }
        };
        tmr = new Timer();
        tmr.schedule(task, 0, 1000);
    }

}

