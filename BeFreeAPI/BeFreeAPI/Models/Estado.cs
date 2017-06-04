namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbEstado")]
    public partial class Estado
    {
        [Key]
        [Column(Order = 0)]
        public int idEstado { get; set; }

        [StringLength(10)]
        public string codUF { get; set; }

        [StringLength(3)]
        public string siglaUF { get; set; }

        [StringLength(50)]
        public string nomeEstado { get; set; }
    }
}
