using System;
using System.Collections.Generic;
using System.Linq;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Portable.DataTransferObjects.Teacher;
using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents a teacher server service.
    /// </summary>
    public sealed class TeacherServerService : ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="TeacherServerService" /> class.
        /// </summary>
        public TeacherServerService()
        {
        }

        /// <summary>
        /// Returns the teacher lookup data.
        /// </summary>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The teacher lookup data.</returns>
        public List<TeacherLookupDto> GetTeacherLookup(Guid accountId)
        {
            List<TeacherLookupDto> data;
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                data = ctx.TeacherSet.Where(f => f.AccountId == null || f.AccountId == accountId).Select(f => new TeacherLookupDto
                {
                    Id = f.Id,
                    Code = f.Code,
                    Caption = f.Caption
                }).ToList();
            }

            return data;
        }

        /// <summary>
        /// Inserts a teacher.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto InsertTeacher(TeacherEditDto itemToSave, Guid? accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                TeacherEntity entity = ctx.TeacherSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity != null)
                {
                    result.Error = "ERR-TEACHER-ALREADY-EXISTS";
                    return result;
                }

                entity = new TeacherEntity();
                entity.Id = itemToSave.Id;
                entity.AccountId = accountId;
                entity.Code = itemToSave.Code;
                entity.Caption = itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;
                ctx.TeacherSet.Add(entity);

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Updates a teacher.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto UpdateTeacher(TeacherEditDto itemToSave, Guid? accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                TeacherEntity entity = ctx.TeacherSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity == null)
                {
                    result.Error = "ERR-TEACHER-NOT-EXISTS";
                    return result;
                }

                if (entity.AccountId == null || entity.AccountId != accountId)
                {
                    result.Error = "ERR-TEACHER-ACCOUNT-INVALID";
                    return result;
                }

                entity.Code = itemToSave.Code;
                entity.Caption = itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;
                ctx.TeacherSet.Add(entity);

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Deletes a teacher.
        /// </summary>
        /// <param name="id">The teacher identifier.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteTeacher(Guid id, Guid accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                TeacherEntity entity = ctx.TeacherSet.FirstOrDefault(f => f.Id == id);
                if (entity == null)
                {
                    result.Error = "ERR-TEACHER-NOT-EXISTS";
                    return result;
                }

                if (entity.AccountId == null)
                {
                    result.Error = "ERR-TEACHER-ACCOUNT-INDEPENDENT";
                    return result;
                }

                if (entity.AccountId != accountId)
                {
                    result.Error = "ERR-TEACHER-ACCOUNT-INVALID";
                    return result;
                }

                if (entity.LessonNavProp.Any())
                {
                    result.Error = "ERR-TEACHER-USED";
                    return result;
                }

                ctx.TeacherSet.Remove(entity);
                ctx.SaveChanges();
                result.Success = true;
                return result;
            }
        }
    }
}
