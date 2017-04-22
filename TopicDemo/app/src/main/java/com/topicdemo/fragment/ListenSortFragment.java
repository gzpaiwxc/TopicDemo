package com.topicdemo.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topicdemo.R;
import com.topicdemo.entity.OptionAnswer;
import com.topicdemo.entity.OptionTab;
import com.topicdemo.entity.PictureItem;
import com.topicdemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListenSortFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "topicdemo";

    /**题目图片，选项，选项卡*/
    private GridView gridViewPic,gridViewOption, gridViewOptionTab;
    /**图片*/
    private RelativeLayout rlPicture;
    /**答案*/
    private RelativeLayout rlOption;
    /**选项可下沉的框*/
    private RelativeLayout rlSelectOption;
    /**下沉按钮*/
    private ImageView imgSink;

    /**
     * 选项框是否下沉
     */
    private boolean isSink = true;

    /**图片的适配器*/
    private PictureGridAdapter pictureGridAdapter;
    /**选项答案适配器*/
    private OptionAdapter optionAdapter;
    /**答题卡选项适配器*/
    private OptionTabAdapter optionTabAdapter;

    private String[] letterOptions = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private String[] numOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    /**选项卡的每个选项*/
    private List<OptionTab> optionTabs = new ArrayList<>();

    /**答案选项*/
    private List<OptionAnswer> optionAnswers = new ArrayList<>();

    /**题目中的图片*/
    private List<PictureItem> pictureItems = new ArrayList<>();


    public ListenSortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listen_sort, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        gridViewPic = (GridView) view.findViewById(R.id.pic_grid_view);
        pictureGridAdapter = new PictureGridAdapter(this.getActivity(), getPictureList());
        gridViewPic.setAdapter(pictureGridAdapter);
//        gridViewPic.setOnItemClickListener(this);

        gridViewOption = (GridView) view.findViewById(R.id.option_grid_view);
        optionAdapter = new OptionAdapter(this.getActivity(), getOptionList());
        gridViewOption.setAdapter(optionAdapter);
        gridViewOption.setOnItemClickListener(optionAnswerListener);

        gridViewOptionTab = (GridView) view.findViewById(R.id.grid_option_tab);
        optionTabAdapter = new OptionTabAdapter(this.getActivity(), getOptionTabList());
        gridViewOptionTab.setAdapter(optionTabAdapter);
        gridViewOptionTab.setOnItemClickListener(optionTabListener);

        rlPicture = (RelativeLayout) view.findViewById(R.id.rl_pic);
        rlOption = (RelativeLayout) view.findViewById(R.id.rl_option);
        rlSelectOption = (RelativeLayout) view.findViewById(R.id.rl_option_select);
        imgSink = (ImageView) view.findViewById(R.id.img_sink);
        imgSink.setOnClickListener(this);
        if (!isSink) {
            rlSelectOption.setVisibility(View.VISIBLE);
        }
    }

    private List<OptionTab> getOptionTabList() {
        optionTabs.clear();
        for (int i=0;i<4;i++) {
            OptionTab optionTab = new OptionTab();
            optionTab.option = letterOptions[i];
            optionTabs.add(optionTab);
        }
        return optionTabs;
    }

    /**获取选项的数据*/
    private List<OptionAnswer> getOptionList() {
        optionAnswers.clear();
        for (int i=0;i<4;i++) {
            OptionAnswer optionAnswer = new OptionAnswer();
            optionAnswer.setNumOption(numOptions[i]);
            optionAnswer.setOptionAnswer("");
            optionAnswers.add(optionAnswer);
        }
        return optionAnswers;
    }

    /**获取题目图片*/
    private List<PictureItem> getPictureList() {
        pictureItems.clear();
        for (int i=0;i<4;i++) {
            PictureItem pictureItem = new PictureItem();
            pictureItem.picture = R.mipmap.ic_launcher;
            pictureItem.letterPotion = letterOptions[i];
            pictureItems.add(pictureItem);
        }
        return pictureItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_sink:
                if (!isSink) {
                    rlSelectOption.setVisibility(View.GONE);
                    for (int i=0;i<optionAnswers.size();i++) {
                        optionAnswers.get(i).setClick(false);
                    }
                    optionAdapter.upDateList(optionAnswers);
                    isSink = true;
                }
                break;
        }
    }


    /**答案选项点击事件*/
    public AdapterView.OnItemClickListener optionAnswerListener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            view.setSelected(true);

            if (isSink) {//如果选项框是下沉的，则点击了答案框后，弹出选项框
                rlSelectOption.setVisibility(View.VISIBLE);
                isSink = false;
            }
                for (int i = 0; i < optionAnswers.size(); i++) {
                    if (i == position) {
                        optionAnswers.get(i).setClick(true);
                    } else {
                        optionAnswers.get(i).setClick(false);
                    }
                }
                optionAdapter.upDateList(optionAnswers);

        }
    };

    private List<Integer> optionClicks = new ArrayList<>();
    /**选项框点击事件*/
    public AdapterView.OnItemClickListener optionTabListener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ToastUtils.showToast(getActivity(),"position="+position);
            OptionTab optionTab = optionTabs.get(position);
            if (optionTab.isClick) {//当选项框的选项未选中时
                Log.d(TAG, "isClick=true");
                for (int i=0;i<optionTabs.size();i++) {
                    if (optionAnswers.get(i).isClick()&&optionAnswers.get(i).getOptionAnswer().length()!=0) {//答案框被选中并且答案框里已填入答案
                        if (TextUtils.equals(optionTab.option, optionAnswers.get(i).getOptionAnswer())) {//答案框里的答案与所点击的选项框的答案是否一致，一致的话就清掉答案框里的内容
                            optionAnswers.get(i).setOptionAnswer("");
                            optionTab.isClick = false;//选项框取消选中
                        }}
//                    } else if (optionAnswers.get(i).isClick() && optionAnswers.get(i).getOptionAnswer().length() == 0) {//
//                        Log.d(TAG, "啥时候走了这一步？");
//                        optionAnswers.get(i).setOptionAnswer("");
//                        optionTab.isClick = true;
//                    }
                }
            } else {//当选项框的选项选中时
                Log.d(TAG, "isClick=false");
                for (int i=0;i<optionTabs.size();i++) {
                    if (optionAnswers.get(i).isClick()&&optionAnswers.get(i).getOptionAnswer().length()==0) {//当答案框被选中并且答案框里没有填入答案时
                        optionTab.isClick = true;//选项框的选项被选中
                        optionAnswers.get(i).setOptionAnswer(letterOptions[position]);//将选项框的答案填入答案框
                        if (i < optionTabs.size()-1) {//填完一个答案后下一个答案框被选中
                            optionAnswers.get(i).setClick(false);
                            optionAnswers.get(i+1).setClick(true);
                            break;
                        }
//                        if (TextUtils.equals(optionAnswers.get(i).getOptionAnswer(), optionTab.option)) {
//                            optionTabs.get(i).isClick = false;
//                        }
                    }
                }
            }
            optionAdapter.upDateList(optionAnswers);
            optionTabAdapter.upDateList(optionTabs);

        }
    };


    public static class PictureGridAdapter extends BaseAdapter {
        private Context mContext;
        private List<PictureItem> mDatas;
        public PictureGridAdapter(Context context, List<PictureItem> datas) {
            this.mContext = context;
            mDatas = datas;
        }
        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_picture, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.picture.setImageResource(mDatas.get(position).picture);
            holder.tvNumOption.setText(mDatas.get(position).letterPotion);
            return convertView;
        }

        private class ViewHolder {
            private TextView tvNumOption;
            private ImageView picture;

            public ViewHolder(View view) {
                tvNumOption = (TextView) view.findViewById(R.id.num_option);
                picture = (ImageView) view.findViewById(R.id.img_option);
            }
        }
    }

    public static class OptionAdapter extends BaseAdapter{
        private Context mContext;
        private List<OptionAnswer> mDatas;
        private boolean isFirst;

        public OptionAdapter(Context context, List<OptionAnswer> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }
        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }
        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_option, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == (mDatas.size() - 1)) {
                holder.tvArrows.setVisibility(View.GONE);
            }
            holder.tvNumOption.setText(mDatas.get(position).getNumOption());
            OptionAnswer optionAnswer = mDatas.get(position);
            if (optionAnswer.isClick()) {
                holder.tvOption.setBackgroundResource(R.drawable.btn_aqua_round_10);
            } else {
                holder.tvOption.setBackgroundResource(R.drawable.btn_white_round_10);
            }
            holder.tvOption.setText(mDatas.get(position).getOptionAnswer());
            if (holder.tvOption.getText().length() != 0 && !mDatas.get(position).isClick()) {
                holder.tvOption.setBackgroundResource(R.drawable.btn_blue_round_10);
            }
            return convertView;
        }

        public void upDateList(List<OptionAnswer> optionAnswers) {
            this.mDatas = optionAnswers;
            notifyDataSetChanged();
        }


        public static class ViewHolder {
            //选项序号
            private TextView tvNumOption;
            //选项框里填的text
            private TextView tvOption;
            //箭头
            private TextView tvArrows;
            public ViewHolder(View view) {
                tvNumOption = (TextView) view.findViewById(R.id.num_option);
                tvOption = (TextView) view.findViewById(R.id.tv_option);
                tvArrows = (TextView) view.findViewById(R.id.tv_arrows);
            }
        }
    }


    private static class OptionTabAdapter extends BaseAdapter{
        private List<OptionTab> mDatas;
        private Context mContext;

        private boolean isFirst=true;

        public OptionTabAdapter(Context context, List<OptionTab> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_option_tab, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            OptionTab optionTab = mDatas.get(position);
            holder.tvOptionTab.setText(optionTab.option);
            if (optionTab.isClick) {
                holder.tvOptionTab.setTextColor(Color.WHITE);
                holder.tvOptionTab.setBackgroundResource(R.drawable.circle_blue);
            } else if (!optionTab.isClick) {
                holder.tvOptionTab.setTextColor(convertView.getResources().getColor(R.color.bg_blue_color));
                holder.tvOptionTab.setBackgroundResource(R.drawable.circle_rim_blue);
            }

            return convertView;
        }

        public void upDateList(List<OptionTab> optionTabs) {
            this.mDatas = optionTabs;
            notifyDataSetChanged();
        }


        private class ViewHolder {
            private TextView tvOptionTab;

            public ViewHolder(View view) {
                tvOptionTab = (TextView) view.findViewById(R.id.text_option_tab);
            }
        }
    }
}
