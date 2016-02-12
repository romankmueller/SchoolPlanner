using System;
using System.Collections.Generic;
using Bzs.Portable.DataTransferObjects.Room;
using Bzs.Server.ServerService;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Bzs.Server
{
    /// <summary>
    /// Represents the unit tests of the <see cref="RoomServerService" /> class.
    /// </summary>
    [TestClass]
    public sealed class RoomServerServiceTest
    {
        /// <summary>
        /// The service to test.
        /// </summary>
        private RoomServerService service;

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
            this.service = new RoomServerService();
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
        /// Tests the method.
        /// </summary>
        [TestMethod]
        public void GetRoomLookup_ReturnsValues()
        {
            List<RoomLookupDto> result = this.service.GetRoomLookup(new Guid("FDB44E7C-AB09-4DF2-9CA9-C42D001E2957"));

            Assert.IsNotNull(result);
            Assert.IsTrue(result.Count > 0);
        }
    }
}
