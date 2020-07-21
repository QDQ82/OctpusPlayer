package com.CNSI.OctopusPlayer.ui.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.CNSI.OctopusPlayer.R;





public class VideoControlTp extends Dialog {
    TextView tp;

    public VideoControlTp(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.videocontroltop_layout);     //다이얼로그에서 사용할 레이아웃입니다.

        tp = (TextView) this.findViewById(R.id.vc_toppanel);

    }

    public void SetTitle(String Title){
        tp.setText(Title);
    }
}
