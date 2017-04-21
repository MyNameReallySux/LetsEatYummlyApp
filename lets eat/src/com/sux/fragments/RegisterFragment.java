package com.sux.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sux.utility.DataAsync;
import com.sux.utility.Encoder;
import com.sux.exceptions.ExceptionManager;
import com.sux.exceptions.RegisterException;
import com.sux.main.R;

/**
 * Created by Shorty on 4/16/14.
 */
public class RegisterFragment extends Fragment {
    private TextView fname, lname, email, password, password2;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        context = getActivity();

        fname = (TextView)view.findViewById(R.id.enterFirstName);
        lname = (TextView)view.findViewById(R.id.enterLastName);
        email = (TextView)view.findViewById(R.id.enterEmail);
        password = (TextView)view.findViewById(R.id.enterPassword);
        password2 = (TextView)view.findViewById(R.id.enterPassword2);
        return view;
    }

    public void register(View view){
        String fnameStr, lnameStr, emailStr, passwordStr, passwordStr2;
        try {
            if(fname.getText() == null || fname.getText() == "") throw new RegisterException("First name is blank. Please enter a first name!");
            if(lname.getText() == null || lname.getText() == "") throw new RegisterException("Last name is blank. Please enter a last name!");
            if(email.getText() == null || lname.getText() == "") throw new RegisterException("Email is blank. Please enter an email address!");
            if(password.getText() == null || password.getText() == "") throw new RegisterException("Password is blank. Please enter a password!");
            if(password2.getText() == null || password2.getText() == "") throw new RegisterException("Passwords do not match! Make sure to type in the exact same password twice.");

            fnameStr = fname.getText().toString();
            lnameStr = lname.getText().toString();
            emailStr = email.getText().toString();
            passwordStr = password.getText().toString();
            passwordStr2 = password2.getText().toString();

            if(fnameStr.length() <= 2 || !Encoder.regex(fnameStr, Encoder.ALPHA_PATTERN)) throw new RegisterException("First name is not valid. Must be at least 3 letters with no spaces.");
            if(lnameStr.length() <= 2 || !Encoder.regex(lnameStr, Encoder.ALPHA_PATTERN)) throw new RegisterException("Last name is not valid. Must be at least 2 characters with no spaces.!");
            if(emailStr.length() <= 0 || !Encoder.regex(emailStr, Encoder.EMAIL_PATTERN)) throw new RegisterException("Please enter an email address! example: 'email@domain.net'");
            if(passwordStr.length() <= 5)                                                 throw new RegisterException("Please enter password! Must be 6 characters or longer.");
            if(!passwordStr.equals(passwordStr2))                                         throw new RegisterException("Passwords do not match! Make sure to type in the exact same password twice.");

            new DataAsync(context).execute(DataAsync.REGISTER_ASYNC, fnameStr, lnameStr, emailStr, passwordStr, passwordStr2);
        } catch (RegisterException e) {
            ExceptionManager.logException(e, context, "Register", "register()");
        }
    }

}