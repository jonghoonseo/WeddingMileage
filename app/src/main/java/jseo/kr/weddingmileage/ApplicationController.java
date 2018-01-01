package jseo.kr.weddingmileage;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;

/**
 * Created by Jonghoon_Seo on 2017. 12. 30..
 */

public class ApplicationController extends Application {
    private static ApplicationController instance = null;
    private static volatile Activity currentActivity = null;

    /**
     * singleton
     * @return singleton
     */
    public static ApplicationController getApplicationControllerContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationController.instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static Activity getCurrentActivity() {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {
        ApplicationController.currentActivity = currentActivity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
