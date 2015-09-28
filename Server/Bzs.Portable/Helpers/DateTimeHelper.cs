using System;
using System.Globalization;

namespace Bzs.Portable.Helpers
{
    /// <summary>
    /// Represents a date time helper.
    /// </summary>
    public static class DateTimeHelper
    {
        /// <summary>
        /// Returns a date/time string representation from a date/time.
        /// </summary>
        /// <param name="dateTime">The date/time.</param>
        /// <returns>The date/time string representation.</returns>
        public static string DateTimeToString(DateTime dateTime)
        {
            return dateTime.ToString("o");
        }

        /// <summary>
        /// Returns a date/time from a date/time string representation.
        /// </summary>
        /// <param name="dateTimeString">The date/time string representation.</param>
        /// <returns>The date/time.</returns>
        public static DateTime StringToDateTime(string dateTimeString)
        {
            return DateTime.Parse(dateTimeString, null, DateTimeStyles.RoundtripKind);
        }
    }
}
