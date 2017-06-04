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
        public int idCidade { get; set; }

        public string codMunicipio { get; set; }

        public string nomeMunicipio { get; set; }

        public string codUF { get; set; }
    }
}
