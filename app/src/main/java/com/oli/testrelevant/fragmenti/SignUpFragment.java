package com.oli.testrelevant.fragmenti;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.oli.testrelevant.R;
import com.oli.testrelevant.api.ApiConstants;
import com.oli.testrelevant.api.RestApi;
import com.oli.testrelevant.klasi.User;
import com.oli.testrelevant.sharedPrefferences.SharedPreff;
import com.squareup.picasso.Picasso;


import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment  {

    public static Fragment secondInstance() {
        Fragment eFrag = new SignUpFragment();
        return eFrag;
    }

    @BindView(R.id.txtSignUp)
    TextView txtSignUp;
    @BindView(R.id.Birthday)
    EditText text1;
    @BindView(R.id.Reff)
    EditText text2;
    @BindView(R.id.Loc)
    EditText text3;
    @BindView(R.id.stikla)
    ImageView stikla;
    @BindView(R.id.txtOOOO)
    TextView textView;
    @BindView(R.id.login_button)
    Button signUp;
    RestApi api;
    private static final String EMAIL = "email";
    User user;
    String token;
    private Unbinder mUnbinder;
    @BindView(R.id.signUppp)
    RelativeLayout relativeLayout;
    final Handler handler = new Handler();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_up, null);
        mUnbinder = ButterKnife.bind(this, view);
        Slika();
        Fontovi();
        return view;
    }
    public void Slika(){
        Picasso.with(getActivity()).load(R.drawable.check_on).fit().into(stikla);
    }
    public void Fontovi(){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Old Typography.ttf");
        txtSignUp.setTypeface(typeface);
    }
    @OnClick(R.id.login_button)
    public void OnKlik(View view){
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList(EMAIL));
        signUp.setVisibility(View.GONE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getToken();
            }
        },3000);
    }

    public void getToken() {
        String bbbb = SharedPreff.getUser(getActivity());
        int fb= 2;
        String and = "android";
        api = new RestApi(getActivity());
        User user1 = new User();
        user1.email = bbbb;
        user1.register_type = fb;
        String key = ApiConstants.api_key;
        {
            Call<User> call = api.postAuthentication(bbbb,fb,key,and);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        user = response.body();
                        token = user.auth_token;
                        SharedPreff.addSessionID(token,getActivity());
                        Toast.makeText(getActivity(), "Uspesno log in", Toast.LENGTH_SHORT).show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                addExpedFragment();
                            }
                        },3000);



                    } else if (!response.isSuccessful()) {

                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        Log.d("Error code 400",String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    Log.d("ovde","aaaa");
                    Toast.makeText(getActivity(), "Something went wrong, please try again !!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
    private void addExpedFragment(){
        Fragment exped = RewardsFragment.thirdInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.signUppp, exped).commit();
    }


}
