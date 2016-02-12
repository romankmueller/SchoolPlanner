using System;
using System.Collections.Generic;
using System.Linq;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Portable.DataTransferObjects.Room;
using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents a room server service.
    /// </summary>
    public sealed class RoomServerService : ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="RoomServerService" /> class.
        /// </summary>
        public RoomServerService()
        {
        }

        /// <summary>
        /// Returns the room lookup data.
        /// </summary>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The room lookup data.</returns>
        public List<RoomLookupDto> GetRoomLookup(Guid accountId)
        {
            List<RoomLookupDto> data;
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                data = ctx.RoomSet.Where(f => f.AccountId == null || f.AccountId == accountId).Select(f => new RoomLookupDto
                {
                    Id = f.Id,
                    Code = f.Code,
                    Caption = f.Caption
                }).ToList();
            }

            return data;
        }

        /// <summary>
        /// Inserts a room.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto InsertRoom(RoomEditDto itemToSave, Guid? accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                RoomEntity entity = ctx.RoomSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity != null)
                {
                    result.Error = "ERR-ROOM-ALREADY-EXISTS";
                    return result;
                }

                entity = new RoomEntity();
                entity.Id = itemToSave.Id;
                entity.AccountId = accountId;
                entity.Code = itemToSave.Code;
                entity.Caption = itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;
                ctx.RoomSet.Add(entity);

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Updates a room.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto UpdateRoom(RoomEditDto itemToSave, Guid? accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                RoomEntity entity = ctx.RoomSet.FirstOrDefault(f => f.Id == itemToSave.Id);
                if (entity == null)
                {
                    result.Error = "ERR-ROOM-NOT-EXISTS";
                    return result;
                }

                if (entity.AccountId == null || entity.AccountId != accountId)
                {
                    result.Error = "ERR-ROOM-ACCOUNT-INVALID";
                    return result;
                }

                entity.Code = itemToSave.Code;
                entity.Caption = itemToSave.Caption;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;
                ctx.RoomSet.Add(entity);

                ctx.SaveChanges();
                result.Success = true;
            }

            return result;
        }

        /// <summary>
        /// Deletes the room.
        /// </summary>
        /// <param name="id">The room identifier.</param>
        /// <param name="accountId">The account identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteRoom(Guid id, Guid accountId)
        {
            ResultDto result = new ResultDto();
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                RoomEntity entity = ctx.RoomSet.FirstOrDefault(f => f.Id == id);
                if (entity == null)
                {
                    result.Error = "ERR-ROOM-NOT-EXISTS";
                    return result;
                }

                if (entity.AccountId == null)
                {
                    result.Error = "ERR-ROOM-ACCOUNT-INDEPENDENT";
                    return result;
                }

                if (entity.AccountId != accountId)
                {
                    result.Error = "ERR-ROOM-ACCOUNT-INVALID";
                    return result;
                }

                if (entity.LessonNavProp.Any())
                {
                    result.Error = "ERR-ROOM-USED";
                    return result;
                }

                ctx.RoomSet.Remove(entity);
                ctx.SaveChanges();
                result.Success = true;
                return result;
            }
        }
    }
}
