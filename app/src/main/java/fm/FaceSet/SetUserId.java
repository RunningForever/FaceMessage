package fm.FaceSet;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.io.IOException;

import fm.FaceDetect.FaceDetect;
import fm.FaceDetect.MegviiAPIConfig;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 11/23/2016.
 */

public class SetUserId extends Thread {
    private String mToken;
    private String mUsername;

    private Handler mHandler;

    private Message mMessage;

    private OkHttpClient mClient;

    public SetUserId(String token, String username, Handler handler) {
        this.mHandler = handler;

        this.mToken = token;
        this.mUsername = username;

        mClient = new OkHttpClient();
    }

    @Override
    public void run() {
        mMessage = mHandler.obtainMessage();
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key", MegviiAPIConfig.APIKEY)
                .add("api_secret", MegviiAPIConfig.APISecret)
                .add("face_token", mToken)
                .add("user_id", mUsername)
                .build();
        Request request = new Request.Builder()
                .url(FaceSetConfig.URL_ADDUSERID)
                .post(requestBody)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            mMessage.what = FaceDetect.FACE_DETECT_ERROR;
            mHandler.sendMessage(mMessage);
            e.printStackTrace();
        }
    }

    public void Post(){
        this.start();
    }
}
