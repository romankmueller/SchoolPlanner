using System;
using System.Collections.Generic;
using Bzs.Portable.DataTransferObjects.Lesson;
using Bzs.Server.ServerService;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Bzs.Server
{
    /// <summary>
    /// Represents the unit tests of the <see cref="LessonServerService" /> class.
    /// </summary>
    [TestClass]
    public sealed class LessonServerServiceTest
    {
        /// <summary>
        /// The service to test.
        /// </summary>
        private LessonServerService service;

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
            this.service = new LessonServerService();
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
        /// Tests the retrieval of a lesson with an invalid identifier and returns null.
        /// </summary>
        [TestMethod]
        public void GetLesson_InvalidId_ReturnsNull()
        {
            LessonEditDto item = this.service.GetLesson(Guid.NewGuid(), new Guid("FDB44E7C-AB09-4DF2-9CA9-C42D001E2957"));

            Assert.IsNull(item);
        }

        /// <summary>
        /// Tests the retrieval of the lessons of a day.
        /// </summary>
        [TestMethod]
        public void GetLessonOfDay_DayId_ReturnsValue()
        {
            List<LessonEditDto> items = this.service.GetLessonsOfDay(new Guid("49FA1196-56A6-45E1-92D5-869C192CB66C"), new Guid("FDB44E7C-AB09-4DF2-9CA9-C42D001E2957"));

            Assert.IsNotNull(items);
            Assert.AreEqual(1, items.Count);
        }

        /// <summary>
        /// Tests the retrieval of the lessons of a day.
        /// </summary>
        [TestMethod]
        public void GetLessonOfWeek_ReturnsValue()
        {
            List<LessonEditDto> items = this.service.GetLessonsOfWeek(new Guid("FDB44E7C-AB09-4DF2-9CA9-C42D001E2957"));

            Assert.IsNotNull(items);
            Assert.AreEqual(1, items.Count);
        }
    }
}
