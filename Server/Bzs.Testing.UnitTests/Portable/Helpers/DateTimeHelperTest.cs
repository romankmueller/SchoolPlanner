using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Bzs.Portable.Helpers
{
    /// <summary>
    /// Represents the unit tests of the <see cref="DateTimeHelper" /> class.
    /// </summary>
    [TestClass]
    public sealed class DateTimeHelperTest
    {
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
        }

        /// <summary>
        /// Cleans up a unit test.
        /// </summary>
        [TestCleanup]
        public void TestCleanup()
        {
        }

        /// <summary>
        /// Tests the DateTimeToString method.
        /// </summary>
        [TestMethod]
        public void DateTimeToString_Value_ReturnsString()
        {
            DateTime dateTime = DateTime.Now;
            string dateTimeString = DateTimeHelper.DateTimeToString(dateTime);
            DateTime resultDateTime = DateTimeHelper.StringToDateTime(dateTimeString);
            
            Assert.AreEqual(dateTime, resultDateTime);
        }
    }
}
