package fm.takePhoto;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;

/**
 * Created by Administrator on 10/20/2016.
 */

public class TakeConfig {
    private TakePhoto mTakePhoto;

    private final int MaxSize = 102400;
    private final int MaxPixel = 800;
    private final int CropWidth = 800;
    private final int CropHeight = 800;

    private final boolean showProgressBar = true;
    private final boolean withWonCrop = true;

    TakeConfig(TakePhoto takePhoto)
    {
        this.mTakePhoto = takePhoto;
    }

    private void configCompress()
    {
        CompressConfig config= new CompressConfig.Builder().setMaxPixel(MaxSize).setMaxPixel(MaxPixel).create();
        mTakePhoto.onEnableCompress(config,showProgressBar);
    }

    public CropOptions configCrop()
    {
        CropOptions.Builder builder=new CropOptions.Builder();
        builder.setAspectX(CropWidth).setAspectY(CropHeight);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
