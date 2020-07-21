package com.CNSI.OctopusPlayer.ui.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.CNSI.OctopusPlayer.R;
import com.CNSI.OctopusPlayer.ui.CustomListview.ListViewItem;
import com.CNSI.OctopusPlayer.ui.home.HomeFragment;

public class CustomDialog_EditSelect extends Dialog {

    CustomDialog customDialog;
    Context mContext;
    String title;
    ListViewItem item;
    public CustomDialog_EditSelect(@NonNull Context context, String _title, ListViewItem _item) {

        super(context);
        item = _item;
        title = _title;
        mContext = context;


        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        setContentView(R.layout.cutom_dialog_edit);     //다이얼로그에서 사용할 레이아웃입니다.

        final Button editButton = (Button) this.findViewById(R.id.camnameButton);
        final Button delButton = (Button) this.findViewById(R.id.deleteButton);

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                customDialog = new CustomDialog(mContext,item,"edit");
                customDialog.show();
                dismiss();
            }
        });


        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("해당 카메라를 삭제하시겠습니까 ?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {        //확인 버튼을 생성하고 클릭시 동작을 구현합니다.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //"YES" Button Click
                        if(((HomeFragment)HomeFragment.mHomeFragment).DeleteItem(item)){
                            Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                        }

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

                dismiss();
            }
        });

    }
}
