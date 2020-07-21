package com.CNSI.OctopusPlayer.ui.CustomDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.CNSI.OctopusPlayer.R;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.CNSI.OctopusPlayer.ui.Video.VideoActivity;


public class VideoControlDialog extends Dialog {

    Button ptzleft;
    Button ptzright;
    Button ptzup;
    Button ptzdown;

    Button Quadview_btn;
    Button singleview_btn;
    Button Nineview_btn;

    Button ptzin;
    Button ptzout;
    TextView tp;
    LinearLayout bot;
    TextView mainview;
    LinearLayout videocontrol_main;
    SurfaceView selectview;
    View innerView;
    String Title;
    ListViewItem cameraitem;

    Context mcontext;
    VideoControlTp vdtop;
    public VideoControlDialog(@NonNull Context context) {

        super(context);
        mcontext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.videocontroldialog_layout);     //다이얼로그에서 사용할 레이아웃입니다.

        innerView = getLayoutInflater().inflate(R.layout.videocontroldialog_layout, null);

        //tp = (TextView) this.findViewById(R.id.vc_toppanel);
        bot = (LinearLayout) this.findViewById(R.id.vc_botpanel);

        //mainview = (TextView) this.findViewById(R.id.textview_main);

        Quadview_btn = (Button) this.findViewById(R.id.multi_view);
        singleview_btn = (Button) this.findViewById(R.id.single_view);
        Nineview_btn = (Button) this.findViewById(R.id.nine_view);

        //videocontrol_main = (LinearLayout) this.findViewById(R.id.videocontrol_main);

        ptzleft = (Button) this.findViewById(R.id.ptz_left);
        ptzleft.setOnTouchListener(buttonTouch);

        ptzright = (Button) this.findViewById(R.id.ptz_right);
        ptzright.setOnTouchListener(buttonTouch);

        ptzup = (Button) this.findViewById(R.id.ptz_up);
        ptzup.setOnTouchListener(buttonTouch);

        ptzdown = (Button) this.findViewById(R.id.ptz_down);
        ptzdown.setOnTouchListener(buttonTouch);

        ptzin = (Button) this.findViewById(R.id.ptz_zoomin);
        ptzin.setOnTouchListener(buttonTouch);

        ptzout = (Button) this.findViewById(R.id.ptz_zoomout);
        ptzout.setOnTouchListener(buttonTouch);

        vdtop = new VideoControlTp(mcontext);

        vdtop.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        vdtop.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        vdtop.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        vdtop.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        vdtop.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        vdtop.getWindow().setGravity(Gravity.TOP);


        singleview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    singleview_btn.setBackgroundResource(R.drawable.ch1_click);
                    Quadview_btn.setBackgroundResource(R.drawable.ch4_def);
                    Nineview_btn.setBackgroundResource(R.drawable.ch9_def);

                    ((VideoActivity)VideoActivity.mVideoActivity).ViewSelect(selectview,1);
                    ((VideoActivity)VideoActivity.mVideoActivity).BtnView = 1;
                }catch (Exception e){

                }
            }
        });

        Quadview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Quadview_btn.setBackgroundResource(R.drawable.ch4_click);
                    singleview_btn.setBackgroundResource(R.drawable.ch1_def);
                    Nineview_btn.setBackgroundResource(R.drawable.ch9_def);

                    ((VideoActivity)VideoActivity.mVideoActivity).ViewSelect(null,4);
                    ((VideoActivity)VideoActivity.mVideoActivity).BtnView = 4;
                }catch (Exception e){

                }

            }
        });
        Nineview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Nineview_btn.setBackgroundResource(R.drawable.ch9_click);
                    singleview_btn.setBackgroundResource(R.drawable.ch1_def);
                    Quadview_btn.setBackgroundResource(R.drawable.ch4_def);

                    ((VideoActivity)VideoActivity.mVideoActivity).ViewSelect(null,9);
                    ((VideoActivity)VideoActivity.mVideoActivity).BtnView = 9;
                }catch (Exception e){

                }

            }
        });

        vdtop.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                    dismiss();
            }
        });

    }
    @Override protected void onCreate(Bundle savedInstanceState)
    {

    }

    public void showtop(){
        vdtop.SetTitle(cameraitem.getTitle());
        vdtop.show();
    }
    public void removetop(){
        vdtop.dismiss();
    }

    public void SetView(boolean type){
        if(type){
            singleview_btn.setBackgroundResource(R.drawable.ch1_click);
            Quadview_btn.setBackgroundResource(R.drawable.ch4_def);
        }else{
            Quadview_btn.setBackgroundResource(R.drawable.ch4_click);
            singleview_btn.setBackgroundResource(R.drawable.ch1_def);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener buttonTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.ptz_left:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ptzleft.setBackgroundResource(R.drawable.image_btn_ptz_left_click);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Left",cameraitem);
                            break;
                        case MotionEvent.ACTION_UP:
                            ptzleft.setBackgroundResource(R.drawable.image_btn_ptz_left_def);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Stop",cameraitem);
                            break;
                    }
                    break;
                case R.id.ptz_right:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ptzright.setBackgroundResource(R.drawable.image_btn_ptz_right_click);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Right",cameraitem);
                            break;
                        case MotionEvent.ACTION_UP:
                            ptzright.setBackgroundResource(R.drawable.image_btn_ptz_right_def);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Stop",cameraitem);
                            break;
                    }
                    break;
                case R.id.ptz_down:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ptzdown.setBackgroundResource(R.drawable.image_btn_ptz_down_click);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Down",cameraitem);
                            break;
                        case MotionEvent.ACTION_UP:
                            ptzdown.setBackgroundResource(R.drawable.image_btn_ptz_down_def);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Stop",cameraitem);
                            break;
                    }
                    break;
                case R.id.ptz_up:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ptzup.setBackgroundResource(R.drawable.image_btn_ptz_up_click);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Up",cameraitem);
                            break;
                        case MotionEvent.ACTION_UP:
                            ptzup.setBackgroundResource(R.drawable.image_btn_ptz_up_def);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Stop",cameraitem);
                            break;
                    }
                    break;
                case R.id.ptz_zoomin:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ptzin.setBackgroundResource(R.drawable.image_btn_ptz_zoom_in_click);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("ZoomIN",cameraitem);
                            break;
                        case MotionEvent.ACTION_UP:
                            ptzin.setBackgroundResource(R.drawable.image_btn_ptz_zoom_in_def);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Stop",cameraitem);
                            break;
                    }
                    break;
                case R.id.ptz_zoomout:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ptzout.setBackgroundResource(R.drawable.image_btn_ptz_zoom_out_click);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("ZoomOut",cameraitem);
                            break;
                        case MotionEvent.ACTION_UP:
                            ptzout.setBackgroundResource(R.drawable.image_btn_ptz_zoom_out_def);
                            ((VideoActivity)VideoActivity.mVideoActivity).PTZEvent("Stop",cameraitem);
                            break;
                    }
                    break;

            }
            return true;
        }
    };

    public void SetSelectView(SurfaceView _surfaceview, ListViewItem item)
    {
        this.selectview = _surfaceview;

        if(item != null)
        {
            this.cameraitem = item;
            //tp.setText(cameraitem.getTitle());
            vdtop.SetTitle(cameraitem.getTitle());
        }else{
            //tp.setText("");
            vdtop.SetTitle("");
        }

    }
}
