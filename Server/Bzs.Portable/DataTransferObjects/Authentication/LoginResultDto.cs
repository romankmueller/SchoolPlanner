using System;
using System.Runtime.Serialization;
using Bzs.Portable.DataTransferObjects.Base;

namespace Bzs.Portable.DataTransferObjects.Authentication
{
    /// <summary>
    /// Represents the login result data transfer object.
    /// </summary>
    [DataContract]
    public sealed class LoginResultDto : ResultDto
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="LoginResultDto" /> class.
        /// </summary>
        public LoginResultDto()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="LoginResultDto" /> class.
        /// </summary>
        /// <param name="sessionId">The session identifier.</param>
        public LoginResultDto(Guid sessionId)
        {
            if (sessionId != Guid.Empty)
            {
                this.Success = true;
                this.SessionId = sessionId;
            }
            else
            {
                this.Success = false;
            }
        }

        /// <summary>
        /// Gets or sets the session identifier.
        /// </summary>
        [DataMember]
        public Guid? SessionId { get; set; }
    }
}
