using System.Collections.Generic;
using System.ServiceModel;
using System.ServiceModel.Web;
using Bzs.Portable.DataTransferObjects.Account;
using Bzs.Portable.DataTransferObjects.Authentication;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Portable.DataTransferObjects.Day;
using Bzs.Portable.DataTransferObjects.Lesson;
using Bzs.Portable.DataTransferObjects.Subject;

namespace Bzs.Server.CloudServices
{
    /// <summary>
    /// Represents an interface of an application service.
    /// </summary>
    [ServiceContract]
    public interface IAppService
    {
        /// <summary>
        /// Returns a ping.
        /// </summary>
        /// <returns>The ping.</returns>
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
        LoginResultDto Login();

        /// <summary>
        /// Retrieves the credentials.
        /// </summary>
        /// <param name="request">The retrieve credentials request.</param>
        [WebInvoke(UriTemplate = "RetrieveCredentials", Method = "PUT", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        void RetrieveCredentials(RetrievePasswordDto request);

        /// <summary>
        /// Returns the day lookup.
        /// </summary>
        /// <returns>The day lookup.</returns>
        [WebGet(UriTemplate = "GetDayLookup", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        List<DayLookupDto> GetDayLookup();

        /// <summary>
        /// Returns the subject lookup.
        /// </summary>
        /// <returns>The subject lookup.</returns>
        [WebGet(UriTemplate = "GetSubjectLookup", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        List<SubjectLookupDto> GetSubjectLookup();

        /// <summary>
        /// Inserts a subject.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns>The result.</returns>
        [WebInvoke(UriTemplate = "InsertSubject", Method = "PUT", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto InsertSubject(SubjectEditDto itemToSave);

        /// <summary>
        /// Updates a subject.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns></returns>
        [WebInvoke(UriTemplate = "UpdateSubject", Method = "POST", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto UpdateSubject(SubjectEditDto itemToSave);

        /// <summary>
        /// Deletes a subject.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <returns>The result.</returns>
        [WebInvoke(UriTemplate = "DeleteSubject", Method = "DELETE", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto DeleteSubject(string id);

        /// <summary>
        /// Returns the lesson.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <returns>The lesson.</returns>
        [WebGet(UriTemplate = "GetLesson/{id}", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        LessonEditDto GetLesson(string id);

        /// <summary>
        /// Returns the lessons of a day.
        /// </summary>
        /// <param name="id">The day identifier.</param>
        /// <returns>The lessons.</returns>
        [WebGet(UriTemplate = "GetLessonOfDay/{id}", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        List<LessonEditDto> GetLessonOfDay(string id);

        /// <summary>
        /// Returns the lessons of a week.
        /// </summary>
        /// <returns>The lessons.</returns>
        [WebGet(UriTemplate = "GetLessonOfWeek", ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        List<LessonEditDto> GetLessonOfWeek();

        /// <summary>
        /// Inserts a lesson.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns>The result.</returns>
        [WebInvoke(UriTemplate = "InsertLesson", Method = "PUT", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto InsertLesson(LessonEditDto itemToSave);

        /// <summary>
        /// Updates a lesson.
        /// </summary>
        /// <param name="itemToSave">The item to save.</param>
        /// <returns></returns>
        [WebInvoke(UriTemplate = "UpdateLesson", Method = "POST", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto UpdateLesson(LessonEditDto itemToSave);

        /// <summary>
        /// Deletes a lesson.
        /// </summary>
        /// <param name="id">The identifier.</param>
        /// <returns>The result.</returns>
        [WebInvoke(UriTemplate = "DeleteLesson/{id}", Method = "DELETE", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        [OperationContract]
        ResultDto DeleteLesson(string id);
    }
}
