namespace BeFreeAPI.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("tbUsuario")]
    public class Usuario
    {
        [Key]
        public int idUsuario { get; set; }

        [StringLength(150)]
        public string nomeUsuario { get; set; }

        [StringLength(18)]
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
        public string senha { get; set; }

        [StringLength(50)]
        public string email { get; set; }

        [StringLength(3)]
        public string ddd { get; set; }

        public string imagemPerfil { get; set; }

        public string codigoSeguranca { get; set; }
    }
}
