package fm.FmDialog;

import android.content.Context;
import fm.FaceDetect.FaceDetect;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 10/25/2016.
 */

public class RegisterDialog {

    private Context mContext;

    private SweetAlertDialog mDialog;

    public RegisterDialog(Context context) {
        mContext = context;
    }

    public RegisterDialog show(){
        mDialog = new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE);
        mDialog.setCancelable(false);
        mDialog.setTitleText("Waiting...");
        mDialog.setContentText("Registering...");
        mDialog.show();
        return this;
    }

    public RegisterDialog changeState(int state,String mUsername){
        switch (state){
            case DialogState.Register.INFOMATION_NOT_COMPLETE:
                mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mDialog.setTitleText("infomation complete error");
                mDialog.setContentText("Please complete your infomation");
                break;
            case DialogState.Register.FORMAT_ERROR_PASSWORD:
                mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mDialog.setTitleText("Password format error");
                mDialog.setContentText("Please check your password");
                break;
            case DialogState.Register.FORMAT_ERROR_USERNAME:
                mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mDialog.setTitleText("Username format error");
                mDialog.setContentText("Please check your user name");
                break;
            case DialogState.Register.REGISTER_SUCCESS:
                mDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                mDialog.setTitleText("Success");
                mDialog.setContentText("Deal "+mUsername+" Welcome to FaceMessage");
                break;
            case DialogState.Register.ALREDY_REGISTER:
                mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mDialog.setTitleText("Username already registered");
                mDialog.setContentText("Please change your username");
                break;
            case DialogState.Register.PASSWORD_NOT_MACTH:
                mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mDialog.setTitleText("Two password not macth");
                mDialog.setContentText("Please Confirm your password");
                break;
            case DialogState.Register.FACE_IDENTITY_ERROR:
                break;
            case FaceDetect.FACE_DETECT_ERROR:
                mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mDialog.setTitleText("Unkown Error");
                mDialog.setContentText("Please try again");
                break;
            default:
                break;
        }
        return this;
    }

    public void DestroyDialog(){
        mDialog.dismissWithAnimation();
    }
}
