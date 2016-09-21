package fm.facemessage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by Administrator on 2016/8/17.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    public abstract Fragment creatFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        TypefaceProvider.registerDefaultIconSets();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = creatFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
