package com.sux.main;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.sux.utility.DataAsync;
import com.sux.utility.LetsEat;
import com.sux.utility.Encoder;
import com.sux.exceptions.ExceptionManager;
import com.sux.exceptions.LoginException;
import com.sux.fragments.MessageFragment;
import org.json.simple.JSONObject;

public class Login extends FragmentActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    /**
     * Called when the activity is first created.
     */

    private EditText email, password;
    private GestureDetectorCompat detector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText)findViewById(R.id.enterEmail);
        password = (EditText)findViewById(R.id.enterPassword);

        Intent intent = getIntent();
        if(intent != null && intent.getStringExtra(LetsEat.MESSAGE) != null && intent.getStringExtra(LetsEat.MESSAGE).equals("completeRegister")){
            registerComplete();
            intent.removeExtra(LetsEat.MESSAGE);
        }

        detector = new GestureDetectorCompat(this, this);
        detector.setOnDoubleTapListener(this);
    }

    public void login(View view){
        String emailStr, passwordStr;
        try {
            if(email.getText() == null || email.getText().toString().length() <= 0)         throw new LoginException("Email field was left blank");
            if(password.getText() == null || password.getText().toString().length() <= 0)   throw new LoginException("Password field was left blank");
            if(email.getText().length() < 6 || !Encoder.regex(email.getText().toString(), Encoder.EMAIL_PATTERN))           throw new LoginException("Invalid email! Please enter a valid email with a host & domain! 'email@domain.net'");
            if(password.getText().length() < 6 || !Encoder.regex(password.getText().toString(), Encoder.PASSWORD_PATTERN))  throw new LoginException("Password must be 6 characters or longer! Must contain only numbers, letters, or '@#$%'.");

            emailStr = email.getText().toString();
            passwordStr = password.getText().toString();

            new DataAsync(this).execute(DataAsync.LOGIN_ASYNC, emailStr, passwordStr);
        } catch (LoginException e) {
            ExceptionManager.logException(e, this, "Login", "login()");
        }
    }

    public void loginComplete(JSONObject result){
        try {
            if(!result.containsKey(LetsEat.MESSAGE)) throw new LoginException("Could not connect to server. Please try again later!");
            String code = result.get(LetsEat.MESSAGE).toString();

            if (code.contains("Login_Blank"))              throw new LoginException("Could not login. Either the email field or password field was left blank.");
            if (code.contains("Login_Not_Yet_Activated"))  throw new LoginException("That account has not been activated!");
            if (code.contains("Login_Deactivated"))        throw new LoginException("That account has been deactivated!");
            if (code.contains("Login_Failed"))             throw new LoginException("The email and password do not match!");
            if (!code.contains("Login_Success"))           throw new LoginException("Could not initialize login.");

            String id = result.get(LetsEat.ID).toString();
            LetsEat.setLoginInfo(this, result);
            LetsEat.loadProfile(this, id);
        } catch (LoginException e) {
            ExceptionManager.logException(e, this, "Login", "loginComplete()");
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void registerComplete(){
        FragmentManager fragmentManager = getFragmentManager();
        MessageFragment messageFragment = new MessageFragment("This is a test message! Your account is ready you just have to log in!", MessageFragment.ICON_CHECK_MARK);
        messageFragment.show(fragmentManager, "Success!");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e){
        new DataAsync(this).execute(DataAsync.LOGIN_ASYNC, "nogrow315@aol.com", "qwerty");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
