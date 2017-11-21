using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace BeFreeWeb.Models
{
    public class Denuncia
    {
        [Key]
        [Display(Name = "ID")]
        public int idDenuncia { get; set; }

        [Display(Name = "Causa")]
        public int causa { get; set; }

        [Display(Name = "Serviço")]
        public int? idServico { get; set; }

        [Display(Name = "Busca")]
        public int? idBusca { get; set; }

        [Display(Name = "Usuario")]
        public int idUsuarioDenunciante { get; set; }
    }
}