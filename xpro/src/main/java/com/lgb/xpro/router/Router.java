package com.lgb.xpro.router;

import android.app.Activity;
import android.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by LGB on 2017/07/01.
 *
 * // https://github.com/SilenceDut/Router
 *    compile 'com.silencedut:router:latest.release'
 */
public class Router {

    private Set<WeakReference<Object>> mReceivers= new CopyOnWriteArraySet<>();

    private Router() {}

    private static class InstanceHolder {
        private static Router sInstance = new Router();
    }

    public static Router getInstance() {
        return InstanceHolder.sInstance;
    }

    public  void register(Object receiver) {
        if(receiver==null) {
            return;
        }
        mReceivers.add(new WeakReference<>(receiver));
    }

    public void unregister(Object receiver) {
        if(receiver==null) {
            return;
        }
        for (Object mReceiver : mReceivers) {
            WeakReference weakReference = (WeakReference) mReceiver;
            Object o = weakReference.get();
            if (receiver.equals(o) || o == null) {
                mReceivers.remove(mReceiver);
            }
        }
    }

    public void unregisterAllAndExit() {
        unregisterAll();
        System.exit(0);
    }

    public void unregisterAll() {
        for (Object mReceiver : mReceivers) {
            WeakReference weakReference = (WeakReference) mReceiver;
            Object o = weakReference.get();
            if(o instanceof Fragment){
                ((Fragment) o) .onDestroy();
            }
        }
        for (Object mReceiver : mReceivers) {
            WeakReference weakReference = (WeakReference) mReceiver;
            Object o = weakReference.get();
            if(o instanceof Activity){
                ((Activity) o) .finish();
            }
        }
        mReceivers.clear();
    }
}
