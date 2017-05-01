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
    public class Busca
    {
        public string nome { get; set; }
        public string categoria { get; set; }
        public string subcategoria { get; set; }
        public string cidade { get; set; }
        public string bairro { get; set; }
        public string descricao { get; set; }
        public int id_busca { get; set; }
        public long cpf { get; set; }
        public bool status { get; set; }
    }
}