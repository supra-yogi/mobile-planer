package com.skripsi.yogi.planner.DAL;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.yogi.planner.Common.RequestHandler;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Domain.Users.IUserRepo;
import com.skripsi.yogi.planner.Domain.Users.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 05/11/2017.
 */

public class UserAPI extends BaseAPI<User> implements IUserRepo {

    public UserAPI(Context context) {
        super(context);
    }

    @Override
    public void save(final ResponseCallBack responseCallBack, final User entity) {
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
                    params.put("username", entity.getUsername());
                    params.put("email", entity.getEmail());
                    params.put("password", entity.getPassword());

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
                    params.put("username", entity.getUsername());
                    params.put("email", entity.getEmail());
                    params.put("password", entity.getPassword());
                    params.put("isChangePassword", String.valueOf(entity.isChangePassword()));

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(putRequest);
        }
    }

    @Override
    public User createNew() {
        return new User();
    }

    @Override
    public String getUrl() {
        return URLs.URL_USER;
    }

    @Override
    public void login(final ResponseCallBack responseCallBack, final String email, final String password) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "login/",
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
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public void checkPassword(final ResponseCallBack responseCallBack, final int id, final String password) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "checkPassword/",
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
                params.put("id", String.valueOf(id));
                params.put("password", password);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public void resetPassword(final ResponseCallBack responseCallBack, final String email) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "resetPassword/",
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
                params.put("email", email);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void getByUsername(final ResponseCallBack responseCallBack, final String username) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getByUsername/",
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
                params.put("username", username);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public void getByEmail(final ResponseCallBack responseCallBack, final String email) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getByEmail/",
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
                params.put("email", email);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }
}
