package com.stockexchange.ui;

import com.stockexchange.entity.price.DfnMessage;

/**
 * Created by darshanas on 11/28/2017.
 */
public interface Observer {

    public void updateView(DfnMessage message);


}
