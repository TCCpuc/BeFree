using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BeFreeAPI.Utils
{
    public class Retorno
    {
        public Retorno(int id, string mensagem)
        {
            this.id = id;
            this.mensagem = mensagem;
        }
        public int id { get; set; }
        public string mensagem { get; set; }
    }
}