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
        public int idServico { get; set; }

        [StringLength(100)]
        public string titulo { get; set; }

        public string descricao { get; set; }

        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idUsuario { get; set; }

        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }
    }
}
