package jseo.kr.weddingmileage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {
    private final SessionCallback mKakaoCallback = new SessionCallback();
    private Session session;

    // view
    private LoginButton loginButton;
    private TextView mTextMessage;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeCallback(mKakaoCallback);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Retrieve login button.
        loginButton = (LoginButton) findViewById(R.id.login_kakao);

        // add session callback.
        session = Session.getCurrentSession();
        session.addCallback(mKakaoCallback);
        mKakaoCallback.setApplicationContext(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If session is closed, show LoginButton.
        if (session.isClosed()){
            loginButton.setVisibility(View.VISIBLE);
        }
        // If session is opened or openable, hide LoginButton
        else {
            loginButton.setVisibility(View.GONE);

            // If it's a state where access token is able to be refreshed, do it.
            if (session.isOpenable()) {
                session.checkAndImplicitOpen();
            }
        }
    }
}
