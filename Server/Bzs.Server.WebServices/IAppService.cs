using System.ServiceModel;
using System.ServiceModel.Web;
using Bzs.Portable.DataTransferObjects;

namespace Bzs.Server.WebServices
{
    /// <summary>
    /// Represents an interface of an application service.
    /// </summary>
    [ServiceContract]
    public interface IAppService
    {
        /// <summary>
        /// Returns a value indicating whether the service is available.
        /// </summary>
        /// <returns>The service is available.</returns>
        [WebGet(UriTemplate = "Ping", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        bool Ping();

        /// <summary>
        /// Represents a register service.
        /// </summary>
        /// <param name="data">The data to register.</param>
        /// <returns>The result.</returns>
        [WebInvoke(UriTemplate = "Register", Method = "PUT", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto Register(RegisterDto data);

        /// <summary>
        /// Login to the application.
        /// </summary>
        /// <returns>The result.</returns>
        [WebGet(UriTemplate = "Login", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto Login();
    }
}
