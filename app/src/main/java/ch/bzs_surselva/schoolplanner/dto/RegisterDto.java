package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

public final class RegisterDto
{
    private String account;
    private String password;
    private String email;

    public RegisterDto(String account, String password, String email)
    {
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("Account", this.account);
            json.put("Password", this.password);
            json.put("Email", this.email);
        }
        catch (JSONException e)
        {
        }

        return json;
    }
}

