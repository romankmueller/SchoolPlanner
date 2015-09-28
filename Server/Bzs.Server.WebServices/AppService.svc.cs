using System;
using System.Security.Authentication;
using System.ServiceModel.Web;
using System.Text;
using System.Web;
using Bzs.Portable.DataTransferObjects;
using Bzs.Server.ServerService;
using Bzs.Server.WebServices.Authorization;

namespace Bzs.Server.WebServices
{
    /// <summary>
    /// Represents an application service.
    /// </summary>
    public sealed class AppService : IAppService
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="AppService" /> class.
        /// </summary>
        public AppService()
        {
        }

        /// <summary>
        /// Returns a value indicating whether the service is available.
        /// </summary>
        /// <returns>The service is available.</returns>
        public bool Ping()
        {
            this.SetResponseHeaderCacheExpiration();

            return true;
        }

        /// <summary>
        /// Represents a register service.
        /// </summary>
        /// <param name="data">The data to register.</param>
        /// <returns>The result.</returns>
        public ResultDto Register(RegisterDto data)
        {
            AccountServerService service = new AccountServerService();
            return service.Register(data);
        }

        /// <summary>
        /// Login to the application.
        /// </summary>
        /// <returns>The result.</returns>
        public ResultDto Login()
        {
            AccountPassword credentials = this.GetCredentialsFromRequest();
            AccountServerService service = new AccountServerService();
            return service.Login(credentials.Account, credentials.Password);
        }

        /// <summary>
        /// Sets the response header cache expiration.
        /// </summary>
        /// <param name="minutesOfExpiration">The minutes of expiration.</param>
        private void SetResponseHeaderCacheExpiration(int minutesOfExpiration = -1)
        {
            HttpContext.Current.Response.Cache.SetExpires(DateTime.UtcNow.AddMinutes(minutesOfExpiration));
            HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
            HttpContext.Current.Response.Cache.SetNoStore();
        }

        /// <summary>
        /// Returns the credentials from the request.
        /// </summary>
        /// <returns>The credentials.</returns>
        private AccountPassword GetCredentialsFromRequest()
        {
            if (WebOperationContext.Current != null)
            {
                foreach (object header in HttpContext.Current.Request.Headers)
                {
                    if (header.ToString() == "Authorization")
                    {
                        string headerItem = HttpContext.Current.Request.Headers[header.ToString()];
                        if (headerItem.StartsWith("Basic "))
                        {
                            string cred = Encoding.UTF8.GetString(Convert.FromBase64String(headerItem.Substring("Basic ".Length)));
                            string[] parts = cred.Split(':');
                            return new AccountPassword(parts[0], parts[1]);
                        }
                    }
                }
            }

            throw new AuthenticationException();
        }
    }
}
