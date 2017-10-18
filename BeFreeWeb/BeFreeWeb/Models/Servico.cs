using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace BeFreeWeb.Models
{
    public class Servico
    {
        [Key]
        public int idServico { get; set; }
    
        public string titulo { get; set; }

        public string descricao { get; set; }

        public int idUsuario { get; set; }

        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }

        public string imagemServico { get; set; }

        public int idDDD { get; set; }
    }
}