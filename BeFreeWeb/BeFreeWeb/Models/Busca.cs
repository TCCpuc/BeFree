using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BeFreeWeb.Models
{
    public class Busca
    {
        public int idBusca { get; set; }

        public string titulo { get; set; }

        public string descricao { get; set; }

        public int idUsuario { get; set; }

        public int idSubCategoria { get; set; }

        public int? idStatus { get; set; }

        public string imagemBusca { get; set; }

        public int idDDD { get; set; }
    }
}