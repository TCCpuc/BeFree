namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbCidade")]
    public partial class Cidade
    {
        [Key]
        [Column(Order = 0)]
        public int idCidade { get; set; }

        [Key]
        [Column(Order = 1)]
        [StringLength(10)]
        public string codMunicipio { get; set; }

        [StringLength(10)]
        public string nomeMunicipio { get; set; }

        [StringLength(10)]
        public string codUF { get; set; }
    }
}
