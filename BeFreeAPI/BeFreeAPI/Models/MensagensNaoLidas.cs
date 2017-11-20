using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BeFreeAPI.Models
{
    public class MensagensNaoLidas
    {
        public MensagensNaoLidas(int id, int num) {
            this.idUsuario = id;
            this.numeroMensagens = num;
        }
        public int idUsuario { get; set; }

        public int numeroMensagens { get; set; }
    }
}