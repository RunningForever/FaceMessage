package fm.facemessage;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 10/6/2016.
 */

public class RegisterActivity extends SingleFragmentActivity {
    @Override
    public Fragment creatFragment() {
        return RegisterFragment.newInstance();
    }
}
