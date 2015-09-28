package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

public final class ResultDto
{
    private boolean success;
    private String error;

    public ResultDto(JSONObject json)
    {
        this.success = false;
        this.error = "";

        try
        {
            this.success = json.getBoolean("Success");
            this.error = json.getString("Error");
        }
        catch (JSONException e)
        {
        }
    }

    public boolean getSuccess()
    {
        return this.success;
    }

    public String getError()
    {
        return this.error;
    }
}


