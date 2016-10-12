package fm.facemessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

/**
 * Created by Administrator on 2016/8/17.
 */
public class LoginFragment extends Fragment {

    private BootstrapEditText mUsername;
    private BootstrapEditText mPassword;
    private BootstrapButton mLogin;
    private BootstrapButton mRegister;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        mUsername = (BootstrapEditText) v.findViewById(R.id.username);
        mPassword = (BootstrapEditText) v.findViewById(R.id.password);
        mLogin = (BootstrapButton) v.findViewById(R.id.login);
        mRegister = (BootstrapButton) v.findViewById(R.id.register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                RegisterFragment registerFragment = RegisterFragment.newInstance().newInstance();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
