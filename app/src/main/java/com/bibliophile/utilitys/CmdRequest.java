package com.bibliophile.utilitys;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

import static com.bibliophile.utilitys.Utilis_.toRequestBody;

/**
 * Created by ANDROID on 9/25/2017.
 */

public class CmdRequest {

    public static Map<String, RequestBody> joinUsParams(
            String center_name
            , String course_id
            , String payment_mode
            , String address
            , String contact_no
            , String package_id
            , String contact_person_name
            , String email
            , String about
            , String country
            , String state
            , String city) {
        //  @PartMap() Map<String, RequestBody> partMap
        Map<String, RequestBody> params = new HashMap<>();
        params.put("center_name", toRequestBody(center_name));
        params.put("course_id", toRequestBody(course_id));
        params.put("payment_mode", toRequestBody(payment_mode));
        params.put("package_id", toRequestBody(package_id));
        params.put("address", toRequestBody(address));
        params.put("contact_no", toRequestBody(contact_no));
        params.put("contact_person_name", toRequestBody(contact_person_name));
        params.put("email", toRequestBody(email));
        params.put("password", toRequestBody("12345"));
        params.put("about", toRequestBody(about));
        params.put("country", toRequestBody(country));
        params.put("state", toRequestBody(state));
        params.put("city", toRequestBody(city));
        return params;
    }

}
