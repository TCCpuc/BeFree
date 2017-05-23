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
        [Column(Order = 0)]
        public int idSubCategoria { get; set; }

        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idCategoria { get; set; }

        public string descricao { get; set; }
    }
}
