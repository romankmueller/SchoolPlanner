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
    
    public partial class SubjectEntity
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public SubjectEntity()
        {
            this.LessonNavProp = new HashSet<LessonEntity>();
        }
    
        public System.Guid Id { get; set; }
        public string Code { get; set; }
        public string Caption { get; set; }
        public System.DateTime ModDate { get; set; }
        public string ModUser { get; set; }
    
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<LessonEntity> LessonNavProp { get; set; }
    }
}