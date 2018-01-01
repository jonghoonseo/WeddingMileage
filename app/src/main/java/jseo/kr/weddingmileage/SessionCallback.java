package jseo.kr.weddingmileage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

/**
 * Created by Jonghoon_Seo on 2017. 12. 31..
 */

class SessionCallback implements ISessionCallback {
    Context applicationContext;

    public String userName;
    public String userId;
    public String profileUrl;

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onSessionOpened() {
        Log.d("TAG", "JH:::::");
        Log.d("TAG", "Session opened");
        //  사용자 정보 가져옴 / 회원 미가입 시 자동가입
        KakaorequestMe();

    }

    protected void KakaorequestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG", "session closed");
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info msg = " + errorResult;
                Logger.d(message);
                Log.v("fail", "fail");

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE)
                    Toast.makeText(applicationContext, "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                else
                    Log.d("TAG" , "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
                Log.d("TAG", "not signed up");
            }

            @Override
            public void onSuccess(UserProfile result) {
                Logger.d("User Profile: " + result);
                Log.v("user", result.toString());

                userName = result.getNickname();
                userId = String.valueOf(result.getId());
                profileUrl = result.getProfileImagePath();
            }
        });
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if (exception != null) {
            Log.d("TAG", exception.getMessage());
        }
    }

    
}
