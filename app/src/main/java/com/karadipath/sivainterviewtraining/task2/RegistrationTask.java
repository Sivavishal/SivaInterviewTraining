package com.karadipath.sivainterviewtraining.task2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.karadipath.sivainterviewtraining.home.UtilFunctions;

import java.util.HashMap;





public class RegistrationTask extends AsyncTask<Void, Void, String> {

    Context mContext;

    HashMap<String, String> mRegistrationDetails;
    // RegistrationService mRegistrationService;


    public RegistrationTask(Context context, HashMap<String, String> registrationdetails) {
        mRegistrationDetails = registrationdetails;
        mContext = context;




    }

    @Override
    protected String doInBackground(Void... voids) {
        try {


            UserDetailsTable mUserDetailsTable = new UserDetailsTable(mContext);
            mUserDetailsTable.deleteAll();
            String status = mUserDetailsTable.InsertUserDetails(mRegistrationDetails);

          /*  if (UtilFunctions.isNetworkAvailable(mContext)) {
               *//* mRegistrationService = new RegistrationService(mContext);
                String status = mRegistrationService.SaveUserInformation(mRegistrationDetails);*//*




                return status;
            } else {
                return UtilFunctions.NETWORK_UNAVAILABLE;
            }
*/
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }


        // TODO: register the new account here.

    }

    @Override
    protected void onPostExecute(final String success) {
        try {


            if (success.toLowerCase().contains(UtilFunctions.SUCCESS.toLowerCase())) {



               /* Intent intent = new Intent(mContext, PinEntryActivity.class);
                mContext.startActivity(intent);*/


                Toast.makeText(mContext, success, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);


            } else {

                Toast.makeText(mContext, success, Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
