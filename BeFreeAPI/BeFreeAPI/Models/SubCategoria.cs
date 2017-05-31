namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbSubCategoria")]
    public partial class SubCategoria
    {
        [Key]
        public int idSubCategoria { get; set; }

        public int idCategoria { get; set; }

        public string descricao { get; set; }
    }
}
