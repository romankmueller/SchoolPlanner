using System;
using System.Runtime.Serialization;
using Bzs.Portable.DataTransferObjects.Base;

namespace Bzs.Portable.DataTransferObjects.Teacher
{
    /// <summary>
    /// Represents a teacher lookup data transfer object.
    /// </summary>
    [DataContract]
    public sealed class TeacherLookupDto : ItemDtoBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="TeacherLookupDto" /> class.
        /// </summary>
        public TeacherLookupDto()
        {
            this.Code = string.Empty;
            this.Caption = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="TeacherLookupDto" /> class.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <param name="code">The code.</param>
        /// <param name="caption">The caption.</param>
        public TeacherLookupDto(Guid id, string code, string caption) : base(id)
        {
            this.Code = code;
            this.Caption = caption;
        }

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
