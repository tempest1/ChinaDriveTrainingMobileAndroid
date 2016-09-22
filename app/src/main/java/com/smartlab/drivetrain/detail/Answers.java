package com.smartlab.drivetrain.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.CardAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/11/13.
 */
public class Answers extends BaseActivity implements PullToRefreshLayout.OnRefreshListener,ActionCallBackListener<List<Card>> {
    private List<Card> list;
    private ListView mListView;
    private PullToRefreshLayout layout;
    private CardAdapter adapter;
    private TextView news_title;
    public final String CMD_CARD_LIST = "showCardList";
    /**
     * 第几页
     */
    private int pagination = 1;
    /**
     * 每页显示数量
     */
    private int pageNum = 10;
    public String ANSWER_LIST = "answerList";
    private String timestamp = "101";
    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Card card = (Card) adapterView.getAdapter().getItem(i);
            LogUtils.i(card.toString());
            answerList(card);
        }
    };
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;

    /**
     * 写card数据请求，card详细信息
     * @param card
     */
    private void answerList(Card card) {
        Intent intent = new Intent();
        intent.setClass(this, AnswerList.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANSWER_LIST, (Serializable) card);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        initView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.pull_list);
        list = new ArrayList<>();
        adapter = new CardAdapter(this,list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(listener);
        news_title = (TextView) findViewById(R.id.news_title);
        news_title.setText("问题答疑");
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        layout.setOnRefreshListener(this);
        Util.runDelay(layout);
    }

    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
        appAction.loadCard(CMD_CARD_LIST, pagination, pageNum, timestamp, Params.CARD_LIST_URL, this);
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pagination++;   //  加载下一页
        currentState = PullToRefreshLayout.PULL_LOAD;
        appAction.loadCard(CMD_CARD_LIST, pagination, pageNum, timestamp, Params.CARD_LIST_URL, this);
    }
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<Card> data) {
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                list.clear();
                list.addAll(data);
                adapter.notifyDataSetChanged();
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                if (data.size() <= 0) {
                    pagination --;
                    showToast("没有更多类容");
                }
                list.addAll(data);
                adapter.notifyDataSetChanged();
                layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
            default:break;
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
    }
}
