using System.Runtime.Serialization;

namespace Bzs.Portable.DataTransferObjects.Base
{
    /// <summary>
    /// Represents a result data transfer object.
    /// </summary>
    [DataContract]
    public class ResultDto
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="ResultDto" /> class.
        /// </summary>
        public ResultDto()
        {
            this.Success = false;
            this.Error = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="ResultDto" /> class.
        /// </summary>
        /// <param name="success">The success flag.</param>
        public ResultDto(bool success) : this()
        {
            this.Success = success;
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="ResultDto" /> class.
        /// </summary>
        /// <param name="error">The error.</param>
        public ResultDto(string error) : this()
        {
            this.Error = error;
        }

        /// <summary>
        /// Gets or sets a value indicating whether the result was successful.
        /// </summary>
        [DataMember]
        public bool Success { get; set; }

        /// <summary>
        /// Gets or sets the error.
        /// </summary>
        [DataMember]
        public string Error { get; set; }
    }
}
