package fm.HttpRequest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fm.FaceDetect.FaceDetect;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 10/22/2016.
 */

public class HttpExecute extends Thread implements Runnable {

    public static final int MSG_SERVER_RESULT = 10;

    private OkHttpClient mHttpClient;

    private Handler mHandler;

    private FaceDetect mDetector;

    private RequestBody mRequestBody;
    private int mRequestCode;

    private Map<Integer,String> mUrl;

    public HttpExecute(Handler handler) {
        mHttpClient = new OkHttpClient();

        mHandler = handler;

        mUrl = new HashMap<>();
        mUrl.put(ServerConfig.REQUEST_DELETE,ServerConfig.SERVER_ADDRESS_DELETE);
        mUrl.put(ServerConfig.REQUEST_LOGIN,ServerConfig.SERVER_ADDRESS_LOGIN);
        mUrl.put(ServerConfig.REQUEST_REGISTER,ServerConfig.SERVER_ADDRESS_REGISTER);
        mUrl.put(ServerConfig.REQUEST_SEARCH,ServerConfig.SERVER_ADDRESS_SERCH);
        mUrl.put(ServerConfig.REQUEST_SET,ServerConfig.SERVER_ADDRESS_SET);
    }

    private String Post(String url, RequestBody requestBody) {
        Request mRequest = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response mResponse = mHttpClient.newCall(mRequest).execute();
            String result =  mResponse.body().string().toString();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        String json =   Post(mUrl.get(mRequestCode),mRequestBody);
        Message message = mHandler.obtainMessage();
        message.obj = json;
        message.what = MSG_SERVER_RESULT;
        mHandler.sendMessage(message);
    }

    public void Register(String username,String password,String rePassword,String image_1,String image_2,String image_3) {
            RequestBody mBody = new FormBody.Builder()
                    .addEncoded("username", username)
                    .addEncoded("password", password)
                    .build();
            mRequestBody = mBody;
            mRequestCode = ServerConfig.REQUEST_REGISTER;
            this.start();
    }
}

