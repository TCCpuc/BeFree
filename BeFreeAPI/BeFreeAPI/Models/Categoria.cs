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
        public int idCategoria { get; set; }

        public string descricao { get; set; }
    }
}
