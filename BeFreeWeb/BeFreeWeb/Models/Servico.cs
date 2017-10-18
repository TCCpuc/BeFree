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
        [Display(Name = "ID")]
        public int idServico { get; set; }

        [Display(Name = "Titulo")]
        public string titulo { get; set; }

        [Display(Name = "Descrição")]
        public string descricao { get; set; }

        public int idUsuario { get; set; }

        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }

        public string imagemServico { get; set; }

        [Display(Name = "DDD")]
        public int idDDD { get; set; }
    }
}