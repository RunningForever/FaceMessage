package fm.facemessage;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fm.FaceDetect.FaceDetect;
import fm.HttpRequest.ServerConfig;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 11/29/2016.
 */

public class CheckLogin extends Thread {
    public static final int LOGIN_SUCCESS = 401;
    public static final int LOGIN_FAIL = 402;

    private OkHttpClient mClient;

    private String mUsername;
    private String mPassword;

    private Handler mHander;
    private Message mMessage;

    public CheckLogin(String username, String password, Handler handler) {
        mClient = new OkHttpClient();

        mUsername = username;
        mPassword = password;

        mHander = handler;
    }

    @Override
    public void run() {
        mMessage = mHander.obtainMessage();
        RequestBody body = new FormBody.Builder()
                .add("username", mUsername)
                .add("password", mPassword)
                .build();
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_ADDRESS_LOGIN)
                .post(body)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            System.out.println(jsonObject);
            if (jsonObject.getInt("state") == 1) {
                mMessage.what = LOGIN_SUCCESS;
                mHander.sendMessage(mMessage);
            } else {
                mMessage.what = LOGIN_FAIL;
                mHander.sendMessage(mMessage);
            }
        } catch (IOException e) {
            mMessage.what = FaceDetect.FACE_DETECT_ERROR;
            mHander.sendMessage(mMessage);
        } catch (JSONException e) {
            mMessage.what = FaceDetect.FACE_DETECT_ERROR;
            mHander.sendMessage(mMessage);
        }
    }

    public void check() {
        this.start();
    }
}
