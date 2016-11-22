package fm.HttpRequest;

import android.os.Handler;

import fm.FaceDetect.FaceDetect;

/**
 * Created by Administrator on 11/6/2016.
 */

public class FaceRequest{

    private Handler mHandler;

    private FaceDetect mDetect;

    private String mOperation;

    private String IMAGE_1;
    private String IMAGE_2;
    private String IMAGE_3;

    public FaceRequest(Handler handler,String path_1,String path_2,String path_3) {
        mHandler = handler;

        IMAGE_1 = path_1;
        IMAGE_2 = path_2;
        IMAGE_3 = path_3;
    }
    public void detect(){
        mDetect = new FaceDetect(mHandler,IMAGE_1,IMAGE_2,IMAGE_3);
        mDetect.start();
    }
}
