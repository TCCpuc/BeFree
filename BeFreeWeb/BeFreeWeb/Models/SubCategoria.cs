using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BeFreeWeb.Models
{
    public class SubCategoria
    {
        public int idSubCategoria { get; set; }

        public int idCategoria { get; set; }

        public string descricao { get; set; }
    }
}