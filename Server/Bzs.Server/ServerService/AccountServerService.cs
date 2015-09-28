using System;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using Bzs.Portable.DataTransferObjects.Account;
using Bzs.Portable.DataTransferObjects.Authentication;
using Bzs.Portable.DataTransferObjects.Base;
using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents an account server service.
    /// </summary>
    public sealed class AccountServerService : ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="AccountServerService" /> class.
        /// </summary>
        public AccountServerService()
        {
        }

        /// <summary>
        /// Registers the data.
        /// </summary>
        /// <param name="data">The data to register.</param>
        /// <returns>The result.</returns>
        public ResultDto Register(RegisterDto data)
        {
            if (data == null)
            {
                return new ResultDto("No data provided.");
            }

            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                if (ctx.AccountSet.Count(f => f.Account == data.Account) > 0)
                {
                    return new ResultDto("The account already exists.");
                }

                AccountEntity entity = new AccountEntity();
                entity.Id = Guid.NewGuid();
                entity.Account = data.Account;
                entity.Password = data.Password;
                entity.Email = data.Email;
                entity.ModDate = DateTime.Now;
                entity.ModUser = Environment.UserName;

                ctx.AccountSet.Add(entity);
                ctx.SaveChanges();
                return new ResultDto(true);
            }
        }

        /// <summary>
        /// Returns the login result.
        /// </summary>
        /// <param name="account">The account.</param>
        /// <param name="password">The password.</param>
        /// <returns>The login result.</returns>
        public LoginResultDto Login(string account, string password)
        {
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                AccountEntity accountEntity = ctx.AccountSet.FirstOrDefault(f => f.Account == account && f.Password == password);
                if (accountEntity != null)
                {
                    return new LoginResultDto(Guid.NewGuid());
                }
                
                return new LoginResultDto();
            }
        }

        /// <summary>
        /// Returns the account identifier.
        /// </summary>
        /// <param name="account">The account.</param>
        /// <returns>The account identifier.</returns>
        public Guid GetAccountId(string account)
        {
            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                AccountEntity entity = ctx.AccountSet.FirstOrDefault(f => f.Account == account);
                if (entity != null)
                {
                    return entity.Id;
                }
            }

            return Guid.Empty;
        }

        /// <summary>
        /// Retrieves the credentials.
        /// </summary>
        /// <param name="email">The email address.</param>
        public void RetrieveCredentials(string email)
        {
            if (email == null)
            {
                return;
            }

            bool sendMail = false;
            string account = string.Empty;
            string password = string.Empty;

            using (BzsEntityContainer ctx = this.CreateContainer())
            {
                AccountEntity accountEntity = ctx.AccountSet.FirstOrDefault(f => f.Email == email);
                if (accountEntity != null)
                {
                    sendMail = true;
                    account = accountEntity.Account;
                    password = accountEntity.Password;
                }
            }

            if (sendMail)
            {
                string sendEmailHost = null;
                string sendEmailAddress = null;
                string sendEmailAccount = null;
                string sendEmailPassword = null;
                string sendEmailDomain = null;

                foreach (string key in ConfigurationManager.AppSettings.Keys)
                {
                    switch (key)
                    {
                        case "SendEmailHost":
                            sendEmailHost = ConfigurationManager.AppSettings[key];
                            break;
                        case "SendEmailAddress":
                            sendEmailAddress = ConfigurationManager.AppSettings[key];
                            break;
                        case "SendEmailAccount":
                            sendEmailAccount = ConfigurationManager.AppSettings[key];
                            break;
                        case "SendEmailPassword":
                            sendEmailPassword = ConfigurationManager.AppSettings[key];
                            break;
                        case "SendEmailDomain":
                            sendEmailDomain = ConfigurationManager.AppSettings[key];
                            break;
                    }
                }
                
                StringBuilder message = new StringBuilder();
                message.AppendLine("Someone has requested your credentials for the BZS Surselva application.");
                message.AppendLine();
                message.AppendLine("Account: " + account);
                message.AppendLine("Password: " + password);
                message.AppendLine();
                message.AppendLine("Kind regards");
                message.AppendLine("BZS Surselva");
                message.AppendLine("Administrator");

                MailMessage mail = new MailMessage(sendEmailAddress, email);
                SmtpClient client = new SmtpClient();
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                client.UseDefaultCredentials = false;
                client.EnableSsl = true;
                client.Credentials = new NetworkCredential(sendEmailAccount, sendEmailPassword, sendEmailDomain);
                client.Host = sendEmailHost;
                mail.Subject = "BZS Surselva: Your Account";
                mail.Body = message.ToString();
                try
                {
                    client.Send(mail);
                }
                catch (SmtpException)
                {
                }
            }
        }
    }
}
