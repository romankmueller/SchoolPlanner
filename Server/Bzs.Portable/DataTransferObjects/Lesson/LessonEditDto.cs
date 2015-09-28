using System;
using System.Runtime.Serialization;
using Bzs.Portable.DataTransferObjects.Base;

namespace Bzs.Portable.DataTransferObjects.Lesson
{
    /// <summary>
    /// Represents a lesson edit data transfer object.
    /// </summary>
    [DataContract]
    public sealed class LessonEditDto : ItemDtoBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="LessonEditDto" /> class.
        /// </summary>
        public LessonEditDto()
        {
        }

        /// <summary>
        /// Gets or sets the day identifier.
        /// </summary>
        [DataMember]
        public Guid DayId { get; set; }

        /// <summary>
        /// Gets or sets the day caption.
        /// </summary>
        [DataMember]
        public string DayCaption { get; set; }

        /// <summary>
        /// Gets or sets the from date.
        /// </summary>
        [DataMember]
        public string FromDate { get; set; }

        /// <summary>
        /// Gets or sets the to date.
        /// </summary>
        [DataMember]
        public string ToDate { get; set; }

        /// <summary>
        /// The subject identifier.
        /// </summary>
        [DataMember]
        public Guid SubjectId { get; set; }

        /// <summary>
        /// Gets or sets the subject code.
        /// </summary>
        [DataMember]
        public string SubjectCode { get; set; }

        /// <summary>
        /// Gets or sets the teacher.
        /// </summary>
        [DataMember]
        public string Teacher { get; set; }

        /// <summary>
        /// Gets or sets the room.
        /// </summary>
        [DataMember]
        public string Room { get; set; }

        /// <summary>
        /// Gets or sets the remark.
        /// </summary>
        [DataMember]
        public string Remark { get; set; }
    }
}
