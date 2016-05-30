package ch.bzs_surselva.schoolplanner.dto;

import org.json.JSONException;
import org.json.JSONObject;

public final class LessonDisplayDto extends ItemDtoBase
{
    private final String JsonDayId = "DayId";
    private final String JsonDayCaption = "DayCaption";
    //private final String JsonFromDate = "FromDate";
   //private final String JsonToDate = "ToDate";
    private final String JsonTime = "Time";
    private final String JsonSubjectCaption = "SubjectCaption";
    private final String JsonTeacherCaption = "TeacherCaption";
    private final String JsonRoomCaption = "RoomCaption";
    private final String JsonRemark = "Remark";

   private String dayId;
    private String dayCaption;
   // private String fromDate;
   // private String toDate;
    private String time;
    private String subjectCaption;
    private String teacherCaption;
    private String roomCaption;
    private String remark;

    public LessonDisplayDto()
    {
        super();
        this.dayId = "";
        this.dayCaption = "";
        //this.fromDate = "";
        //this.toDate = "";
        this.time = "";
        this.subjectCaption = "";
        this.teacherCaption = "";
        this.roomCaption = "";
        this.remark = "";
    }

    public LessonDisplayDto(JSONObject json)
    {
        super(json);
        try
        {   this.dayId = json.getString(JsonDayId);
            this.dayCaption = json.getString(JsonDayCaption);
           // this.fromDate = json.getString(JsonFromDate);
           // this.toDate = json.getString(JsonToDate);
            this.time = json.getString(JsonTime);
            this.subjectCaption = json.getString(JsonSubjectCaption);
            this.teacherCaption = json.getString(JsonTeacherCaption);
            this.roomCaption = json.getString(JsonRoomCaption);
            this.remark = json.getString(JsonRemark);
        }
        catch (JSONException e)
        {
            System.out.println(e.toString());
        }

    }
    public String getDayId(){return this.dayId;}
    public String getDayCaption(){return this.dayCaption; }
   // public String getFromDate(){ return this.fromDate;}
    //public String getToDate(){return this.toDate;}

    public String getTime(){return this.time;}
    public String getSubjectCaption()
    {
        return this.subjectCaption;
    }
    public String getTeacherCaption()
    {
        return this.teacherCaption;
    }
    public String getRoomCaption()
    {
        return this.roomCaption;
    }
    public String getRemark()
    {
        return this.remark;
    }

    public String toString()
    {
        return this.remark;
    }
}
