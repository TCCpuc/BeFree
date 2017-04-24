using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace BeFreeAPP.Models
{
    class Usuario
    {
        public int idUsuario { get; set; }
        public string nomeUsuario { get; set; }
        public string cpf { get; set; }
        public int? idCidade { get; set; }
        public int? idEstado { get; set; }
        public string bairro { get; set; }
        public string logradouro { get; set; }
        public string numero { get; set; }
        public string cep { get; set; }
        public DateTime? dataNascimento { get; set; }
        public DateTime? dataCadastro { get; set; }
        public bool? ativo { get; set; }
        public string senha { get; set; }
    }
}