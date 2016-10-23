package fm.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 10/22/2016.
 */

public class HttpExecute extends Thread implements Runnable {

    private String mUsername;
    private String mPassword;
    private String mRePassword;

    private OkHttpClient mHttpClient;

    public HttpExecute(String username,String password,String rePassword){
        this.mPassword = password;
        this.mUsername = username;
        this.mRePassword = rePassword;

        mHttpClient = new OkHttpClient();
    }
    public String Post(String url){
        RequestBody mBody = new FormBody.Builder()
                .add("username",this.mUsername)
                .add("password",this.mPassword)
                .build();
        Request mRequest = new Request.Builder()
                .url(url)
                .post(mBody)
                .build();
        try {
            Response mResponse = mHttpClient.newCall(mRequest).execute();
            return mResponse.body().string().toString();
        }catch (IOException e){

        }
        return null;
    }

    @Override
    public void run() {
        String s = Post(ServerConfig.SERVER_ADDRESS);
        try {
            JSONObject mJson = new JSONObject(s);
            System.out.println(mJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

