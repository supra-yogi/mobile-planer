package com.skripsi.yogi.planner.Common;

import org.json.JSONArray;

/**
 * Created by Yogi on 04/11/2017.
 */

public interface ResponseCallBack {
    void onResponse(JSONArray response);
    void onResponse(String response);
    void onError(String error);
}
