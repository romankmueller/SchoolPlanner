package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by conrad on 03.03.2016.
 */
public final class RoomLookupDto extends ItemDtoBase
{
    private  final String JsonCode = "Code";
    private final String JsonCaption = "Caption";

    private String code;
    private String caption;

    public RoomLookupDto()
    {
        super();
        this.code = "";
        this.caption = "";
    }

    public RoomLookupDto(JSONObject json)
    {
        super(json);
        try
        {
            this.code = json.getString(JsonCode);
            this.caption = json.getString(JsonCaption);
        }
        catch(JSONException e)
        {
        }
    }
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
