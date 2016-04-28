package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONObject;

public class LessonDisplayDto extends ItemDtoBase
{

    public LessonDisplayDto()
    {
        super();
    }

    public LessonDisplayDto(JSONObject json)
    {
        super(json);
    }
}
