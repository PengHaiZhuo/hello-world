activity_releaseadd.xml
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <android.support.v4.widget.DrawerLayout
        android:id="@+id/drawer_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <FrameLayout
            android:id="@+id/main_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true">
            <ScrollView
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

            </ScrollView>
        </FrameLayout>

        <LinearLayout
            android:id="@+id/nav_view"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="end"
            android:fitsSystemWindows="true"
            android:orientation="vertical" />

    </android.support.v4.widget.DrawerLayout>
</LinearLayout>

使用

private LinearLayout navigationView;
private DrawerLayout drawer;
private Context mContext;
private RightSideslipLay menuHeaderView;

navigationView = (LinearLayout) findViewById(R.id.nav_view);
drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
menuHeaderView = new RightSideslipLay(mContext);
navigationView.addView(menuHeaderView);



//打开自定义的RightSideslipLay
drawer.openDrawer(GravityCompat.END);

menuHeaderView.setCloseMenuCallBack(new RightSideslipLay.CloseMenuCallBack() {
            @Override
            public void setupCloseMean() {
		//关闭自定义viewRightSideslipLay
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        menuHeaderView.setdataCallBack(new RightSideslipLay.DataCallBack() {
            @Override
            public void setupData(List<AttrList.Attr.Vals> da) {
                //返回了选中的数据，自己操作吧
            }

        });