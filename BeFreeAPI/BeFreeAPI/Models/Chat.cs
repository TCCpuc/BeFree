namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbChat")]
    public partial class Chat
    {
        [Key]
        public int ID { get; set; }

        public int USUARIO_1 { get; set; }

        public int USUARIO_2 { get; set; }

        public int? ULTIMA_MENSAGEM { get; set; }
    }
}
