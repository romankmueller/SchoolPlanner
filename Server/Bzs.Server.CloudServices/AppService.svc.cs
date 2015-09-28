using System;
using System.Collections.Generic;
using System.Security.Authentication;
using System.ServiceModel.Web;
using System.Text;
using System.Web;
using Bzs.Portable.DataTransferObjects.Account;
using Bzs.Portable.DataTransferObjects.Authentication;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Portable.DataTransferObjects.Day;
using Bzs.Portable.DataTransferObjects.Lesson;
using Bzs.Portable.DataTransferObjects.Subject;
using Bzs.Server.CloudServices.Authorization;
using Bzs.Server.ServerService;

namespace Bzs.Server.CloudServices
{
    /// <summary>
    /// Represents an application service.
    /// </summary>
    public class AppService : IAppService
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="AppService" /> class.
        /// </summary>
        public AppService()
        {
        }

        /// <summary>
        /// Returns a ping.
        /// </summary>
        /// <returns>The ping.</returns>
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
            this.SetResponseHeaderCacheExpiration();

            AccountServerService service = new AccountServerService();
            return service.Register(data);
        }

        /// <summary>
        /// Login to the application.
        /// </summary>
        /// <returns>The result.</returns>
        public LoginResultDto Login()
        {
            this.SetResponseHeaderCacheExpiration();

            AccountPassword credentials = this.GetCredentialsFromRequest();
            AccountServerService service = new AccountServerService();
            return service.Login(credentials.Account, credentials.Password);
        }

        /// <summary>
        /// Retrieves the credentials.
        /// </summary>
        /// <param name="request">The retrieve credentials request.</param>
        public void RetrieveCredentials(RetrievePasswordDto request)
        {
            this.SetResponseHeaderCacheExpiration();

            AccountServerService service = new AccountServerService();
            service.RetrieveCredentials(request.Email);
        }

        /// <summary>
        /// Returns the day lookup.
        /// </summary>
        /// <returns>The day lookup.</returns>
        public List<DayLookupDto> GetDayLookup()
        {
            this.SetResponseHeaderCacheExpiration(30);

            DayServerService service = new DayServerService();
            return service.GetDayLookup();
        }

        /// <summary>
        /// Returns the subject lookup.
        /// </summary>
        /// <returns>The subject lookup.</returns>
        public List<SubjectLookupDto> GetSubjectLookup()
        {
            this.SetResponseHeaderCacheExpiration(30);

            SubjectServerService service = new SubjectServerService();
            return service.GetSubjectLookup();
        }

        /// <summary>
        /// Inserts a subject.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns>The result.</returns>
        public ResultDto InsertSubject(SubjectEditDto itemToSave)
        {
            this.SetResponseHeaderCacheExpiration();

            SubjectServerService service = new SubjectServerService();
            return service.InsertUpdateSubject(itemToSave);
        }

        /// <summary>
        /// Updates a subject.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns></returns>
        public ResultDto UpdateSubject(SubjectEditDto itemToSave)
        {
            this.SetResponseHeaderCacheExpiration();

            SubjectServerService service = new SubjectServerService();
            return service.InsertUpdateSubject(itemToSave);
        }

        /// <summary>
        /// Deletes a subject.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteSubject(string id)
        {
            this.SetResponseHeaderCacheExpiration();

            SubjectServerService service = new SubjectServerService();
            return service.DeleteSubject(new Guid(id));
        }

        /// <summary>
        /// Returns the lesson.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <returns>The lesson.</returns>
        public LessonEditDto GetLesson(string id)
        {
            this.SetResponseHeaderCacheExpiration();

            LessonServerService service = new LessonServerService();
            return service.GetLesson(new Guid(id));
        }

        /// <summary>
        /// Returns the lessons of a day.
        /// </summary>
        /// <param name="id">The day identifier.</param>
        /// <returns>The lessons.</returns>
        public List<LessonEditDto> GetLessonOfDay(string id)
        {
            this.SetResponseHeaderCacheExpiration();

            AccountPassword credentials = this.GetCredentialsFromRequest();
            AccountServerService accountService = new AccountServerService();
            Guid accountId = accountService.GetAccountId(credentials.Account);
            LessonServerService service = new LessonServerService();
            return service.GetLessonsOfDay(new Guid(id), accountId);
        }

        /// <summary>
        /// Returns the lessons of a week.
        /// </summary>
        /// <returns>The lessons.</returns>
        public List<LessonEditDto> GetLessonOfWeek()
        {
            this.SetResponseHeaderCacheExpiration();

            AccountPassword credentials = this.GetCredentialsFromRequest();
            AccountServerService accountService = new AccountServerService();
            Guid accountId = accountService.GetAccountId(credentials.Account);
            LessonServerService service = new LessonServerService();
            return service.GetLessonsOfWeek(accountId);
        }

        /// <summary>
        /// Inserts a lesson.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns>The result.</returns>
        public ResultDto InsertLesson(LessonEditDto itemToSave)
        {
            this.SetResponseHeaderCacheExpiration();

            AccountPassword credentials = this.GetCredentialsFromRequest();
            AccountServerService accountService = new AccountServerService();
            Guid accountId = accountService.GetAccountId(credentials.Account);
            LessonServerService service = new LessonServerService();
            return service.InsertUpdateLesson(itemToSave, accountId);
        }

        /// <summary>
        /// Updates a lesson.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns></returns>
        public ResultDto UpdateLesson(LessonEditDto itemToSave)
        {
            this.SetResponseHeaderCacheExpiration();

            AccountPassword credentials = this.GetCredentialsFromRequest();
            AccountServerService accountService = new AccountServerService();
            Guid accountId = accountService.GetAccountId(credentials.Account);
            LessonServerService service = new LessonServerService();
            return service.InsertUpdateLesson(itemToSave, accountId);
        }

        /// <summary>
        /// Deletes a lesson.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <returns>The result.</returns>
        public ResultDto DeleteLesson(string id)
        {
            this.SetResponseHeaderCacheExpiration();

            LessonServerService service = new LessonServerService();
            return service.DeleteLesson(new Guid(id));
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
