using System.Collections.Generic;
using Bzs.Portable.DataTransferObjects.Day;
using Bzs.Server.ServerService;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Bzs.Server
{
    /// <summary>
    /// Represents the unit tests of the <see cref="DayServerService" /> class.
    /// </summary>
    [TestClass]
    public sealed class DayServerServiceTest
    {
        /// <summary>
        /// The service to test.
        /// </summary>
        private DayServerService service;

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
            this.service = new DayServerService();
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
        /// Tests the retrieval of the day lookup.
        /// </summary>
        [TestMethod]
        public void GetDayLookup_ReturnsValues()
        {
            List<DayLookupDto> result = this.service.GetDayLookup();

            Assert.IsNotNull(result);
            Assert.IsTrue(result.Count > 0);
        }
    }
}
