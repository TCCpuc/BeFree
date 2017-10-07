namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbMensagem")]
    public partial class Mensagem
    {
        [Key]
        public int ID { get; set; }

        public int CHAT { get; set; }

        public int USUARIO_ORIGEM { get; set; }

        public int USUARIO_DESTINO { get; set; }

        public DateTime? DATA { get; set; }

        public string MENSAGEM { get; set; }

    }
}
