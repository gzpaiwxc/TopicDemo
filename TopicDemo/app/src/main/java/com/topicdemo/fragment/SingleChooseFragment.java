package com.topicdemo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topicdemo.R;
import com.topicdemo.entity.AnswerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChooseFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {
    private ListView listView;
    private ProgressBar progressBar;
    private ImageView imgPlay;
    private boolean flag;
    private int i = 0;
    private SingleChooseListAdapter adapter;

    private List<AnswerItem> list = new ArrayList<>();

    public SingleChooseFragment() {
        // Required empty public constructor
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressBar.getProgress() < 100) {
                progressBar.setProgress(i++);
            } else {
                imgPlay.setImageResource(R.mipmap.play);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_single_choose, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.list_view);
        list = getDatas();
        adapter = new SingleChooseListAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        imgPlay = (ImageView) view.findViewById(R.id.play_pause);
        imgPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause:
                imgPlay.setImageResource(R.mipmap.pause);
                progressBar.setProgress(0);
                i = 0;
                Log.d("MainActivity", "progress==>" + progressBar.getProgress());
                flag = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            while (flag) {
                                if (i < 110) {
                                    Thread.sleep(60);
                                    i++;
                                    mHandler.sendEmptyMessage(0);
                                } else {
                                    flag = false;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    public List<AnswerItem> getDatas() {
        List<AnswerItem> strs = new ArrayList<>();
        for (int i=0;i<3;i++) {
            AnswerItem item = new AnswerItem();
            item.setAnswerItem("这是第" + i + "个选项");
            if (i == 1) {
                item.setRight(true);
            } else {
                item.setRight(false);
            }
            strs.add(item);
        }
        return strs;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            list.get(position).setUserClick(true);
            adapter.upDateList(list);
    }


    private static class SingleChooseListAdapter extends BaseAdapter{
        private static String[] options = {"A", "B", "C", "D", "E", "F"};
        private Context mContext;
        private List<AnswerItem> mDatas;

        public SingleChooseListAdapter(Context context, List<AnswerItem> datas) {
            this.mDatas = datas;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_single_choose, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder= (ViewHolder) convertView.getTag();
            }

            holder.tvAnswerOption.setText(options[position]);
            holder.tvAnswer.setText(mDatas.get(position).getAnswerItem());
            AnswerItem answerItem = mDatas.get(position);

            if (answerItem.isUserClick()) {
                holder.rlAnswer.setBackgroundResource(R.drawable.btn_blue_round_10);
                answerItem.setUserClick(false);
            } else {
                holder.rlAnswer.setBackgroundResource(R.drawable.btn_white_round_10);
            }
            return convertView;
        }

        public void upDateList(List<AnswerItem> list) {
            this.mDatas = list;
            notifyDataSetChanged();

        }


        private class ViewHolder {
            private TextView tvAnswer;
            private TextView tvAnswerOption;
            private RelativeLayout rlAnswer;

            public ViewHolder(View view) {
                tvAnswer = (TextView) view.findViewById(R.id.tv_answer);
                tvAnswerOption = (TextView) view.findViewById(R.id.tv_answer_option);
                rlAnswer = (RelativeLayout) view.findViewById(R.id.rl_answer_item);
            }
        }
    }
}
