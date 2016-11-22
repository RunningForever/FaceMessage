package fm.FaceDetect;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import fm.FmDialog.DialogState;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 10/26/2016.
 */

public class FaceDetect extends Thread {
    public static final int FACE_DETECT_ERROR = 200;

    private Handler mHandler;

    private Context mContext;

    private String mPath_1;
    private String mPath_2;
    private String mPath_3;

    private OkHttpClient mClient;
    private Response mResponse;

    private Message mMessage;

    private JSONObject mJsonObject;

    public FaceDetect(Handler handler, String Path_1, String Path_2, String Path_3) {
        mHandler = handler;
        mClient = new OkHttpClient();
        mPath_1 = Path_1;
        mPath_2 = Path_2;
        mPath_3 = Path_3;
    }

    public Request getDetectRequest(String path) {
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody mBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key", MegviiAPIConfig.APIKEY)
                .addFormDataPart("api_secret", MegviiAPIConfig.APISecret)
                .addFormDataPart("return_attributes", MegviiAPIConfig.RETURN_ATTRIBUTES)
                .addFormDataPart("image_file", "head_image", fileBody)
                .build();
        Request mRequest = new Request.Builder()
                .url(MegviiAPIConfig.DETECT_URL)
                .post(mBody)
                .build();
        return mRequest;
    }

    @Override
    public void run() {
        mMessage = mHandler.obtainMessage();
        try {
            mResponse = mClient.newCall(getDetectRequest(mPath_1)).execute();
            if (canCompare()) {
                mResponse = mClient.newCall(getDetectRequest(mPath_2)).execute();
                if (canCompare()) {
                    mResponse = mClient.newCall(getDetectRequest(mPath_3)).execute();
                    if (canCompare()) {
                        mMessage.what = DialogState.Detect.FACE_DETECT_SUCCESS;
                        mMessage.obj = getFaceToken();
                        mHandler.sendMessage(mMessage);
                    } else {
                        mMessage.what = DialogState.Detect.FACE_ERROR_3;
                        mHandler.sendMessage(mMessage);
                    }
                } else {
                    mMessage.what = DialogState.Detect.FACE_ERROR_2;
                    mHandler.sendMessage(mMessage);
                }
            } else {
                mMessage.what = DialogState.Detect.FACE_ERROR_1;
                mHandler.sendMessage(mMessage);
            }
        } catch (IOException e) {

            mMessage.what = FACE_DETECT_ERROR;
            mHandler.sendMessage(mMessage);
            e.printStackTrace();
        }
    }

    public boolean canCompare() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mResponse.body().string());
            mJsonObject = jsonObject;
            JSONObject json = jsonObject.getJSONArray("faces").getJSONObject(0).getJSONObject("attributes").getJSONObject("facequality");
            return json.getDouble("value") > json.getDouble("threshold");
        } catch (JSONException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public String getFaceToken() {
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray =  mJsonObject.getJSONArray("faces");
            JSONObject json = jsonArray.getJSONObject(0);
            return json.getString("face_token");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
