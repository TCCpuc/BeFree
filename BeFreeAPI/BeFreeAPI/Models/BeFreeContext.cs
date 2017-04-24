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
        }*/
    }
}
