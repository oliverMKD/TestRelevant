package com.oli.testrelevant;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.oli.testrelevant.adapteri.VPagerAdapter;
import com.oli.testrelevant.fragmenti.LogInFragment;
import com.oli.testrelevant.fragmenti.RewardsFragment;
import com.oli.testrelevant.fragmenti.SignUpFragment;
import com.oli.testrelevant.helpers.CustomViewPager;
import com.oli.testrelevant.sharedPrefferences.SharedPreff;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends AppCompatActivity {
    public VPagerAdapter adapter;
    public @BindView(R.id.vp)
    CustomViewPager vPage;
    Context context;
    CallbackManager callbackManager;
    String email ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        context = this;
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        FB();
        adapter = new VPagerAdapter(getSupportFragmentManager());
        adapter.dodadiFragment(new LogInFragment());
        adapter.dodadiFragment(new RewardsFragment());
        adapter.dodadiFragment(new SignUpFragment());
        vPage.setAdapter(adapter);
        vPage.setPagingEnabled(false);

        Intent intent = getIntent();
        if (intent.hasExtra("token")){
            vPage.setCurrentItem(1);
        } else if (intent.hasExtra("notoken")){
            vPage.setCurrentItem(0);
        }
        info();
    }
    public void info(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.oli.testrelevant", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void FB(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("Login Response ", response.toString());

                                try {
                                    email = object.getString("email");
                                    SharedPreff.addUser(email,FirstActivity.this);
                                    Log.v("Email = ", " " + email);
                                    Toast.makeText(getApplicationContext(), "Email : " + email, Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "No Facebook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onBackPressed() {

    }
}
