package fm.takePhoto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import java.io.File;

import fm.facemessage.R;

/**
 * Created by Administrator on 10/20/2016.
 */

public class TakeActivity extends TakePhotoActivity  {
    private TakePhoto mTakePhoto;

    public static String IMAGE_PATH = "image_path";
    public static int IMAGE_RES = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTakePhoto = getTakePhoto();
        Take();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.d("Take","Fail");
        finish();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Log.d("Take","Cancel");
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Intent mIntent = new Intent();
        mIntent.putExtra(IMAGE_PATH,result.getImage().getPath());
        this.setResult(IMAGE_RES,mIntent);
        finish();
    }
    private void Take()
    {
        File file=new File(Environment.getExternalStorageDirectory(), "/FaceMessage/photo/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        TakeConfig mConfig = new TakeConfig(mTakePhoto);
        mTakePhoto.onPickFromCaptureWithCrop(imageUri,mConfig.configCrop());
    }
}
