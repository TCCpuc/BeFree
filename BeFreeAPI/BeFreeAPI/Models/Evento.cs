namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbEvento")]
    public partial class Evento
    {
        [Key]
        public int idEvento { get; set; }

        public int idServico { get; set; }

        public int idUsuarioContratante { get; set; }

        public DateTime dtEvento { get; set; }

        public int? hrInicio { get; set; }

        public int? hrFinal { get; set; }

        public decimal notaAvaliacao { get; set; }

        public bool? avaliado { get; set; }

        public int situacaoEvento { get; set; }
    }
}
