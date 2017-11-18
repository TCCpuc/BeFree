namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbDenuncia")]
    public partial class Denuncia
    {
        [Key]
        public int idDenuncia { get; set; }

        public int causa { get; set; }

        public int? idServico { get; set; }

        public int? idBusca { get; set; }
    }
}
