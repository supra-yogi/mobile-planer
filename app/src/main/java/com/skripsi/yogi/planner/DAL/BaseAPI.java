package com.skripsi.yogi.planner.DAL;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.yogi.planner.Common.EntityBase;
import com.skripsi.yogi.planner.Common.IRepository;
import com.skripsi.yogi.planner.Common.RequestHandler;
import com.skripsi.yogi.planner.Common.ResponseCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yogi on 02/11/2017.
 */

public abstract class BaseAPI<T extends EntityBase> implements IRepository<T> {
    protected Context context;
    protected String tag;
    protected String url;

    public BaseAPI(Context context) {
        this.context = context;
        this.tag = context.getClass().getSimpleName();
        url = getUrl();
    }

    @Override
    public void getById(final ResponseCallBack responseCallBack, int id) {
        JsonArrayRequest request = new JsonArrayRequest(url + id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseCallBack.onResponse(response);
                        Log.d(tag, "Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void getAll(final ResponseCallBack responseCallBack) {
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseCallBack.onResponse(response);
                        Log.d(tag, "Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void delete(final ResponseCallBack responseCallBack, int id) {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(deleteRequest);
    }

    public String errorResponseHandler(VolleyError error) {
        String message = null;
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            NetworkResponse response = error.networkResponse;
            JSONObject errors = null;
            if (response.data != null) {
                try {
                    String responseBody = new String(response.data);
                    errors = new JSONObject(responseBody);
                    message = errors.getString("message");
                } catch (JSONException e) {
                    Log.d(context.getClass().getSimpleName(), "onErrorResponse: " + e.getMessage());
                }
            } else {
                message = "The server could not be found. Please try again after some time!!";
            }
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! The server could not be found or no internet connection.";
        }

        return message;
    }

    public abstract void save(final ResponseCallBack responseCallBack, T entity);
    public abstract T createNew();
    public abstract String getUrl();
}