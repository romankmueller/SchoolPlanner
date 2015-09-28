using System;
using System.Collections.Generic;
using System.Linq;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Portable.DataTransferObjects.Lesson;
using Bzs.Portable.Helpers;
using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents a lesson server service.
    /// </summary>
    public sealed class LessonServerService : ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="LessonServerService" /> class.
        /// </summary>
        public LessonServerService()
        {
        }

        /// <summary>
        /// Returns the lesson.
        /// </summary>
        /// <param name="id">The lesson identifier.</param>
        /// <returns>The lesson data.</returns>
        public LessonEditDto GetLesson(Guid id)
        {
            LessonEditDto data = null;
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                LessonEntity entity = ctx.LessonSet.FirstOrDefault(f => f.Id == id);
                if (entity != null)
                {
                    data = this.FillLessonEditDto(new LessonEditDto(), entity);
                    data.Remark = entity.Remark;
                }
            }

            return data;
        }

        /// <summary>
        /// Returns the lessons of a day.
        /// </summary>
        /// <param name="dayId">The day identifier.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The lessons of the day.</returns>
        public List<LessonEditDto> GetLessonsOfDay(Guid dayId, Guid accountId)
        {
            List<LessonEditDto> data = new List<LessonEditDto>();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                IQueryable<LessonEntity> entities = ctx.LessonSet.Where(f => f.DayId == dayId && f.AccountId == accountId);
                foreach (LessonEntity entity in entities)
                {
                    LessonEditDto item = this.FillLessonEditDto(new LessonEditDto(), entity);
                    data.Add(item);
                }
            }

            return data;
        }

        /// <summary>
        /// Returns the lessons of the week.
        /// </summary>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The lessons of the week.</returns>
        public List<LessonEditDto> GetLessonsOfWeek(Guid accountId)
        {
            List<LessonEditDto> data = new List<LessonEditDto>();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                IQueryable<LessonEntity> entities = ctx.LessonSet.Where(f => f.AccountId == accountId);
                foreach (LessonEntity entity in entities)
                {
                    LessonEditDto item = this.FillLessonEditDto(new LessonEditDto(), entity);
                    data.Add(item);
                }
            }

            return data;
        }

        /// <summary>
        /// Inserts or updates a lesson.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto InsertUpdateLesson(LessonEditDto itemToSave, Guid accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                LessonEntity entity = ctx.LessonSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity == null)
                {
                    entity = new LessonEntity();
                    entity.Id = itemToSave.Id;
                    entity.AccountId = accountId;
                    entity.DayId = itemToSave.DayId;
                    entity.FromDate = DateTimeHelper.StringToDateTime(itemToSave.FromDate);
                    entity.ToDate = DateTimeHelper.StringToDateTime(itemToSave.ToDate);
                    entity.SubjectId = itemToSave.SubjectId;
                    entity.Teacher = itemToSave.Teacher;
                    entity.Room = itemToSave.Room;
                    entity.Remark = itemToSave.Remark;
                    entity.ModDate = DateTime.Now;
                    entity.ModUser = Environment.UserName;

                    ctx.LessonSet.Add(entity);
                }

                entity.AccountId = accountId;
                entity.DayId = itemToSave.DayId;
                entity.FromDate = DateTimeHelper.StringToDateTime(itemToSave.FromDate);
                entity.ToDate = DateTimeHelper.StringToDateTime(itemToSave.ToDate);
                entity.SubjectId = itemToSave.SubjectId;
                entity.Teacher = itemToSave.Teacher;
                entity.Room = itemToSave.Room;
                entity.Remark = itemToSave.Remark;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Deletes a lesson.
        /// </summary>
        /// <param name="id">The lesson identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteLesson(Guid id)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                LessonEntity entity = ctx.LessonSet.FirstOrDefault(f => f.Id == id);
                if (entity != null)
                {
                    ctx.LessonSet.Remove(entity);
                    ctx.SaveChanges();
                    result.Success = true;
                }
            }

            return result;
        }

        /// <summary>
        /// Fills the lesson edit data transfer object.
        /// </summary>
        /// <param name="item">The item to fill.</param>
        /// <param name="entity">The entity.</param>
        /// <returns>The filled item.</returns>
        private LessonEditDto FillLessonEditDto(LessonEditDto item, LessonEntity entity)
        {
            item.Id = entity.Id;
            item.DayId = entity.DayId;
            item.DayCaption = entity.DayNavProp.Caption;
            item.FromDate = DateTimeHelper.DateTimeToString(entity.FromDate);
            item.ToDate = DateTimeHelper.DateTimeToString(entity.ToDate);
            item.SubjectId = entity.SubjectId;
            item.SubjectCode = entity.SubjectNavProp.Code;
            item.Teacher = entity.Teacher;
            item.Room = entity.Room;
            item.Remark = entity.Remark;
            return item;
        }
    }
}
