package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public abstract class ItemDtoBase
{
    private static final String JsonId = "Id";

    private UUID id;

    protected ItemDtoBase()
    {
        this.id = UUID.randomUUID();
    }

    protected ItemDtoBase(UUID id)
    {
        this.id = id;
    }

    protected ItemDtoBase(JSONObject json)
    {
        try
        {
            this.id = UUID.fromString(json.getString(JsonId));
        }
        catch (JSONException e)
        {
        }
    }

    public UUID getId()
    {
        return this.id;
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();

        try
        {
            json.put(JsonId, this.getId());
        }
        catch (JSONException e)
        {
        }

        return json;
    }
}
