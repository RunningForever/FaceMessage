package fm.facemessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapEditText;

/**
 * Created by Administrator on 2016/8/17.
 */
public class LoginFragment extends Fragment{

    private BootstrapEditText mUsername;
    private BootstrapEditText mPassword;

    public static LoginFragment newInstance(){return new LoginFragment();}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);
        mUsername = (BootstrapEditText) v.findViewById(R.id.username);
        mPassword = (BootstrapEditText) v.findViewById(R.id.password);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
