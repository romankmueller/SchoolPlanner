﻿//------------------------------------------------------------------------------
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
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class BzsEntityContainer : DbContext
    {
        public BzsEntityContainer()
            : base("name=BzsEntityContainer")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<AccountEntity> AccountSet { get; set; }
        public virtual DbSet<DayEntity> DaySet { get; set; }
        public virtual DbSet<LessonEntity> LessonSet { get; set; }
        public virtual DbSet<SubjectEntity> SubjectSet { get; set; }
    }
}