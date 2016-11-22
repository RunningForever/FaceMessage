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
 * Created by Administrator on 11/17/2016.
 */

public class SetSearch extends Thread {

    private Handler mHandler;

    OkHttpClient mClient;

    private String mToken;

    private Message mMessage;

    public SetSearch(Handler handler, String token) {
        mHandler = handler;
        mClient = new OkHttpClient();
        mToken = token;
    }

    private void Search(String token) {
        mMessage = mHandler.obtainMessage();
        RequestBody body = new FormBody.Builder()
                .add("api_key", MegviiAPIConfig.APIKEY)
                .add("api_secret", MegviiAPIConfig.APISecret)
                .add("face_token", token)
                .add("outer_id", FaceSetConfig.SET_NAME)
                .build();
        Request request = new Request.Builder()
                .url(FaceSetConfig.URL_SEARCH_FACE)
                .post(body)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            if (jsonObject.has("results")) {
                JSONObject result = (JSONObject) jsonObject.getJSONArray("results").get(0);
                if (result.getDouble("confidence") > jsonObject.getJSONObject("thresholds").getDouble("1e-5")) {
                    mMessage.what = DialogState.Detect.FACE_EXSIT;
                    mHandler.sendMessage(mMessage);
                } else {
                    mMessage.what = DialogState.Detect.FACE_CAN_REGISTER;
                    mMessage.obj = mToken;
                    mHandler.sendMessage(mMessage);
                }
            } else {
                mMessage.what = DialogState.Detect.FACE_CAN_REGISTER;
                mMessage.obj = mToken;
                mHandler.sendMessage(mMessage);
            }
        } catch (IOException e) {
            mMessage.what = FaceDetect.FACE_DETECT_ERROR;
            mHandler.sendMessage(mMessage);
            e.printStackTrace();
        } catch (JSONException e) {
            mMessage.what = FaceDetect.FACE_DETECT_ERROR;
            mHandler.sendMessage(mMessage);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.Search(mToken);
    }
}
