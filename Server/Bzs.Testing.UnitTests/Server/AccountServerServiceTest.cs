using Bzs.Portable.DataTransferObjects.Authentication;
using Bzs.Server.ServerService;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Bzs.Server
{
    /// <summary>
    /// Represents the unit tests of the <see cref="AccountServerService" /> class.
    /// </summary>
    [TestClass]
    public sealed class AccountServerServiceTest
    {
        /// <summary>
        /// The service to test.
        /// </summary>
        private AccountServerService service;

        /// <summary>
        /// Initializes the test class.
        /// </summary>
        /// <param name="context">The test context.</param>
        [ClassInitialize]
        public static void ClassInitialize(TestContext context)
        {
        }

        /// <summary>
        /// Cleans up the test class.
        /// </summary>
        [ClassCleanup]
        public static void ClassCleanup()
        {
        }

        /// <summary>
        /// Initializes a unit test.
        /// </summary>
        [TestInitialize]
        public void TestInitialize()
        {
            this.service = new AccountServerService();
        }

        /// <summary>
        /// Cleans up a unit test.
        /// </summary>
        [TestCleanup]
        public void TestCleanup()
        {
            this.service = null;
        }

        /// <summary>
        /// Tests the login with a valid user and a valid password.
        /// </summary>
        [TestMethod]
        public void Login_ValidUser_ValidPassword_ReturnsSuccess()
        {
            LoginResultDto result = this.service.Login("test", "test");

            Assert.IsNotNull(result);
            Assert.IsTrue(result.Success);
        }

        /// <summary>
        /// Tests the login with an invalid user and a valid password.
        /// </summary>
        [TestMethod]
        public void Login_InvalidUser_ValidPassword_ReturnsSuccess()
        {
            LoginResultDto result = this.service.Login("account", "test");

            Assert.IsNotNull(result);
            Assert.IsFalse(result.Success);
        }

        /// <summary>
        /// Tests the login with an valid user and an invalid password.
        /// </summary>
        [TestMethod]
        public void Login_ValidUser_InvalidPassword_ReturnsSuccess()
        {
            LoginResultDto result = this.service.Login("test", "password");

            Assert.IsNotNull(result);
            Assert.IsFalse(result.Success);
        }

        /// <summary>
        /// Tests the retrieval of the credentials.
        /// </summary>
        [Ignore]
        [TestMethod]
        public void RetrieveCredentials_ValidEmail()
        {
            this.service.RetrieveCredentials("roman.mueller@muellerchur.ch");
        }
    }
}
