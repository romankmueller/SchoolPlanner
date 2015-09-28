namespace Bzs.Server.WebServices.Authorization
{
    /// <summary>
    /// Represents an account and password.
    /// </summary>
    public sealed class AccountPassword
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="AccountPassword" /> class.
        /// </summary>
        /// <param name="account">The account.</param>
        /// <param name="password">The password.</param>
        public AccountPassword(string account, string password)
        {
            this.Account = account;
            this.Password = password;
        }

        /// <summary>
        /// Gets the account.
        /// </summary>
        public string Account { get; private set; }

        /// <summary>
        /// Gets the password.
        /// </summary>
        public string Password { get; private set; }
    }
}