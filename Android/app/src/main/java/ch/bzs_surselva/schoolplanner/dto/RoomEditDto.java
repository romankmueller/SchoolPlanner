package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import ch.bzs_surselva.schoolplanner.helpers.StringHelper;

/**
 * Created by conrad on 03.03.2016.
 */
public final class RoomEditDto extends ItemDtoBase
{
    private static final String JsonCode = "Code";
    private static final String JsonCaption = "Caption";

    private String code;
    private String caption;

    public RoomEditDto()
    {
        super();
        this.caption = "";
        this.code = "";
    }

    public RoomEditDto(UUID id, String code, String caption)
    {
        super(id);
        this.setCode(code);
        this.setCaption(caption);
    }

    public RoomEditDto(JSONObject json)
    {
        super(json);
        try
        {
            this.setCode(json.getString(JsonCode) );
            this.setCaption(json.getString(JsonCaption));
        }
        catch (JSONException e)
        {
        }
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String value)
    {
        this.code = StringHelper.nullToEmpty(value);
    }

    public String getCaption()
    {
        return this.caption;
    }

    public void setCaption(String value)
    {
        this.caption = StringHelper.nullToEmpty(value);
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject json = super.toJson();

        try
        {
            json.put(JsonCode, this.code);
            json.put(JsonCaption, this.caption);

        }
        catch(JSONException e)
        {
        }
        return json;
    }
}
