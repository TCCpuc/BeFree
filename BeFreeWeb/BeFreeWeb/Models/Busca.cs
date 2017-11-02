using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace BeFreeWeb.Models
{
    public class Busca
    {
        [Key]
        [Display(Name = "ID")]
        public int idBusca { get; set; }

        [Display(Name = "Título")]
        public string titulo { get; set; }

        [Display(Name = "Descrição")]
        public string descricao { get; set; }

        [Display(Name = "Usuário")]
        public int idUsuario { get; set; }

        [Display(Name = "Categoria")]
        public int idSubCategoria { get; set; }

        [Display(Name = "Status")]
        public int? idStatus { get; set; }

        [Display(Name = "Imagem")]
        public string imagemBusca { get; set; }

        [Display(Name = "DDD")]
        public int idDDD { get; set; }
    }
}