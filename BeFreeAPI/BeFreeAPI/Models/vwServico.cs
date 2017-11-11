namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class vwServico
    {
        [Key]
        public int idServico { get; set; }

        [StringLength(100)]
        public string titulo { get; set; }

        public string descricao { get; set; }

        public int idUsuario { get; set; }

        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }

        public string imagemServico { get; set; }

        public int idDDD { get; set; }

        public decimal? preco { get; set; }

        [StringLength(50)]
        public string formaPagto { get; set; }

        public decimal? mediaAvaliacao { get; set; }

        public string descrCategoria { get; set; }

        public string descrSubCategoria { get; set; }

    }
}
