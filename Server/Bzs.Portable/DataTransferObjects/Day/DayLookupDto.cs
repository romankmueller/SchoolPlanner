using System;
using System.Runtime.Serialization;
using Bzs.Portable.DataTransferObjects.Base;

namespace Bzs.Portable.DataTransferObjects.Day
{
    /// <summary>
    /// Represents a day lookup data transfer object.
    /// </summary>
    [DataContract]
    public sealed class DayLookupDto : ItemDtoBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="DayLookupDto" /> class.
        /// </summary>
        public DayLookupDto()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="DayLookupDto" /> class.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <param name="order">The order position.</param>
        /// <param name="code">The code.</param>
        /// <param name="caption">The caption.</param>
        public DayLookupDto(Guid id, int order, string code, string caption) : base(id)
        {
            this.Order = order;
            this.Code = code;
            this.Caption = caption;
        }

        /// <summary>
        /// Gets or sets the order.
        /// </summary>
        [DataMember]
        public int Order { get; set; }

        /// <summary>
        /// Gets or sets the code.
        /// </summary>
        [DataMember]
        public string Code { get; set; }

        /// <summary>
        /// Gets or sets the caption.
        /// </summary>
        [DataMember]
        public string Caption { get; set; }
    }
}
