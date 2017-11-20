namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbLog")]
    public partial class Log
    {
        [Key]
        public int idLog { get; set; }

        [StringLength(200)]
        public string titulo { get; set; }

        [StringLength(2000)]
        public string descricao { get; set; }

        public DateTime? data { get; set; }
    }
}
