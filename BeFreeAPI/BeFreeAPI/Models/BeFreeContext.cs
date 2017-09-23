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
        public virtual DbSet<VwChatUsuarios> vw_chat_usuario { get; set; }

        public System.Data.Entity.DbSet<BeFreeAPI.Models.DDD> tbDDDs { get; set; }

        /*protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Cidade>()
                .Property(e => e.codMunicipio)
                .IsUnicode(false);

            modelBuilder.Entity<Cidade>()
                .Property(e => e.nomeMunicipio)
                .IsFixedLength();

            modelBuilder.Entity<Cidade>()
                .Property(e => e.codUF)
                .IsUnicode(false);

            modelBuilder.Entity<Estado>()
                .Property(e => e.codUF)
                .IsUnicode(false);

            modelBuilder.Entity<Estado>()
                .Property(e => e.siglaUF)
                .IsUnicode(false);

            modelBuilder.Entity<Estado>()
                .Property(e => e.nomeEstado)
                .IsUnicode(false);

            modelBuilder.Entity<Usuario>()
                .Property(e => e.nomeUsuario)
                .IsUnicode(false);

            modelBuilder.Entity<Usuario>()
                .Property(e => e.cpf)
                .IsUnicode(false);

            modelBuilder.Entity<Usuario>()
                .Property(e => e.bairro)
                .IsUnicode(false);

            modelBuilder.Entity<Usuario>()
                .Property(e => e.logradouro)
                .IsUnicode(false);

            modelBuilder.Entity<Usuario>()
                .Property(e => e.numero)
                .IsUnicode(false);

            modelBuilder.Entity<Usuario>()
                .Property(e => e.cep)
                .IsUnicode(false);

            modelBuilder.Entity<tbBusca>()
                .Property(e => e.titulo)
                .IsUnicode(false);

            modelBuilder.Entity<tbBusca>()
                .Property(e => e.descricao)
                .IsUnicode(false);

            modelBuilder.Entity<tbCategoria>()
                .Property(e => e.descricao)
                .IsUnicode(false);

            modelBuilder.Entity<tbServico>()
                .Property(e => e.titulo)
                .IsUnicode(false);

            modelBuilder.Entity<tbServico>()
                .Property(e => e.descricao)
                .IsUnicode(false);

            modelBuilder.Entity<tbSubCategoria>()
                .Property(e => e.descricao)
                .IsUnicode(false);
        }*/
    }
}
