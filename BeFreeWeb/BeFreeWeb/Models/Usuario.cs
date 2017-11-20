using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace BeFreeWeb.Models
{
    public class Usuario
    {
        [Key]
        [Display(Name = "ID")]
        public int idUsuario { get; set; }

        [StringLength(150)]
        [Display(Name = "Nome")]
        public string nomeUsuario { get; set; }

        [StringLength(18)]
        [Display(Name = "CPF")]
        public string cpf { get; set; }

        public int? idCidade { get; set; }

        public int? idEstado { get; set; }

        [StringLength(50)]
        public string bairro { get; set; }

        [StringLength(100)]
        public string logradouro { get; set; }

        [StringLength(10)]
        public string numero { get; set; }

        [StringLength(8)]
        public string cep { get; set; }

        public DateTime? dataNascimento { get; set; }

        public DateTime? dataCadastro { get; set; }

        public bool? ativo { get; set; }

        [StringLength(20)]
        [Display(Name = "Senha")]
        public string senha { get; set; }

        [StringLength(50)]
        [Display(Name = "E-mail")]
        public string email { get; set; }

        [StringLength(3)]
        [Display(Name = "DDD")]
        public string ddd { get; set; }

        public string imagemPerfil { get; set; }

        public string codigoSeguranca { get; set; }

        public string paginaRenderizada {get;set;}
    }
}