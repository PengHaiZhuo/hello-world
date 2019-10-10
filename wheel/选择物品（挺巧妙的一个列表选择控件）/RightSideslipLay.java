package com.wp.jiajia.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.wp.jiajia.activity.R;
import com.wp.jiajia.adapter.RightSideslipLayAdapter;
import com.wp.jiajia.model.AttrList;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加放行条-选择物品弹出框布局
 */
public class RightSideslipLay extends RelativeLayout {
    public List<AttrList.Attr.Vals> listdata;
    public DataCallBack dataCallBack;
    private Context mCtx;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private RelativeLayout mRelateLay;
    private RightSideslipLayAdapter slidLayFrameAdapter;
    private String JsonStr = "{\"attr\": [{ \"isoPen\": true,\"single_check\": 0,\"key\": \"大家电\", \"vals\": [ { \"val\": \"冰箱\", \"img\": 1}, {\"val\": \"电视机\", \"img\": 2 }, {\"val\": \"洗衣机\", \"img\": 3 },{\"val\": \"音响\", \"img\": 4 }, {\"val\": \"空调\", \"img\": 5 },{\"val\": \"橱柜\", \"img\": 6 }, {\"val\": \"消毒柜\", \"img\":7 },{\"val\": \"冷柜\", \"img\": 8 }, {\"val\": \"灶具\", \"img\": 9 }]}," +
            "{\"single_check\": 0,\"key\": \"小家电\", \"vals\": [{ \"val\": \"电扇\", \"img\": 10},{ \"val\": \"电磁炉\", \"img\": 11},{ \"val\": \"电饭煲\", \"img\": 12},{ \"val\": \"微波炉\", \"img\": 13},{ \"val\": \"热水器\", \"img\": 14},{ \"val\": \"电烤箱\", \"img\": 15},{ \"val\": \"投影仪\", \"img\": 16},{ \"val\": \"电脑\", \"img\": 17}]}," +
            "{\"single_check\": 0,\"key\": \"家具\", \"vals\": [{ \"val\": \"床\", \"img\": 18},{ \"val\": \"沙发\", \"img\": 19} ,{ \"val\": \"衣柜\", \"img\": 20},{ \"val\": \"茶几\", \"img\": 21},{ \"val\": \"桌\", \"img\": 22},{ \"val\": \"书柜\", \"img\": 23},{ \"val\": \"鞋柜\", \"img\": 24}]}," +
            "{\"single_check\": 0,\"key\": \"其他\", \"vals\": [{ \"val\": \"封包箱子\", \"img\": 25}]}]}";
    private AttrList attr;
    /**
     * 创建PopupWindow
     */
    private CloseMenuCallBack menuCallBack;
    private OnClickListenerWrapper mOnClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.fram_reset_but:
                    resetAttr();
                    listdata.clear();
                    dataCallBack.setupData(listdata);
                    break;
                case R.id.fram_ok_but:
                    menuCallBack.setupCloseMean();
                    listdata.clear();
                    for (int i = 0; i < slidLayFrameAdapter.getData().size(); i++) {
                        AttrList.Attr mAttr = slidLayFrameAdapter.getData().get(i);
                        listdata.addAll(mAttr.getSelectVals());
                    }
                    dataCallBack.setupData(listdata);
                    break;
            }
        }
    };

    public RightSideslipLay(Context context) {
        super(context);
        mCtx = context;
        listdata = new ArrayList<>();
        inflateView();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);
        selectList = findViewById(R.id.selsectFrameLV);
        resetBrand = findViewById(R.id.fram_reset_but);
        mRelateLay = findViewById(R.id.select_frame_lay);
        okBrand = findViewById(R.id.fram_ok_but);
        resetBrand.setOnClickListener(mOnClickListener);
        okBrand.setOnClickListener(mOnClickListener);
        mRelateLay.setOnClickListener(mOnClickListener);
        setUpList();
    }

    /**
     * 点击重置按钮，不变化打开状态，只变化选中状态
     */
    private void resetAttr() {
        if (attr.getAttr() == null) {
            return;
        }
        for (int i = 0; i < attr.getAttr().size(); i++) {
            if (attr.getAttr().get(i).getSelectVals() != null) {
                attr.getAttr().get(i).getSelectVals().clear();
            }
            attr.getAttr().get(i).setShowStr("");
            for (AttrList.Attr.Vals vals : attr.getAttr().get(i).getVals()) {
                vals.setChick(false);
            }
        }
        slidLayFrameAdapter = new RightSideslipLayAdapter(mCtx, attr.getAttr());
        selectList.setAdapter(slidLayFrameAdapter);
    }

    private void setUpList() {
        attr = new Gson().fromJson(JsonStr.toString(), AttrList.class);
        if (attr.getAttr() == null) {
            return;
        }
        for (int i = 0; i < attr.getAttr().size(); i++) {
            if (attr.getAttr().get(i).getSelectVals() == null) {
                attr.getAttr().get(i).setSelectVals(new ArrayList<AttrList.Attr.Vals>());
            }
        }
        if (slidLayFrameAdapter == null) {
            slidLayFrameAdapter = new RightSideslipLayAdapter(mCtx, attr.getAttr());
            selectList.setAdapter(slidLayFrameAdapter);
        } else {
            slidLayFrameAdapter.replaceAll(attr.getAttr());
        }
    }

    public void setCloseMenuCallBack(CloseMenuCallBack menuCallBack) {
        this.menuCallBack = menuCallBack;
    }

    public void setdataCallBack(DataCallBack m) {
        dataCallBack = m;
    }

    public interface CloseMenuCallBack {
        void setupCloseMean();
    }

    public interface DataCallBack {
        void setupData(List<AttrList.Attr.Vals> da);
    }
}
