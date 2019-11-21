package com.skripsi.yogi.planner.DAL;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.yogi.planner.Common.RequestHandler;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Domain.Batasans.Batasan;
import com.skripsi.yogi.planner.Domain.Batasans.IBatasanRepo;
import com.skripsi.yogi.planner.Domain.Users.IUserRepo;
import com.skripsi.yogi.planner.Domain.Users.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 05/11/2017.
 */

public class BatasanAPI extends BaseAPI<Batasan> implements IBatasanRepo {

    public BatasanAPI(Context context) {
        super(context);
    }

    @Override
    public void save(final ResponseCallBack responseCallBack, final Batasan entity) {
        if (entity.getId() == 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseCallBack.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseCallBack.onError(errorResponseHandler(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(entity.getId()));
                    params.put("userId", String.valueOf(entity.getUser().getId()));
                    //Batasan Waktu
                    params.put("waktuCepatFrom", entity.getWaktuCepatFrom().toString());
                    params.put("waktuCepatTo", entity.getWaktuCepatTo().toString());
                    params.put("waktuLamaFrom", entity.getWaktuLamaFrom().toString());
                    params.put("waktuLamaTo", entity.getWaktuLamaTo().toString());
                    //Batasan Harga
                    params.put("biayaRendahFrom", entity.getBiayaRendahFrom().toString());
                    params.put("biayaRendahTo", entity.getBiayaRendahTo().toString());
                    params.put("biayaSedangFrom", entity.getBiayaSedangFrom().toString());
                    params.put("biayaSedangTo", entity.getBiayaSedangTo().toString());
                    params.put("biayaTinggiFrom", entity.getBiayaTinggiFrom().toString());
                    params.put("biayaTinggiTo", entity.getBiayaTinggiTo().toString());
                    //Batasan Interest
                    params.put("kebutuhanRendahFrom", entity.getKebutuhanRendahFrom().toString());
                    params.put("kebutuhanRendahTo", entity.getKebutuhanRendahTo().toString());
                    params.put("kebutuhanSedangFrom", entity.getKebutuhanTinggiFrom().toString());
                    params.put("kebutuhanSedangTo", entity.getKebutuhanTinggiTo().toString());

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(postRequest);
        } else {
            StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseCallBack.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseCallBack.onError(errorResponseHandler(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(entity.getId()));
                    //Batasan Waktu
                    params.put("waktuCepatFrom", entity.getWaktuCepatFrom().toString());
                    params.put("waktuCepatTo", entity.getWaktuCepatTo().toString());
                    params.put("waktuLamaFrom", entity.getWaktuLamaFrom().toString());
                    params.put("waktuLamaTo", entity.getWaktuLamaTo().toString());
                    //Batasan Harga
                    params.put("biayaRendahFrom", entity.getBiayaRendahFrom().toString());
                    params.put("biayaRendahTo", entity.getBiayaRendahTo().toString());
                    params.put("biayaSedangFrom", entity.getBiayaSedangFrom().toString());
                    params.put("biayaSedangTo", entity.getBiayaSedangTo().toString());
                    params.put("biayaTinggiFrom", entity.getBiayaTinggiFrom().toString());
                    params.put("biayaTinggiTo", entity.getBiayaTinggiTo().toString());
                    //Batasan Interest
                    params.put("kebutuhanRendahFrom", entity.getKebutuhanRendahFrom().toString());
                    params.put("kebutuhanRendahTo", entity.getKebutuhanRendahTo().toString());
                    params.put("kebutuhanSedangFrom", entity.getKebutuhanTinggiFrom().toString());
                    params.put("kebutuhanSedangTo", entity.getKebutuhanTinggiTo().toString());

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(putRequest);
        }
    }

    @Override
    public Batasan createNew() {
        return new Batasan();
    }

    @Override
    public String getUrl() {
        return URLs.URL_BATASAN;
    }

    @Override
    public void getBatasanByUserId(final ResponseCallBack responseCallBack, final int userId) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getBatasanByUserId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", String.valueOf(userId));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }
}
