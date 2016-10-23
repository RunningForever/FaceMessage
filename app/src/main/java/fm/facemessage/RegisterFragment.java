package fm.facemessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.bumptech.glide.Glide;

import java.io.File;

import fm.HttpRequest.HttpExecute;
import fm.HttpRequest.ServerConfig;
import fm.takePhoto.TakeActivity;

/**
 * Created by Administrator on 10/6/2016.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public static final int  IMAGE_REQUEST_1 = 1;
    public static final int  IMAGE_REQUEST_2 = 2;
    public static final int  IMAGE_REQUEST_3 = 3;

    private BootstrapThumbnail mBootstrapThumbnail_1;
    private BootstrapThumbnail mBootstrapThumbnail_2;
    private BootstrapThumbnail mBootstrapThumbnail_3;

    private BootstrapButton mTakePhoto1;
    private BootstrapButton mTakePhoto2;
    private BootstrapButton mTakePhoto3;
    private BootstrapButton mRegister;

    private BootstrapEditText mUername;
    private BootstrapEditText mPassword;
    private BootstrapEditText mRePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container,false);

        mBootstrapThumbnail_1 = (BootstrapThumbnail) v.findViewById(R.id.image1);
        mBootstrapThumbnail_2 = (BootstrapThumbnail) v.findViewById(R.id.image2);
        mBootstrapThumbnail_3 = (BootstrapThumbnail) v.findViewById(R.id.image3);

        mTakePhoto1 = (BootstrapButton) v.findViewById(R.id.takephoto1);
        mTakePhoto2 = (BootstrapButton) v.findViewById(R.id.takephoto2);
        mTakePhoto3 = (BootstrapButton) v.findViewById(R.id.takephoto3);
        mRegister = (BootstrapButton) v.findViewById(R.id.commit_register);

        mUername = (BootstrapEditText) v.findViewById(R.id.username);
        mPassword = (BootstrapEditText) v.findViewById(R.id.password);
        mRePassword = (BootstrapEditText) v.findViewById(R.id.re_password);

        mBootstrapThumbnail_1.setRounded(true);
        mBootstrapThumbnail_2.setRounded(true);
        mBootstrapThumbnail_3.setRounded(true);

        mTakePhoto1.setOnClickListener(this);
        mTakePhoto2.setOnClickListener(this);
        mTakePhoto3.setOnClickListener(this);

        mRegister.setOnClickListener(this);

        return v;
    }
    @Override
    public void onClick(View view) {
        Intent i;
          switch(view.getId()){
              case R.id.takephoto1:
                  i = new Intent(getActivity(), TakeActivity.class);
                  startActivityForResult(i,IMAGE_REQUEST_1);
                  break;
              case R.id.takephoto2:
                  i = new Intent(getActivity(), TakeActivity.class);
                  startActivityForResult(i,IMAGE_REQUEST_2);
                  break;
              case R.id.takephoto3:
                  i = new Intent(getActivity(), TakeActivity.class);
                  startActivityForResult(i,IMAGE_REQUEST_3);
                  break;
              case R.id.commit_register:
                  HttpExecute mExecute = new HttpExecute(mUername.getText().toString(),mPassword.getText().toString(),mRePassword.getText().toString());
                  mExecute.start();
                  break;
              default:break;
          }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == TakeActivity.IMAGE_RES)
        {
            String path = data.getStringExtra(TakeActivity.IMAGE_PATH);
            switch (requestCode)
            {
                case IMAGE_REQUEST_1:
                    Glide.with(this).load(new File(path)).into(mBootstrapThumbnail_1);
                    break;
                case IMAGE_REQUEST_2:
                    Glide.with(this).load(new File(path)).into(mBootstrapThumbnail_2);
                    break;
                case IMAGE_REQUEST_3:
                    Glide.with(this).load(new File(path)).into(mBootstrapThumbnail_3);
                    break;
                default:break;
            }
        }
    }
}
