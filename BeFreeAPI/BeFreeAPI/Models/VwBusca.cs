namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class VwBusca
    {
        [Key]
        public int idBusca { get; set; }

        [StringLength(100)]
        public string titulo { get; set; }

        public string descricao { get; set; }

        public int idUsuario { get; set; }

        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }

        public string imagemBusca { get; set; }

        public int idDDD { get; set; }

        public decimal preco { get; set; }

        public string formaPagto { get; set; }

        public string descrCategoria { get; set; }

        public string descrSubCategoria { get; set; }
    }
}
