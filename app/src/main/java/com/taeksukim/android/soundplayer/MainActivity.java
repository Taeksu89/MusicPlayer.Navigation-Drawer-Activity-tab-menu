package com.taeksukim.android.soundplayer;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;




import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.taeksukim.android.soundplayer.util.fragment.PagerAdapter;


import static com.taeksukim.android.soundplayer.ListFragment.TYPE_ARTIST;
import static com.taeksukim.android.soundplayer.ListFragment.TYPE_SONG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQ_PERMISSION = 100; // 권한요청코드

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

    }

    private void init(){
        //화면의 툴바 가져오기
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //타이틀 색깔 변경.
        toolbar.setTitleTextColor(Color.YELLOW);
        setSupportActionBar(toolbar);

        // 플로팅 버튼 설정
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShuffle();
            }
        });


        // 네비 드로워 설정
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 컨텐츠 영역
        //1 탭 레이아웃
        // 탭 Layout 정의

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);


        // 탭 생성 및 타이틀 입력
        tabLayout.addTab( tabLayout.newTab().setText(
                getResources().getString( R.string.menu_title )) // "Title" -> values/strings.xml > 값을 세팅
        );
        tabLayout.addTab( tabLayout.newTab().setText( getResources().getString(R.string.menu_artist)) );
        tabLayout.addTab( tabLayout.newTab().setText( getResources().getString(R.string.menu_album)) );
        tabLayout.addTab( tabLayout.newTab().setText( getResources().getString(R.string.menu_genre)) );




        //2 뷰페이저저
        // 프래그먼트 페이저 작성
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        //아답터 설정 필요
        // 프래그먼트 초기화
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        adapter.add(ListFragment.newInstance(1, TYPE_SONG));
        adapter.add(ListFragment.newInstance(3, TYPE_ARTIST));
        adapter.add(new ThreeFragment());
        adapter.add(new FourFragment());

        viewPager.setAdapter(adapter);

        //1. 페이저 리스너 : 페이저가 변경되었을 때 탭을 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


    }

    //리스트 섞기
    public void setShuffle(){
        //To do
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // 툴바 우측 상단 메뉴 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //툴바 우측 상단 메뉴 onClick
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "setting is selected!!", Toast.LENGTH_SHORT).show();
            return true;

            case R.id.action_mylist :
                Toast.makeText(this, "mylist is selected!!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_search :
                Toast.makeText(this, "search is selected!!", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //네비게이션 드로어 메뉴가 Onclick되면 호출
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_title) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.menu_artist) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.menu_album) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.menu_genre) {
            viewPager.setCurrentItem(3);
        } else if (id == R.id.nav_mylist) {

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 권한관리
    private void checkPermission() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if( PermissionControl.checkPermission(this, REQ_PERMISSION) ){
                init();
            }
        }else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            if( PermissionControl.onCheckResult(grantResults)){
                init();
            }else{
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
