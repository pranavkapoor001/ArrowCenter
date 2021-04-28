package com.pk.arrowcenter.Utils;

import android.os.Build;

public class Constants {

    /**
     * ARROW_TOPIC used to subscribe to Arrow wide topic for FCM
     */
    public static final String ARROW_TOPIC = "/topics/ArrowOS";

    /**
     * DEVICE_TOPIC used to subscribe to device specific topic for FCM
     */
    public static final String DEVICE_TOPIC = "/topics/" + Build.DEVICE;
}
