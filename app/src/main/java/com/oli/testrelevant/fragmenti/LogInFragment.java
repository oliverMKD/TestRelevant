package com.oli.testrelevant.fragmenti;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.oli.testrelevant.R;


import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.Unbinder;


public class LogInFragment extends Fragment {

    @BindView(R.id.txtWelcome)
    TextView welcome;
    @BindView(R.id.login_button)
    Button login;
    @BindView(R.id.txtReturning)
    TextView nazad;
    @BindView(R.id.signIn)
    Button sign;
    @BindView(R.id.terms)
    TextView terms;
    @BindView(R.id.maincontainer)
    RelativeLayout relativeLayout;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, null);

        mUnbinder = ButterKnife.bind(this, view);
        fontovi();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpedFragment();

            }
        });

        return view;
    }
    private void addExpedFragment(){
        Fragment exped = SignUpFragment.secondInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, exped).commit();
        login.setVisibility(View.GONE);
        sign.setVisibility(View.GONE);
    }


    public void fontovi(){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Old Typography.ttf");
        welcome.setTypeface(typeface);
        Typeface returning = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Regular.ttf");
        nazad.setTypeface(returning);
        Typeface pravila = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Regular.ttf");
        terms.setTypeface(pravila);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
