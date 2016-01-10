package ch.bzs_surselva.schoolplanner.helpers;

import android.util.Base64;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;

import javax.net.ssl.HttpsURLConnection;

public final class RequestHelper
{
    public static HttpsURLConnection createRequest(String restService, String method)
    {
        if (!method.equals("GET") && !method.equals("PUT") && !method.equals("POST") && !method.equals("DELETE"))
        {
            throw new InvalidParameterException("The method must be of type 'GET', 'PUT' or 'POST'.");
        }

        String baseAddress = "https://bzssurselva.azurewebsites.net/appservice.svc/";
        String urlAddress = baseAddress + restService;
        try
        {
            URL url = new URL(urlAddress);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            String userPassword = CredentialHelper.getAccount() + ":" + CredentialHelper.getPassword();
            String authorization = new String(Base64.encode(userPassword.getBytes(), Base64.NO_WRAP));
            connection.setRequestProperty("Authorization", "Basic " + authorization);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(method);
            return connection;
        }
        catch (MalformedURLException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
    }
}

