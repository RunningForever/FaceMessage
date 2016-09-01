package fm.facemessage;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/8/17.
 */
public class LoginActivity extends SingleFragmentActivity {
    @Override
    public Fragment creatFragment() {
        return LoginFragment.newInstance();
    }
}
