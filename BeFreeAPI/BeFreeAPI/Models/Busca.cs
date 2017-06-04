namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbBusca")]
    public partial class Busca
    {
        [Key]
        public int idBusca { get; set; }

        [StringLength(100)]
        public string titulo { get; set; }

        public string descricao { get; set; }

        public int idUsuario { get; set; }

        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }
    }
}
