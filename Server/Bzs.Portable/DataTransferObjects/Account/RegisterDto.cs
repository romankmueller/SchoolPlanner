using System.Runtime.Serialization;

namespace Bzs.Portable.DataTransferObjects.Account
{
    /// <summary>
    /// Represents a register data transfer object.
    /// </summary>
    [DataContract]
    public sealed class RegisterDto
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="RegisterDto" /> class.
        /// </summary>
        public RegisterDto()
        {
        }

        /// <summary>
        /// Gets or sets the account.
        /// </summary>
        [DataMember]
        public string Account { get; set; }

        /// <summary>
        /// Gets or sets the password.
        /// </summary>
        [DataMember]
        public string Password { get; set; }

        /// <summary>
        /// Gets or sets the email address.
        /// </summary>
        [DataMember]
        public string Email { get; set; }
    }
}
