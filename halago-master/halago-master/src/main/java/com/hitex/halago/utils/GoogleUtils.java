package com.hitex.halago.utils;

//import org.apache.http.client.fluent.Form;
//import org.apache.http.client.fluent.Request;


public class GoogleUtils {
//    public static String getToken(final String code) throws ClientProtocolException, IOException {
//        String response = Request.Post(Constance.GOOGLE_LINK_GET_TOKEN)
//                .bodyForm(Form.form().add("client_id", Constance.GOOGLE_CLIENT_ID)
//                        .add("client_secret", Constance.GOOGLE_CLIENT_SECRET)
//                        .add("redirect_uri", Constance.GOOGLE_REDIRECT_URI).add("code", code)
//                        .add("grant_type", Constance.GOOGLE_GRANT_TYPE).build())
//                .execute().returnContent().asString();
//        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
//        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
//        return accessToken;
//    }
//
//    public static GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
//        String link = Constance.GOOGLE_LINK_GET_USER_INFO + accessToken;
//        String response = Request.Get(link).execute().returnContent().asString();
//        GooglePojo googlePojo = new Gson().fromJson(response, GooglePojo.class);
//        System.out.println(googlePojo);
//        return googlePojo;
//    }
//    }
}
