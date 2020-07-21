package com.CNSI.OctopusPlayer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.CNSI.OctopusPlayer.ui.CustomDialog.CustomDialog;
import com.CNSI.OctopusPlayer.ui.HttpRequestData;
import com.CNSI.OctopusPlayer.ui.home.HomeFragment;
import com.CNSI.OctopusPlayer.ui.share.ShareFragment;
import com.CNSI.OctopusPlayer.ui.tools.ToolsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
// Version 1.0 2020/04/01
public class MainActivity extends AppCompatActivity{

    boolean menu_visible;
    private AppBarConfiguration mAppBarConfiguration;
    CustomDialog cd;
    FloatingActionButton fab;
    public static Activity mainactivity;
    Fragment home;
    ToolsFragment tool;
    ShareFragment info;
    HttpRequestData httpRequestData;
    String server_camdata;

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장

    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        mainactivity = this;
        menu_visible = true;
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        cd = new CustomDialog(this, null, "add");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd.show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,R.id.nav_tools, R.id.nav_share).setDrawerLayout(drawer).build();

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                ((HomeFragment)HomeFragment.mHomeFragment).GetCameraDataExecute();
            }
        }, 2000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        for(int i = 0; i < menu.size(); i++){
            if(menu_visible){
                menu.getItem(i).setVisible(true);
            }else{
                menu.getItem(i).setVisible(false);
            }

        }

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            //finish();
            moveTaskToBack(true);						// 태스크를 백그라운드로 이동
            finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }
    public void SetVisibleMenu(boolean b){
        if(b){
            fab.show();
            menu_visible = true;
        }else{
            fab.hide();
            menu_visible = false;
        }

    }


}
