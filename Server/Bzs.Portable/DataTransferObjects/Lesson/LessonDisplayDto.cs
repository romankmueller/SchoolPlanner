using System;
using System.Runtime.Serialization;
using Bzs.Portable.DataTransferObjects.Base;

namespace Bzs.Portable.DataTransferObjects.Lesson
{
    /// <summary>
    /// Represents a lesson display data transfer object.
    /// </summary>
    [DataContract]
    public sealed class LessonDisplayDto : ItemDtoBase
    {
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
        /// Gets or sets the subject code.
        /// </summary>
        [DataMember]
        public string SubjectCode { get; set; }

        /// <summary>
        /// Gets or sets the subject caption.
        /// </summary>
        [DataMember]
        public string SubjectCaption { get; set; }

        /// <summary>
        /// Gets or sets the teacher code.
        /// </summary>
        [DataMember]
        public string TeacherCode { get; set; }

        /// <summary>
        /// Gets or sets the teacher caption.
        /// </summary>
        [DataMember]
        public string TeacherCaption { get; set; }

        /// <summary>
        /// Gets or sets the room code.
        /// </summary>
        [DataMember]
        public string RoomCode { get; set; }

        /// <summary>
        /// Gets or sets the room caption.
        /// </summary>
        [DataMember]
        public string RoomCaption { get; set; }

        /// <summary>
        /// Gets or sets the remark.
        /// </summary>
        [DataMember]
        public string Remark { get; set; }
    }
}
