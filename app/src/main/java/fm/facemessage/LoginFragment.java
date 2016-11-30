package fm.facemessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/8/17.
 */
public class LoginFragment extends Fragment {

    private BootstrapEditText mUsername;
    private BootstrapEditText mPassword;
    private BootstrapButton mLogin;
    private BootstrapButton mRegister;

    private CheckLogin checkLogin;
    private Handler mHandler;

    private SweetAlertDialog mDialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CheckLogin.LOGIN_SUCCESS:
                        mDialog.dismissWithAnimation();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        break;
                    case CheckLogin.LOGIN_FAIL:
                        mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        mDialog.setTitleText("Something error");
                        break;
                    default:
                        break;
                }
            }
        };
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        mUsername = (BootstrapEditText) v.findViewById(R.id.username);
        mPassword = (BootstrapEditText) v.findViewById(R.id.password);
        mLogin = (BootstrapButton) v.findViewById(R.id.login);
        mRegister = (BootstrapButton) v.findViewById(R.id.register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setTitleText("Loading...");
                mDialog.show();
                checkLogin = new CheckLogin(mUsername.getText().toString(), mPassword.getText().toString(), mHandler);
                checkLogin.check();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
