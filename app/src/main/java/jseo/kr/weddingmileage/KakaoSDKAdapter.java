package jseo.kr.weddingmileage;

import android.app.Activity;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

/**
 * Created by Jonghoon_Seo on 2017. 12. 31..
 */

public class KakaoSDKAdapter extends KakaoAdapter {

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
//            @Override
//            public Activity getTopActivity() {
//                return ApplicationController.getCurrentActivity();
//            }

            @Override
            public Context getApplicationContext() {
                return ApplicationController.getApplicationControllerContext();
            }
        };
    }

    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            // 로그인시 access token과 refresh token을 저장할 때 암호화 여부를 결정한다.
            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] { AuthType.KAKAO_LOGIN_ALL };
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }
}
