package app.framework.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lgb.xpro.activity.AppActivity;
import com.lgb.xpro.router.Router;

/**
 * Created by LW on 2017/4/14.
 */

public abstract class SupperAppActivity extends AppActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示 且修改  activity_base.xml toolbar_padding_top 为20dp
//        ActivityUIChangeUitl.setPhoneBarHyaline(this);
        Router.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        // 在此处进行回收
        Router.getInstance().unregister(this);
        super.onDestroy();
    }

}
