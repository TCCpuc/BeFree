
using System;
using System.Collections;

namespace BeFreeAPP.Models
{
    public class Servico
    {
        //public string nome { get; set; }
        //public string categoria { get; set;}
        //public string subcategoria { get; set;}
        //public string cidade { get; set;}
        //public string bairro { get; set;}
        //public string descricao { get; set; }
        //public int id_servico { get; set; }
        //public long cpf { get; set; }
        public int idServico { get; set; }
        public string titulo { get; set; }
        public string descricao { get; set; }
        public int idUsuario { get; set; }
        public int idSubCategoria { get; set; }
        public int? idStatus { get; set; }
    }
}