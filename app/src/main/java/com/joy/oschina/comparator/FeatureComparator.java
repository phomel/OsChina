package com.joy.oschina.comparator;

import com.joy.oschina.bean.Featured;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/10/19.
 */
public class FeatureComparator implements Comparator<Featured> {

    @Override
    public int compare(Featured fea1, Featured fea2) {
        long time1 = fea1.getCreated_at().getTime();
        long time2 = fea2.getCreated_at().getTime();
        if(time1>time2){
            return -1;
        } else if(time1==time2){
            return 0;
        } else {
            return 1;
        }
    }

}
