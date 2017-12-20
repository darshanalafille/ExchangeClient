package com.stockexchange;

import com.stockexchange.price.PriceClient;
import com.stockexchange.ui.Shell;


/**
 * Created by darshanas on 11/27/2017.
 */
public class StartClient {

    public static void main(String[] args) {
        PriceClient priceClient = PriceClient.getInstance();
        new Thread(() -> priceClient.startRead()).start();
        new Shell();
    }

}
