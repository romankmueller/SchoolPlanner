using System;
using System.Runtime.Serialization;

namespace Bzs.Portable.DataTransferObjects.Base
{
    /// <summary>
    /// Represents a base item data transfer object.
    /// </summary>
    [DataContract]
    public abstract class ItemDtoBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="ItemDtoBase" /> class.
        /// </summary>
        protected ItemDtoBase()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="ItemDtoBase" /> class.
        /// </summary>
        /// <param name="id">The identifier.</param>
        protected ItemDtoBase(Guid id)
        {
            this.Id = id;
        }

        /// <summary>
        /// Gets or sets the identifier.
        /// </summary>
        [DataMember]
        public Guid Id { get; set; }
    }
}
