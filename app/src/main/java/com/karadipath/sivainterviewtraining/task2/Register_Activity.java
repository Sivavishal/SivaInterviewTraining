package com.karadipath.sivainterviewtraining.task2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karadipath.sivainterviewtraining.R;

import java.util.HashMap;


public class Register_Activity extends AppCompatActivity {
    Context mContext;
    TextInputLayout mInput_layout_edtTxt_first_name, mInput_layout_edtTxt_first_email, mInput_layout_edtTxt_first_password;
    EditText mEdtxt_first_name, mEdtxt_mobile, mEdtxt_email, mEdtxt_password;
    RelativeLayout mSignup_button, mLayout;
    TextView mTxt_signup;
    String mUserName, mUserEmail, mUserMobile, mUserPassword;
    RegistrationTask mRegistrationTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContext = Register_Activity.this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        mInput_layout_edtTxt_first_name = (TextInputLayout) findViewById(R.id.input_edtTxt_first_name);
        mInput_layout_edtTxt_first_email = (TextInputLayout) findViewById(R.id.input_edtTxt_email);
        mInput_layout_edtTxt_first_password = (TextInputLayout) findViewById(R.id.input_edtTxt_password);
        mEdtxt_first_name = (EditText) findViewById(R.id.edtTxt_first_name);
        mEdtxt_mobile = (EditText) findViewById(R.id.edtTxt_mob_no);
        mEdtxt_email = (EditText) findViewById(R.id.edtTxt_email);
        mEdtxt_password = (EditText) findViewById(R.id.edtTxt_password);
        mSignup_button = (RelativeLayout) findViewById(R.id.signup_button);
        mLayout = (RelativeLayout) findViewById(R.id.parent_lay);
        mTxt_signup = (TextView) findViewById(R.id.txt_signup);




        mSignup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidDetails = CheckFieldvalues();

                if (isValidDetails) {
                    HashMap<String, String> registrationdetails = new HashMap<String, String>();
                    registrationdetails.put("UserName", mUserName);
                    registrationdetails.put("PIN", mUserPassword);
                    registrationdetails.put("mobile", mUserMobile);
                    registrationdetails.put("UserEmail", mUserEmail);





                    mRegistrationTask = new RegistrationTask(mContext, registrationdetails);
                    mRegistrationTask.execute();


                }

            }
        });
    }

    public boolean CheckFieldvalues() {

        mEdtxt_first_name.setError(null);
        mEdtxt_mobile.setError(null);
        mEdtxt_email.setError(null);
        mEdtxt_password.setError(null);


        mUserName = mEdtxt_first_name.getText().toString().trim();
        mUserEmail = mEdtxt_email.getText().toString().trim();
        mUserMobile =  mEdtxt_mobile.getText().toString().trim();
        mUserPassword = mEdtxt_password.getText().toString().trim();


        if (mUserName.isEmpty()) {
            mEdtxt_first_name.setError(getString(R.string.error_field_required));
            return false;
        }/* else if (mUserMobile.isEmpty()) {
            mEdtxt_mobile.setError(getString(R.string.error_field_required));
            return false;
        }*/ else if (mUserEmail.isEmpty()) {
            mEdtxt_email.setError(getString(R.string.error_field_required));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mUserEmail).matches()) {
            mEdtxt_email.setError(getString(R.string.error_invalid_email));
            return false;
        }else if (mUserPassword.isEmpty()) {
            mEdtxt_password.setError(getString(R.string.error_field_required));
            return false;
        }else if (mUserPassword.length()!=4) {
            mEdtxt_password.setError(getString(R.string.error_four_digit_field_required));
            return false;
        }


        return true;
    }
}
