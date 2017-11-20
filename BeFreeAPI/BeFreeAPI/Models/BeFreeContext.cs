namespace BeFreeAPI.Models
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class BeFreeContext : DbContext
    {
        public BeFreeContext()
            : base("name=BeFreeContext")
        {
        }

        public DbSet<Cidade> tbCidades { get; set; }
        public DbSet<Estado> tbEstadoes { get; set; }
        public DbSet<Usuario> tbUsuarios { get; set; }
        public virtual DbSet<Busca> tbBuscas { get; set; }
        public virtual DbSet<Categoria> tbCategorias { get; set; }
        public virtual DbSet<Servico> tbServicoes { get; set; }
        public virtual DbSet<SubCategoria> tbSubCategorias { get; set; }
        public virtual DbSet<Chat> tbChats { get; set; }
        public virtual DbSet<Mensagem> tbMensagems { get; set; }
        public virtual DbSet<Evento> tbEventos { get; set; }
        public virtual DbSet<Denuncia> tbDenuncias { get; set; }
        public virtual DbSet<Log> tbLogs { get; set; }
        public virtual DbSet<VwChatUsuarios> vw_chat_usuario { get; set; }
        public virtual DbSet<VwEventoUsuario> vw_evento_usuario { get; set; }
        public virtual DbSet<vwServico> vw_servico { get; set; }

        public virtual DbSet<VwBusca> vw_busca { get; set; }

        public System.Data.Entity.DbSet<BeFreeAPI.Models.DDD> tbDDDs { get; set; }

    }
}
