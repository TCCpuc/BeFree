namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbCategoria")]
    public partial class Categoria
    {
        [Key]
        [Column(Order = 0)]
        public int idCategoria { get; set; }

        [Key]
        [Column(Order = 1)]
        public string descricao { get; set; }
    }
}
