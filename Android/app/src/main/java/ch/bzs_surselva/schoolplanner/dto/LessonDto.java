package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import ch.bzs_surselva.schoolplanner.helpers.StringHelper;

public final class LessonDto extends ItemDtoBase
{
    private static final String JsonDayId = "DayId";
    private static final String JsonFromDate = "FromDate";
    private static final String JsonToDate = "ToDate";
    private static final String JsonSubjectId = "SubjectId";
    private static final String JsonTeacherId = "TeacherId";
    private static final String JsonRoomId = "RoomId";
    private static final String JsonRemark = "Remark";
    private static final String JsonDayCaption = "DayCaption";

    private int dayOfWeek;
    private Date from;
    private Date to;
    private String subject;
    private String teacher;
    private String room;
    private String remark;

    public LessonDto()
    {
        super();

        this.dayOfWeek = 0;
        this.from = new Date();
        this.to = new Date();
        this.subject = "";
        this.teacher = "";
        this.room = "";
        this.remark = "";
    }

    public LessonDto(JSONObject json)
    {
        this();

        try
        {
            this.setSubject(json.getString(JsonSubjectId));
            this.setTeacher(json.getString(JsonTeacherId));
            this.setRoom(json.getString(JsonRoomId));
            this.setRemark(json.getString(JsonRemark));
        }
        catch (JSONException e)
        {
        }
    }

    public String getSubject()
    {
        return this.subject;
    }

    public void setSubject(String subject)
    {
        this.subject = StringHelper.nullToEmpty(subject);
    }

    public String getTeacher()
    {
        return this.teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = StringHelper.nullToEmpty(teacher);
    }

    public String getRoom()
    {
        return this.room;
    }

    public void setRoom(String room)
    {
        this.room = StringHelper.nullToEmpty(room);
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = StringHelper.nullToEmpty(remark);
    }

    public final JSONObject toJson()
    {
        JSONObject json = super.toJson();
        try
        {
            json.put(JsonDayId, this.dayOfWeek);
            json.put(JsonFromDate, this.from);
            json.put(JsonToDate, this.to);
            json.put(JsonSubjectId, this.getSubject());
            json.put(JsonTeacherId, this.getTeacher());
            json.put(JsonRoomId, this.getRoom());
            json.put(JsonRemark, this.getRemark());
        }
        catch (JSONException e)
        {
        }

        return json;
    }
}