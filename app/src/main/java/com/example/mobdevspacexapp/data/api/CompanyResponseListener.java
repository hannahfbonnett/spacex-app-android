package com.example.mobdevspacexapp.data.api;

import com.example.mobdevspacexapp.data.model.Company;

//Ref: https://stackoverflow.com/questions/33535435/how-to-create-a-proper-volley-listener-for-cross-class-volley-method-calling
public interface CompanyResponseListener {
    void onError(String message);

    void onResponse(Company response);
}
