package fm.facemessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import fm.FmDialog.DialogState;
import fm.FmDialog.RegisterDialog;
import fm.HttpRequest.FaceRequest;
import fm.HttpRequest.HttpExecute;
import fm.HttpRequest.ServerConfig;
import fm.takePhoto.TakeActivity;
import fm.FaceDetect.FaceDetect;

/**
 * Created by Administrator on 10/6/2016.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public static final int IMAGE_REQUEST_1 = 1;
    public static final int IMAGE_REQUEST_2 = 2;
    public static final int IMAGE_REQUEST_3 = 3;

    private static final int REGISTER_STATE_FAIL = 0;
    private static final int REGISTER_STATE_SUCCESS = 1;

    private String IMAGE_PATH_1;
    private String IMAGE_PATH_2;
    private String IMAGE_PATH_3;

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

    private Handler mHandler;

    private RegisterDialog mDialog;

    private FaceRequest mFaceRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

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

        mDialog = new RegisterDialog(getActivity());

        return v;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HttpExecute.MSG_SERVER_RESULT:
                        try {
                            JSONObject jsonObject = new JSONObject((String) msg.obj);
                            switch (jsonObject.getInt("state")) {
                                case REGISTER_STATE_FAIL:
                                    mDialog.changeState(DialogState.Register.ALREDY_REGISTER, jsonObject.getString("username"));
                                    break;
                                case REGISTER_STATE_SUCCESS:
                                    mDialog.changeState(DialogState.Register.REGISTER_SUCCESS, jsonObject.getString("username"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case FaceDetect.FACE_DETECT_ERROR:
                        mDialog.changeState(FaceDetect.FACE_DETECT_ERROR,"");
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.takephoto1:
                i = new Intent(getActivity(), TakeActivity.class);
                startActivityForResult(i, IMAGE_REQUEST_1);
                break;
            case R.id.takephoto2:
                i = new Intent(getActivity(), TakeActivity.class);
                startActivityForResult(i, IMAGE_REQUEST_2);
                break;
            case R.id.takephoto3:
                i = new Intent(getActivity(), TakeActivity.class);
                startActivityForResult(i, IMAGE_REQUEST_3);
                break;
            case R.id.commit_register:
                CheckRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == TakeActivity.IMAGE_RES) {
            String path = data.getStringExtra(TakeActivity.IMAGE_PATH);
            switch (requestCode) {
                case IMAGE_REQUEST_1:
                    IMAGE_PATH_1 = path;
                    Glide.with(this).load(new File(path)).into(mBootstrapThumbnail_1);
                    break;
                case IMAGE_REQUEST_2:
                    IMAGE_PATH_2 = path;
                    Glide.with(this).load(new File(path)).into(mBootstrapThumbnail_2);
                    break;
                case IMAGE_REQUEST_3:
                    IMAGE_PATH_3 = path;
                    Glide.with(this).load(new File(path)).into(mBootstrapThumbnail_3);
                    break;
                default:
                    break;
            }
        }
    }

    private void CheckRegister() {
        mDialog.show();
        String username = mUername.getText().toString();
        String password = mPassword.getText().toString();
        String re_password = mRePassword.getText().toString();
        if (username == " " || password == " " || re_password == " ") {
            mDialog.changeState(DialogState.Register.INFOMATION_NOT_COMPLETE, username);
        } else if (!password.equals(re_password)) {
            mDialog.changeState(DialogState.Register.PASSWORD_NOT_MACTH, username);
        }
        else if(IMAGE_PATH_1==null||IMAGE_PATH_2 == null||IMAGE_PATH_3 == null){
            mDialog.changeState(DialogState.Register.INFOMATION_NOT_COMPLETE,"");
        }
        else {
            mFaceRequest = new FaceRequest(mHandler,IMAGE_PATH_1,IMAGE_PATH_2,IMAGE_PATH_3);
            mFaceRequest.detect();
            //  mExecute.Register(username, password, re_password, IMAGE_PATH_1, IMAGE_PATH_2, IMAGE_PATH_3);
        }
    }

    @Override
    public void onDestroy() {
        mDialog.DestroyDialog();
        super.onDestroy();
    }
}
