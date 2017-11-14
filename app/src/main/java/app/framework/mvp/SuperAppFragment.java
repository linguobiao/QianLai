package app.framework.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lgb.xpro.activity.AppFragment;
import com.lgb.xpro.router.Router;

/**
 * Created by LW on 2017/4/14.
 */

public class SuperAppFragment extends AppFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Router.getInstance().unregister(this);
    }
}
