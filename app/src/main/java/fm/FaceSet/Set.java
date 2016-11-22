package fm.FaceSet;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fm.FaceDetect.FaceDetect;
import fm.FaceDetect.MegviiAPIConfig;
import fm.FmDialog.DialogState;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 11/16/2016.
 */

public class Set extends Thread {
    private String mToken;
    private OkHttpClient mClient;
    private Handler mHandler;
    private Message mMessage;
    public Set(Handler handler) {
        mClient = new OkHttpClient();
        mHandler = handler;
    }

    @Override
    public void run() {
        mMessage = mHandler.obtainMessage();
        RequestBody body = new FormBody.Builder()
                .add("api_key", MegviiAPIConfig.APIKEY)
                .add("api_secret", MegviiAPIConfig.APISecret)
                .add("face_tokens", mToken)
                .add("outer_id", FaceSetConfig.SET_NAME)
                .build();
        Request request = new Request.Builder()
                .url(FaceSetConfig.URL_ADD_FACE)
                .post(body)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            System.out.println(jsonObject);
            if(jsonObject.has("face_added")){
                mMessage.what = DialogState.Detect.FACE_NOT_REGISTER;
                mHandler.sendMessage(mMessage);
            }else{
                mMessage.what = FaceDetect.FACE_DETECT_ERROR;
                mHandler.sendMessage(mMessage);
            }
        } catch (IOException e) {
            mMessage.what = FaceDetect.FACE_DETECT_ERROR;
            mHandler.sendMessage(mMessage);
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Add(String token) {
        mToken = token;
        this.start();
    }
}
