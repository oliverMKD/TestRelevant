package com.oli.testrelevant.fragmenti;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oli.testrelevant.R;
import com.oli.testrelevant.adapteri.RecyclerAdapter;
import com.oli.testrelevant.api.ApiConstants;
import com.oli.testrelevant.api.RestApi;
import com.oli.testrelevant.klasi.Rewards;
import com.oli.testrelevant.modeli.RewardsModel;
import com.oli.testrelevant.sharedPrefferences.SharedPreff;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RewardsFragment extends android.support.v4.app.Fragment  {

    public static Fragment thirdInstance() {
        Fragment eFrag1 = new RewardsFragment();
        return eFrag1;
    }

    @BindView(R.id.txtRewards)
    TextView textView;
    @BindView(R.id.slikaBull)
    ImageView bik;
    @BindView(R.id.svezda1)
    ImageView svezda1;
    @BindView(R.id.svezda2)
    ImageView svezda2;
    @BindView(R.id.svezda3)
    ImageView svezda3;
    @BindView(R.id.svezda4)
    ImageView svezda4;
    @BindView(R.id.rewards)
    TextView textView2;
    @BindView(R.id.slikaTocki)
    ImageView tocki;
    @BindView(R.id.MyRV)
    RecyclerView rv;
    RecyclerAdapter adapter;
    RestApi api;
    public Rewards rewards1;
    RewardsModel model = new RewardsModel();
    private Unbinder mUnbinder;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rewards, null);

        mUnbinder = ButterKnife.bind(this, view);
        getRewards();
        setSliki();
        setText();


        return view;
    }
    public void setSliki(){
        Picasso.with(getActivity()).load(R.drawable.txlc_lotterry_image).into(bik);
        Picasso.with(getActivity()).load(R.drawable.stars_rewards).into(svezda1);
        Picasso.with(getActivity()).load(R.drawable.stars_rewards).into(svezda2);
        Picasso.with(getActivity()).load(R.drawable.stars_rewards).into(svezda3);
        Picasso.with(getActivity()).load(R.drawable.stars_rewards).into(svezda4);
        Picasso.with(getActivity()).load(R.drawable.line_dotted).fit().into(tocki);
    }
    public void setText(){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Old Typography.ttf");
        textView.setTypeface(typeface);
        Typeface typeface2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/UnitedSansSemiExt-Light_0.otf");
        textView2.setTypeface(typeface2);
    }

    public void getRewards(){
        api = new RestApi(getActivity());
        String bbbb = SharedPreff.getSessionID(getActivity());
        String aaa = ApiConstants.api_key;
        Call<Rewards> call = api.getNagradi(bbbb,aaa);
        call.enqueue(new Callback<Rewards>() {
            @Override
            public void onResponse(Call<Rewards> call, Response<Rewards> response) {
                if (response.isSuccessful()){
                    rewards1 = response.body();
                    adapter = new RecyclerAdapter(getActivity(),model);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(getActivity(),1));
                    rv.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Response successful", Toast.LENGTH_SHORT).show();
                } else if (!response.isSuccessful()){

                }

            }

            @Override
            public void onFailure(Call<Rewards> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
