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
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The subject lookup data.</returns>
        public List<SubjectLookupDto> GetSubjectLookup(Guid accountId)
        {
            List<SubjectLookupDto> data;
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                data = ctx.SubjectSet.Where(f => f.AccountId == null || f.AccountId == accountId).Select(f => new SubjectLookupDto
                {
                    Id = f.Id,
                    Code = f.Code,
                    Caption = f.Caption
                }).ToList();
            }

            return data;
        }

        /// <summary>
        /// Inserts a subject.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto InsertSubject(SubjectEditDto itemToSave, Guid accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                SubjectEntity entity = ctx.SubjectSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity != null)
                {
                    result.Error = "ERR-SUBJECT-ALREADY-EXISTS";
                    return result;
                }

                entity = new SubjectEntity();
                entity.Id = itemToSave.Id;
                entity.AccountId = accountId;
                entity.Code = itemToSave.Code.Length > 10 ? itemToSave.Code.Substring(0, 9) : itemToSave.Code;
                entity.Caption = itemToSave.Caption.Length > 50 ? itemToSave.Caption.Substring(0, 49) : itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;
                ctx.SubjectSet.Add(entity);

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Updates a subject.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto UpdateSubject(SubjectEditDto itemToSave, Guid? accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                SubjectEntity entity = ctx.SubjectSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity == null)
                {
                    result.Error = "ERR-SUBJECT-NOT-EXISTS";
                    return result;
                }

                if (entity.AccountId == null || entity.AccountId != accountId)
                {
                    result.Error = "ERR-SUBJECT-ACCOUNT-INVALID";
                    return result;
                }

                entity.Code = itemToSave.Code.Length > 10 ? itemToSave.Code.Substring(0, 9) : itemToSave.Code;
                entity.Caption = itemToSave.Caption.Length > 50 ? itemToSave.Caption.Substring(0, 49) : itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;
                ctx.SubjectSet.Add(entity);

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Deletes a subject.
        /// </summary>
        /// <param name="id">The subject identifier.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteSubject(Guid id, Guid accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                SubjectEntity entity = ctx.SubjectSet.FirstOrDefault(f => f.Id == id);
                if (entity == null)
                {
                    result.Error = "ERR-SUBJECT-NOT-EXISTS";
                    return result;
                }

                if (entity.AccountId == null)
                {
                    result.Error = "ERR-SUBJECT-ACCOUNT-INDEPENDENT";
                    return result;
                }

                if (entity.AccountId != accountId)
                {
                    result.Error = "ERR-SUBJECT-ACCOUNT-INVALID";
                    return result;
                }

                if (entity.LessonNavProp.Any())
                {
                    result.Error = "ERR-SUBJECT-USED";
                    return result;
                }

                ctx.SubjectSet.Remove(entity);
                ctx.SaveChanges();
                result.Success = true;
                return result;
            }
        }

        /// <summary>
        /// Returns a value indicating whether the subject exists.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <param name="ctx">The entity container.</param>
        /// <returns>The subject exists.</returns>
        public bool SubjectExists(Guid id, BzsEntityContainer ctx)
        {
            return ctx.SubjectSet.Any(f => f.Id == id);
        }
    }
}
