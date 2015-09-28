//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Bzs.Server.DataAccess
{
    using System;
    using System.Collections.Generic;
    
    public partial class LessonEntity
    {
        public System.Guid Id { get; set; }
        public System.Guid AccountId { get; set; }
        public System.Guid DayId { get; set; }
        public System.DateTime FromDate { get; set; }
        public System.DateTime ToDate { get; set; }
        public System.Guid SubjectId { get; set; }
        public string Teacher { get; set; }
        public string Room { get; set; }
        public string Remark { get; set; }
        public System.DateTime ModDate { get; set; }
        public string ModUser { get; set; }
    
        public virtual AccountEntity AccountNavProp { get; set; }
        public virtual DayEntity DayNavProp { get; set; }
        public virtual SubjectEntity SubjectNavProp { get; set; }
    }
}
