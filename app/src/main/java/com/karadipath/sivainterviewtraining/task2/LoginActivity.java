package com.karadipath.sivainterviewtraining.task2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karadipath.sivainterviewtraining.R;
import com.karadipath.sivainterviewtraining.home.UtilFunctions;

import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public EditText mEdtxt_email, mEdtxt_password;
    TextView mTxt_signup, mTxt_signin;
    RelativeLayout mSignUp_button, mSignIn_button;
    String mUserEmail, mPassword;
    Context mContext;
    UserDetailsTable mUserDetailsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContext = LoginActivity.this;
        mSignUp_button = (RelativeLayout) findViewById(R.id.signup_button);
        mSignIn_button = (RelativeLayout) findViewById(R.id.login_button);
        mEdtxt_email = (EditText) findViewById(R.id.edtTxt_user_name);
        mEdtxt_password = (EditText) findViewById(R.id.edtTxt_password);
        mTxt_signup = (TextView) findViewById(R.id.txt_signup);
        mTxt_signin = (TextView) findViewById(R.id.txt_login);

        mUserDetailsTable = new UserDetailsTable(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }


        mSignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Register_Activity.class);
                mContext.startActivity(intent);
            }
        });

        mSignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 /*   Intent intent = new Intent(mContext, HomeScreenActivity.class);
                    mContext.startActivity(intent);*/

                boolean isValidDetails = CheckFieldvalues();

                if (isValidDetails) {





                    HashMap<String, String> mUserDetails = null;
                    try {
                        mUserDetails = mUserDetailsTable.getUserDetail();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (mUserDetails != null) {


                        if (mUserEmail.equalsIgnoreCase(mUserDetails.get("UserEmail")) && mPassword.equalsIgnoreCase(mUserDetails.get("PIN"))) {
                            mEdtxt_password.setText("");

                            UtilFunctions.setPreference(mContext, UtilFunctions.PREFS_USEREMAIL, mUserEmail);
                            UtilFunctions.setPreference(mContext, UtilFunctions.PREFS_USER_PIN, mPassword);



                            Intent intent = new Intent(mContext, WelcomeActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, R.string.error_invalid_entry, Toast.LENGTH_LONG).show();

                        }


                    } else {
                        Toast.makeText(mContext, R.string.error_please_signup, Toast.LENGTH_LONG).show();

                    }


                }
            }
        });
        if(!UtilFunctions.getPreference(mContext,UtilFunctions.PREFS_USEREMAIL).trim().isEmpty()){
            Intent intent = new Intent(mContext, WelcomeActivity.class);
            mContext.startActivity(intent);
        }
    }
    private boolean CheckFieldvalues() {
        mEdtxt_email.setError(null);
        mEdtxt_password.setError(null);
        mUserEmail = mEdtxt_email.getText().toString().trim();
        mPassword = mEdtxt_password.getText().toString().trim();


        if (mUserEmail.isEmpty()) {
            mEdtxt_email.setError(getString(R.string.login_error_field_required));
            return false;
        }
        if (mPassword.isEmpty()) {
            mEdtxt_password.setError(getString(R.string.error_field_required));
            return false;
        }
        if (!(mUserEmail.contains("@"))) {
            mEdtxt_email.setError(getString(R.string.login_error_invalid_email));
            return false;
        }
        if (mPassword.length()!=4) {
            mEdtxt_password.setError(getString(R.string.error_four_digit_field_required));
            return false;
        }


        return true;
    }

}

