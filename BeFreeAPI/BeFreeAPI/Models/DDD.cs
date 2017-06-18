namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbDDD")]
    public partial class DDD
    {
        [Key]
        public int idDDD { get; set; }

        [StringLength(3)]
        public string codDDD { get; set; }

        public string descricao { get; set; }
    }
}
