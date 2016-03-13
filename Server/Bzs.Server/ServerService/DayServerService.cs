using System;
using System.Collections.Generic;
using System.Linq;
using Bzs.Portable.DataTransferObjects.Day;
using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents a day server service.
    /// </summary>
    public sealed class DayServerService : ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="DayServerService" /> class.
        /// </summary>
        public DayServerService()
        {
        }

        /// <summary>
        /// Returns the day lookup.
        /// </summary>
        /// <returns>The day lookup.</returns>
        public List<DayLookupDto> GetDayLookup()
        {
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                return ctx.DaySet.Select(f => new DayLookupDto
                {
                    Id = f.Id,
                    Order = f.Order,
                    Code = f.Code,
                    Caption = f.Caption
                }).ToList();    
            }
        }

        /// <summary>
        /// Returns a value indicating whether the day exists.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <param name="ctx">The entity container.</param>
        /// <returns>The day exists.</returns>
        public bool DayExists(Guid id, BzsEntityContainer ctx)
        {
            return ctx.DaySet.Any(f => f.Id == id);
        }
    }
}
