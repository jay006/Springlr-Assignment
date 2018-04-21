package com.example.joker.springlr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //request code for signIn
    private static final int RC_SIGN_IN = 123;

    //authenticator
    private FirebaseAuth firebaseAuth;

    //auth state listener
    private FirebaseAuth.AuthStateListener authStateListener;

    //UserName
    private static String userName = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user signed in

                    userName = user.getDisplayName();

                    //check whether user logged in using facebook or google.
                    for (UserInfo userInfo : user.getProviderData()) {

                        if (userInfo.getProviderId().equals("facebook.com")) {
                            //For linked facebook account
                            Log.d("provider_info", "User is signed in with Facebook");

                        } else if (userInfo.getProviderId().equals("google.com")) {
                            //For linked Google account
                            Log.d("provider_info", "User is signed in with Google");
                        }

                    }

                } else {
                    //user not signed in

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.FacebookBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };
    }
    //handling signIn cancelled on backPress.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, userName + " logged in.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "SignedIn cancelled ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    //detaching listener from firebase auth.
    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    //ataching listener from firebase auth
    @Override
    protected void onResume() {
        super.onResume();

        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.signOut:
                //signOut
                AuthUI.getInstance().signOut(this);
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }

    }
}
