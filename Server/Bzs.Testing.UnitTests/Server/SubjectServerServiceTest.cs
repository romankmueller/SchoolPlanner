using System.Collections.Generic;
using Bzs.Portable.DataTransferObjects.Subject;
using Bzs.Server.ServerService;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Bzs.Server
{
    /// <summary>
    /// Represents the unit tests of the <see cref="SubjectServerService" /> class.
    /// </summary>
    [TestClass]
    public sealed class SubjectServerServiceTest
    {
        /// <summary>
        /// The service to test.
        /// </summary>
        private SubjectServerService service;

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
            this.service = new SubjectServerService();
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
        /// Tests the retrieval of the subject lookup.
        /// </summary>
        [TestMethod]
        public void GetSubjectLookup_ReturnsValues()
        {
            List<SubjectLookupDto> result = this.service.GetSubjectLookup();

            Assert.IsNotNull(result);
            Assert.IsTrue(result.Count > 0);
        }
    }
}
