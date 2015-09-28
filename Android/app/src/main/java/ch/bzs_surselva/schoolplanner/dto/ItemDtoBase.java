package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public abstract class ItemDtoBase
{
    private UUID id;

    protected ItemDtoBase()
    {
        this.setId(UUID.randomUUID());
    }

    public UUID getId()
    {
        return this.id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("Id", this.getId());
        }
        catch (JSONException e)
        {
        }

        return json;
    }
}
