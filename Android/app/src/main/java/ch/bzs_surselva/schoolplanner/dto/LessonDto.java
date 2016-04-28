package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.UUID;

import ch.bzs_surselva.schoolplanner.helpers.StringHelper;

public final class LessonDto extends ItemDtoBase
{
   /* private static final String JsonDayOfWeek = "DayOfWeek";
    private static final String JsonFrom = "From";
    private static final String JsonTo = "To";*/
    private static final String JsonSubject = "Subject";
    private static final String JsonTeacher = "Teacher";
    private static final String JsonRoom = "Room";
    private static final String JsonRemark = "Remark";

   // private int dayOfWeek;
   // private Date from;
    //private Date to;
    private String subject;
    private String teacher;
    private String room;
    private String remark;

    public LessonDto()
    {
        super();
        //this.dayOfWeek = 0;
       // this.from = new Date();
        //this.to = new Date();
        this.subject = "";
        this.teacher = "";
        this.room = "";
        this.remark = "";

    }

    public LessonDto(UUID id,/* String dayOfWeek, String from, String to,*/ String subject, String teacher, String room, String remark)
    {
        super(id);

        //this.setDayOfWeek(dayOfWeek);
       // this.from = new Date();
       // this.setTo( new Date());
        this.setSubject(subject);
        this.setTeacher(teacher);
        this.setRoom(room);
        this.setRemark(remark);
    }

    public LessonDto(JSONObject json)
    {
        super(json);

        try
        {
            this.setSubject(json.getString(JsonSubject));
            this.setTeacher(json.getString(JsonTeacher));
            this.setRoom(json.getString(JsonRoom));
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
           // json.put(JsonDayOfWeek, this.dayOfWeek);
           // json.put(JsonFrom, this.from);
           // json.put(JsonTo, this.to);
            json.put(JsonSubject, this.getSubject());
            json.put(JsonTeacher, this.getTeacher());
            json.put(JsonRoom, this.getRoom());
            json.put(JsonRemark, this.getRemark());
        }
        catch (JSONException e)
        {
        }

        return json;
    }
}