package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.SujectTitleAdapter;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.presenter.CenterPresenter;
import dev.baofeng.com.supermovie.presenter.GetSujectPresenter;
import dev.baofeng.com.supermovie.presenter.iview.ISubjectView;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SubjectFragment extends Fragment implements View.OnClickListener, ISubjectView, BasePullLayout.OnPullCallBackListener {

    private static SubjectFragment subjectFragment;
    @BindView(R.id.rv_suject_list)
    RecyclerView rvSujectList;
    Unbinder unbinder;
    @BindView(R.id.refreshMore)
    SimplePullLayout refreshMore;
    private GetSujectPresenter getSujectPresenter;
    private int index = 1;
    private SubjectTitleInfo infoList;
    private SujectTitleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suject_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static SubjectFragment getInstance() {
        if (subjectFragment == null) {
            subjectFragment = new SubjectFragment();
        } else {
            return subjectFragment;
        }
        return subjectFragment;
    }

    private void initView() {
        //初始化数据
        initData();
    }

    private void initData() {
        getSujectPresenter = new GetSujectPresenter(getContext(), this);

        getSujectPresenter.getSubjectTitle(index, 12);

        refreshMore.setOnPullListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadData(SubjectInfo info) {
    }

    @Override
    public void loadData(SubjectTitleInfo info) {
        this.infoList = info;
        adapter = new SujectTitleAdapter(getContext(), infoList);
        rvSujectList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSujectList.setAdapter(adapter);
    }

    @Override
    public void loadError(String msg) {
    }

    @Override
    public void loadMore(SubjectInfo result) {
    }

    @Override
    public void loadMore(SubjectTitleInfo result) {
        infoList.getData().addAll(result.getData());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSujectPresenter.getSubjectTitle(1, 12);
                if (refreshMore!=null){
                    refreshMore.finishPull("加载完成",true);
                }
            }
        },1000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSujectPresenter.getMoreTitleData(++index,12);
                refreshMore.finishPull("加载完成",true);
            }
        },1000);
    }
}
