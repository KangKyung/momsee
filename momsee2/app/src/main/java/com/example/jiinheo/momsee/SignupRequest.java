package com.example.jiinheo.momsee;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rudgn on 2018-12-28.
 */

public class SignupRequest extends StringRequest {

    final static private String URL = "http://k2h0508.cafe24.com/SignUp.php";
    private Map<String, String> parameters;

    public SignupRequest(String userEmail, String userPassword, String userName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userEmail", userEmail);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
