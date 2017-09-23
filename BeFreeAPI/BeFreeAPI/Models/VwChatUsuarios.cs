namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;


    public partial class VwChatUsuarios
    {

        public int ID { get; set; }
        public int USUARIO_1 { get; set; }
        public string nomeUsuario1 { get; set; }
        public string imagemUsuario1 { get; set; }
        public int USUARIO_2 { get; set; }
        public string nomeUsuario2 { get; set; }
        public string imagemUsuario2 { get; set; }
        public string MENSAGEM { get; set; }
    }
}