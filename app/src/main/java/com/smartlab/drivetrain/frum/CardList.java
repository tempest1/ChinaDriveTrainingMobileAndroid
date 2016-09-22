package com.smartlab.drivetrain.frum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.CardAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.model.Frunm;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smartlab on 15/10/8.
 * 论坛主题列表
 */
public class CardList extends BaseActivity implements PullToRefreshLayout.OnRefreshListener,ActionCallBackListener<List<Card>>, View.OnClickListener{
    private Frunm frunm;
    private List<Card> list;
    private ListView mListView;
    private PullToRefreshLayout layout;
    private CardAdapter adapter;
    private TextView news_title;
    private int pagination = 1;
    private int pageNum = 10;
    public String POST_LIST_SER = "PostList";
    public String Serializable_card = "card";
    public final String CMD_CARD_LIST = "showCardList";
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;
    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Card card = (Card) adapterView.getAdapter().getItem(i);
            LogUtils.i(card.toString());
            showPostList(card);
        }
    };

    private void showPostList(Card card) {
        User info = MainApplication.getUserInfo();
        if (info == null){
            showToast("请登录后再操作");
            return;
        } else {
            Intent intent = new Intent();
            intent.setClass(CardList.this, PostList.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(POST_LIST_SER, (Serializable) card);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        getData();
        initView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        TextView add_card = (TextView) findViewById(R.id.add_card);
        add_card.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.pull_list);
        list = new ArrayList<>();
        adapter = new CardAdapter(this,list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(listener);
        news_title = (TextView) findViewById(R.id.news_title);
        if (frunm != null) {
//            LogUtils.e(frunm.toString());
            news_title.setText(frunm.getTitle());
        }
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        layout.setOnRefreshListener(this);
        Util.runDelay(layout);      //  延时执行
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }
    /*
     * 返回
     */
    public void doBack(View view) {
        onBackPressed();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        frunm = (Frunm) bundle.getSerializable(Serializable_card);
    }

    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
        appAction.loadCard(CMD_CARD_LIST, pagination, pageNum, frunm.getTimestamp(), Params.CARD_LIST_URL, this);
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_LOAD;
        pagination ++;
        appAction.loadCard(CMD_CARD_LIST, pagination, pageNum, frunm.getTimestamp(), Params.CARD_LIST_URL, this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                if (list != null)
                Collections.sort(list);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_card:
                if (MainApplication.isLogin()) {
                    Intent intent = new Intent();
                    intent.setClass(CardList.this, AddCard.class);
                    intent.putExtra("plateId", frunm.getTimestamp());
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                } else {
                    showToast("请先登录..");
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_FIRST_USER) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Card card = (Card) bundle.getSerializable(AddCard.KEY_CARD_ID);
                    list.add(card);
                    if (list != null)
                        Collections.sort(list);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
