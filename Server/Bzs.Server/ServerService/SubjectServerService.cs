using System;
using System.Collections.Generic;
using System.Linq;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Portable.DataTransferObjects.Subject;
using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents a subject server service.
    /// </summary>
    public sealed class SubjectServerService : ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="SubjectServerService" /> class.
        /// </summary>
        public SubjectServerService()
        {
        }

        /// <summary>
        /// Returns the subject lookup data.
        /// </summary>
        /// <returns></returns>
        public List<SubjectLookupDto> GetSubjectLookup()
        {
            List<SubjectLookupDto> data;
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                data = ctx.SubjectSet.Select(f => new SubjectLookupDto
                {
                    Id = f.Id,
                    Code = f.Code,
                    Caption = f.Caption
                }).ToList();
            }

            return data;
        }

        public ResultDto InsertUpdateSubject(SubjectEditDto itemToSave)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                SubjectEntity entity = ctx.SubjectSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity == null)
                {
                    entity = new SubjectEntity();
                    entity.Id = itemToSave.Id;
                    entity.Code = itemToSave.Code;
                    entity.Caption = itemToSave.Caption;
                    entity.ModDate = DateTime.Now;
                    entity.ModUser = Environment.UserName;
                    ctx.SubjectSet.Add(entity);
                }

                entity.Code = itemToSave.Code;
                entity.Caption = itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Deletes the subject.
        /// </summary>
        /// <param name="id">The subject identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteSubject(Guid id)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                SubjectEntity entity = ctx.SubjectSet.FirstOrDefault(f => f.Id == id);
                if (entity != null && !entity.LessonNavProp.Any())
                {
                    ctx.SubjectSet.Remove(entity);

                    ctx.SaveChanges();
                    result.Success = true;
                }
            }

            return result;
        }
    }
}
