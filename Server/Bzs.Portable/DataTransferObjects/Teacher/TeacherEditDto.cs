using System;
using System.Runtime.Serialization;
using Bzs.Portable.DataTransferObjects.Base;

namespace Bzs.Portable.DataTransferObjects.Teacher
{
    /// <summary>
    /// Represents a room edit data transfer object.
    /// </summary>
    [DataContract]
    public sealed class TeacherEditDto : ItemDtoBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="TeacherEditDto" /> class.
        /// </summary>
        public TeacherEditDto()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="TeacherEditDto" /> class.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <param name="code">The code.</param>
        /// <param name="caption">The caption.</param>
        public TeacherEditDto(Guid id, string code, string caption) : base(id)
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
