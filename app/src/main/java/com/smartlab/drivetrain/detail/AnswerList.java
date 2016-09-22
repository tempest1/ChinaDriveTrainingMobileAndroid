package com.smartlab.drivetrain.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.PostAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.model.CardResponse;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/11/13.
 * 问题回答列表
 */
public class AnswerList extends BaseActivity implements PullToRefreshLayout.OnRefreshListener,ActionCallBackListener<List<CardResponse>>{

    private Card card;
    private List<CardResponse> list;
    private PullToRefreshLayout layout;
    private PostAdapter adapter;
    public String ANSWER_LIST = "answerList";
    private int pagination = 1;//当前页码
    private int pageNum = 12;//每页显示的数量
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        initView();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        card = (Card) bundle.getSerializable(ANSWER_LIST);
        LogUtils.i(card + "");
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        ListView mListView = (ListView) findViewById(R.id.pull_list);
        list = new ArrayList<CardResponse>();
        adapter = new PostAdapter(this,list);
        mListView.setAdapter(adapter);
        TextView news_title = (TextView) findViewById(R.id.news_title);
        news_title.setText("问题答疑");
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        layout.setOnRefreshListener(this);
        Util.runDelay(layout);
    }


    /**
     * 刷新操作
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getAnswerList(PullToRefreshLayout.PULL_REFRESH);
    }

    private void getAnswerList(int currentState) {

        if (card == null){
            close(this);
            return;
        }
        if (card.getTimestamp() == null || Util.isEmptyString(card.getTimestamp())) {
            close(this);
            return;
        }
        User info = MainApplication.getUserInfo();
        if (info != null) {
            String userName = info.getPhone();
//        if (userName == null){
//            showToast("请登录后再查看");
//            return;
//        }
            this.currentState = currentState;
            appAction.loadPostList("showPostList", card.getTimestamp(), pagination, userName, pageNum, Params.POST_LIST_URL, this);
        } else {
            this.currentState = currentState;
            appAction.loadPostList("showPostList", card.getTimestamp(), pagination, null, pageNum, Params.POST_LIST_URL, this);
        }
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pagination++;
        getAnswerList(PullToRefreshLayout.PULL_LOAD);
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<CardResponse> data) {
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                list.clear();
                list.addAll(data);
                adapter.notifyDataSetChanged();
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                if (data.size() <= 0){
                    pagination --;
                    showToast("没有更多内容。。");
                }
                list.addAll(data);
                adapter.notifyDataSetChanged();
                layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
        }
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                layout.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                layout.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
        showToast(message);
    }
}
