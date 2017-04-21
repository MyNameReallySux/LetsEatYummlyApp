package com.sux.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.sux.utility.LetsEat;
import com.sux.exceptions.ExceptionManager;
import com.sux.exceptions.RegisterException;
import com.sux.fragments.RegisterFragment;
import org.json.simple.JSONObject;

/**
 * Created by lah113-6 on 3/4/14.
 */
public class Register extends FragmentActivity {
    private RegisterFragment registerFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.register_container, registerFragment)
                .commit();
    }

    public void register(View view){
        registerFragment.register(view);
    }

    public void completeRegister(JSONObject result){
        try {
            if(!result.containsKey(LetsEat.MESSAGE)) throw new RegisterException("Could not connect to server. Please try again later!");
            String code = result.get(LetsEat.MESSAGE).toString();

            if (code.contains("Register_No_First_Name"))       throw new RegisterException("You did not enter a first name!");
            if (code.contains("Register_No_Last_Name"))        throw new RegisterException("You did not enter a last name!");
            if (code.contains("Register_Email_Invalid"))       throw new RegisterException("You did not enter a valid email address!");
            if (code.contains("Register_Email_In_Use"))        throw new RegisterException("The email you entered is already in use!");
            if (code.contains("Register_Password_Invalid"))    throw new RegisterException("You did not enter a valid password!");
            if (code.contains("Register_Password_No_Match"))   throw new RegisterException("The passwords you entered did not match!");
            if (code.contains("Register_No_Country"))          throw new RegisterException("You did not select a valid country!");
            if (code.contains("Register_No_Birthday"))         throw new RegisterException("You did not select a valid birthdate!");
            if (code.contains("Register_Age_Restricted"))      throw new RegisterException("You must be at least 13 to make an account!");
            if (code.contains("Register_No_Gender"))           throw new RegisterException("You did not select a valid gender!");
            if (!code.contains("Register_Success"))            throw new RegisterException("Could not retrieve status. Please try again later!");

            Intent intent = new Intent(this, Login.class);
            intent.putExtra(LetsEat.MESSAGE, "completeRegister");
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } catch (RegisterException e) {
            ExceptionManager.logException(e, this, "Register", "completeRegister()");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}