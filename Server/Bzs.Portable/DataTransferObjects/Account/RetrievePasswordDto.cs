using System.Runtime.Serialization;

namespace Bzs.Portable.DataTransferObjects.Account
{
    /// <summary>
    /// Represents a retrieve password data transfer object.
    /// </summary>
    [DataContract]
    public sealed class RetrievePasswordDto
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="RetrievePasswordDto" /> class.
        /// </summary>
        public RetrievePasswordDto()
        {
        }

        /// <summary>
        /// Gets or sets the email address.
        /// </summary>
        [DataMember]
        public string Email { get; set; }
    }
}
