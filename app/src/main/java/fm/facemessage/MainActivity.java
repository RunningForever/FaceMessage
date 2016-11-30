package fm.facemessage;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

/**
 * Created by Administrator on 11/23/2016.
 */

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mFragmentTabHost;
    private TabWidget mTabWidget;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabWidget = mFragmentTabHost.getTabWidget();
        mTabWidget.setStripEnabled(true);
        mTabWidget.setDividerDrawable(android.R.color.background_dark);

        TabHost.TabSpec message = mFragmentTabHost.newTabSpec("message").setIndicator(getTabImage(R.layout.button_message));
        TabHost.TabSpec send = mFragmentTabHost.newTabSpec("send").setIndicator(getTabImage(R.layout.button_send));
        TabHost.TabSpec setting = mFragmentTabHost.newTabSpec("setting").setIndicator(getTabImage(R.layout.button_setting));

        mFragmentTabHost.addTab(message, MessageFragment.class, null);
        mFragmentTabHost.addTab(send, SendFragment.class, null);
        mFragmentTabHost.addTab(setting, SendFragment.class, null);

        mFragmentTabHost.setCurrentTab(0);
    }

    private View getTabImage(int ID) {
        View view = LayoutInflater.from(this).inflate(ID, null);
        return view;
    }
}
