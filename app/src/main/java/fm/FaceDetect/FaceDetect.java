package fm.FaceDetect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Administrator on 10/26/2016.
 */

public class FaceDetect extends Thread {
    public  static final int FACE_DETECT_ERROR = 200;

    private Handler mHandler;

    private Context mContext;

    private String mPath;

    private OkHttpClient mClient;
    private Response mResponse;

    private Message mMessage;

    public FaceDetect(Handler handler,String Path){
        mHandler = handler;
        mClient = new OkHttpClient();
        mPath = Path;
    }

    public Request getDetectRequest() {
        File file = new File(mPath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"),file);
        RequestBody mBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key",MegviiAPIConfig.APIKEY)
                .addFormDataPart("api_secret",MegviiAPIConfig.APISecret)
                .addFormDataPart("return_attributes",MegviiAPIConfig.RETURN_ATTRIBUTES)
                .addFormDataPart("image_file","head_image",fileBody)
                .build();
        Request mRequest = new Request.Builder()
                .url(MegviiAPIConfig.DETECT_URL)
                .post(mBody)
                .build();
        return mRequest;
    }

    @Override
    public void run() {
        try {
            mResponse = mClient.newCall(getDetectRequest()).execute();
        } catch (IOException e) {
            mMessage = mHandler.obtainMessage();
            mMessage.what = FACE_DETECT_ERROR;
            mHandler.sendMessage(mMessage);
            e.printStackTrace();
        }
    }

    public boolean canCompare() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(mResponse.body().string());
       // String is = jsonObject.getString();
        return false;
    }
}
