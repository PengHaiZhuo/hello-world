package com.wp.jiajia.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wp.jiajia.activity.R;
import com.wp.jiajia.model.AttrList;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 新增放行条-选择物品window listview的填充器
 */
public class RightSideslipLayChildAdapter extends SimpleBaseAdapter<AttrList.Attr.Vals> {

    private List<AttrList.Attr.Vals> seachData;
    //购买数量
    private int amount = 1;
    private SlidLayFrameChildCallBack slidLayFrameChildCallBack;

    public RightSideslipLayChildAdapter(Context context, List<AttrList.Attr.Vals> data,List<AttrList.Attr.Vals> selectVals) {
        super(context, data);
        this.seachData = selectVals;
    }

    public static List<AttrList.Attr.Vals> removegood(List<AttrList.Attr.Vals> list_goods) {
        if (list_goods!=null&&list_goods.size() > 1) {
            for (int i = 0; i < list_goods.size(); i++) {
                for (int j = list_goods.size() - 1; j > i; j--) {
                    if (list_goods.get(j).getV().equals(list_goods.get(i).getV())) {
                        list_goods.remove(j);
                    }
                }
            }
        }
        return list_goods;
    }

    public void setSeachData(List<AttrList.Attr.Vals> seachData) {
        this.seachData = seachData;
    }

    @Override
    public int getItemResource() {
        return R.layout.gv_right_sideslip_child_layout;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        ImageView item_img = holder.getView(R.id.item_img);
        TextView item_text = holder.getView(R.id.item_text);
        final LinearLayout item_layout = holder.getView(R.id.item_layout);

        final EditText etAmount = holder.getView(R.id.etAmount);
        Button btnDecrease = holder.getView(R.id.btnDecrease);
        Button btnIncrease = holder.getView(R.id.btnIncrease);

        final AttrList.Attr.Vals vals = getData().get(position);
        try {
            R.drawable d = new R.drawable();
            Field fieldimgId = d.getClass().getDeclaredField("release_s" + vals.getImg());
            //这个ID就是每个图片资源ID
            int imgId = (Integer) fieldimgId.get(d);
            item_img.setImageResource(imgId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        item_text.setText(vals.getV());
        item_layout.setTag(position);
        item_layout.setSelected(vals.isChick());

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.valueOf(etAmount.getText().toString());
                if (amount > 1) {
                    amount--;
                    etAmount.setText(amount + "");
                }
            }
        });
        btnIncrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                amount = Integer.valueOf(etAmount.getText().toString());
                if (amount < 100) {
                    amount++;
                    etAmount.setText(amount + "");
                }
            }
        });
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())){
                    etAmount.setText("1");
                }else {
                    amount = Integer.valueOf(s.toString());
                }
                if (amount > 100) {
                    etAmount.setText(100 + "");
                    return;
                }
                if (item_layout.isSelected()) {
                    seachData.remove(vals);
                    vals.setNum("*" + amount);
                    seachData.add(vals);
                    slidLayFrameChildCallBack.CallBackSelectData(removegood(seachData));
                }
            }
        });


        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AttrList.Attr.Vals vals = getData().get(position);
                if (null == vals.getNum()) {
                    vals.setNum("*" + etAmount.getText().toString());
                }
                if (!vals.isChick()) {
                    vals.setChick(true);
                    item_layout.setSelected(true);
                    seachData.add(vals);
                } else {
                    vals.setChick(false);
                    item_layout.setSelected(false);
                    seachData.remove(vals);
                }
                notifyDataSetChanged();
                slidLayFrameChildCallBack.CallBackSelectData(removegood(seachData));
            }
        });
        return convertView;
    }

    public void setSlidLayFrameChildCallBack(SlidLayFrameChildCallBack slidLayFrameChildCallBack) {
        this.slidLayFrameChildCallBack = slidLayFrameChildCallBack;
    }

    public interface SlidLayFrameChildCallBack {
        void CallBackSelectData(List<AttrList.Attr.Vals> seachData);
    }

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }


}