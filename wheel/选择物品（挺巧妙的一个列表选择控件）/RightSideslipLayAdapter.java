package com.wp.jiajia.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wp.jiajia.activity.R;
import com.wp.jiajia.model.AttrList;
import com.wp.jiajia.ui.AutoMeasureHeightGridView;
import com.wp.jiajia.ui.OnClickListenerWrapper;

import java.util.List;

/**
 * 增放行条-选择物品window listview每个item的填充器
 */
public class RightSideslipLayAdapter extends SimpleBaseAdapter<AttrList.Attr> {

    OnClickListenerWrapper onClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            int id = v.getId();
            if (id == R.id.item_select_lay) {
                AutoMeasureHeightGridView childLv3GV = (AutoMeasureHeightGridView) v.getTag();
                int pos = (int) childLv3GV.getTag();
                AttrList.Attr itemdata = data.get(pos);
                boolean isSelect = !itemdata.isoPen();
                // 再将当前选择CB的实际状态
                itemdata.setIsoPen(isSelect);
                notifyDataSetChanged();
            }
        }
    };

    public RightSideslipLayAdapter(Context context, List<AttrList.Attr> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_right_sideslip_lay;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        TextView itemFrameTitleTv = holder.getView(R.id.item_frameTv);
        TextView itemFrameSelectTv = holder.getView(R.id.item_selectTv);

        LinearLayout layoutItem = holder.getView(R.id.item_select_lay);
        AutoMeasureHeightGridView itemFrameGv = holder.getView(R.id.item_selectGv);
        itemFrameGv.setVisibility(View.VISIBLE);
        AttrList.Attr mAttr = getData().get(position);
        itemFrameTitleTv.setText(mAttr.getKey());
        itemFrameSelectTv.setText(mAttr.getShowStr());


        if (mAttr.getVals() != null) {
            convertView.setVisibility(View.VISIBLE);
            if (mAttr.isoPen()) {
                itemFrameSelectTv.setTag(itemFrameGv);
                itemFrameTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_prodcatelist, 0);
                fillLv2CateViews(mAttr, mAttr.getVals(), itemFrameGv);
                layoutItem.setTag(itemFrameGv);
            } else {
                fillLv2CateViews(mAttr, mAttr.getVals().subList(0, 0), itemFrameGv);
                itemFrameSelectTv.setText(mAttr.getShowStr());
                itemFrameTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_prodcatelist, 0);
                layoutItem.setTag(itemFrameGv);
                itemFrameSelectTv.setVisibility(View.VISIBLE);
            }
            layoutItem.setOnClickListener(onClickListener);
        } else {
            convertView.setVisibility(View.GONE);
        }
        itemFrameGv.setTag(position);
        return convertView;
    }

    private void fillLv2CateViews(AttrList.Attr mAttr, List<AttrList.Attr.Vals> list,AutoMeasureHeightGridView childLvGV) {
        RightSideslipLayChildAdapter mChildAdapter;
        if (childLvGV.getAdapter() == null) {
            mChildAdapter = new RightSideslipLayChildAdapter(context, list, mAttr.getSelectVals());
            childLvGV.setAdapter(mChildAdapter);
        } else {
            mChildAdapter = (RightSideslipLayChildAdapter) childLvGV.getAdapter();
            mChildAdapter.setSeachData(mAttr.getSelectVals());
            mChildAdapter.replaceAll(list);
        }

        mChildAdapter.setSlidLayFrameChildCallBack(new RightSideslipLayChildAdapter.SlidLayFrameChildCallBack() {
            @Override
            public void CallBackSelectData(List<AttrList.Attr.Vals> seachData) {
                mAttr.setShowStr(setupSelectStr(seachData));
                mAttr.setSelectVals(seachData);
                notifyDataSetChanged();
            }
        });

    }

    private String setupSelectStr(List<AttrList.Attr.Vals> data) {
        StringBuilder builder = new StringBuilder();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.size() == 1) {
                    builder.append(data.get(i).getV() + data.get(i).getNum());
                } else {
                    if (i == data.size() - 1) {
                        builder.append(data.get(i).getV() + data.get(i).getNum());
                    } else {
                        builder.append(data.get(i).getV() + data.get(i).getNum() + ",");
                    }
                }
            }
            return new String(builder);
        } else {
            return "";
        }
    }
}
