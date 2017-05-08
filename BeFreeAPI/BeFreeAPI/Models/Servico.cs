namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbServico")]
    public partial class Servico
    {
        [Key]
        [Column(Order = 0)]
        public int idServico { get; set; }

        [StringLength(100)]
        public string titulo { get; set; }

        [Key]
        [Column(Order = 1)]
        public string descricao { get; set; }

        [Key]
        [Column(Order = 2)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idUsuario { get; set; }

        [Key]
        [Column(Order = 3)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }
    }
}
