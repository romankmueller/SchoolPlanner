package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class DayLookupDto extends ItemDtoBase
{
    //private final String JsonOrder = "";
    private final String JsonCode = "Code";
    private final String JsonCaption = "Caption";

    //private int order;
    private String code;
    private String caption;

    public DayLookupDto()
    {
        super();
       // this.order = 0;
        this.code = "";
        this.caption = "";
    }

    public DayLookupDto(JSONObject json)
    {
        super(json);
        try
        {
           // this.order = json.getInt(JsonOrder);
            this.code = json.getString(JsonCode);
            this.caption = json.getString(JsonCaption);
        }
        catch (JSONException e)
        {
        }
    }

    /*public int getOrder()
    {
        return this.order;
    }*/

    public String getCode()
    {
        return this.code;
    }

    public String getCaption()
    {
        return this.caption;
    }

    public String toString()
    {
        return this.caption;
    }
}
