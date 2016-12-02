package com.koma.filemanager.helper;

import com.squareup.otto.Bus;

/**
 * Created by koma on 12/2/16.
 */

public final class BusProvider {
    private static final Bus sBus = new Bus();

    private BusProvider() {
    }

    public static Bus getInstance() {
        return sBus;
    }
}
