using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using BeFreeAPI.Models;
using BeFreeAPI.Utils;
using System.Drawing;
using System.Drawing.Imaging;
using System.Configuration;

namespace BeFreeAPI.Controllers
{
    public class UsuariosController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();
        private Function function = new Function();

        // GET: api/Usuarios
        public IQueryable<Usuario> GettbUsuarios()
        {
            return db.tbUsuarios;
        }

        // GET: api/Usuarios/5
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult GetUsuario(int id)
        {
            IQueryable<Usuario> usuario = db.tbUsuarios.Where(u => u.idUsuario == id);
            if (usuario == null)
            {
                return NotFound();
            }

            return Ok(usuario);
        }


        // GET: api/Usuarios/GetUsuarioByEmail/?email=exemplo@exemplo.com
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult GetUsuarioByEmail(String email)
        {
            IQueryable<Usuario> usuario = db.tbUsuarios.Where(u => u.email == email);
            if (usuario == null)
            {
                return NotFound();
            }

            return Ok(usuario);
        }

        // PUT: api/Usuarios/5
        [ResponseType(typeof(void))]
        [HttpPost]
        public IHttpActionResult PutUsuario(int id, Usuario usuario)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != usuario.idUsuario)
            {
                return BadRequest();
            }

            if (!usuario.imagemPerfil.Contains("http:")) {
                usuario.imagemPerfil = this.SetImagem(usuario);
            }

            db.Entry(usuario).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsuarioExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Usuarios
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult PostUsuario(Usuario usuario)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            usuario.imagemPerfil = this.SetImagem(usuario);

            db.tbUsuarios.Add(usuario);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (UsuarioExists(usuario.idUsuario))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = usuario.idUsuario }, usuario);
        }

        // DELETE: api/Usuarios/5
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult DeleteUsuario(int id)
        {
            Usuario usuario = db.tbUsuarios.Find(id);
            if (usuario == null)
            {
                return NotFound();
            }

            db.tbUsuarios.Remove(usuario);
            db.SaveChanges();

            return Ok(usuario);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UsuarioExists(int id)
        {
            return db.tbUsuarios.Count(e => e.idUsuario == id) > 0;
        }


        // GET: api/Usuarios
        public IQueryable<Usuario> CriaUsuario()
        {
            return db.tbUsuarios;
        }

        private string SetImagem(Usuario usuario)
        {

            Random random = new Random();

            string nomeImage =  "usuarios\\" + usuario.nomeUsuario.ToString().Replace(" ", "_") + "_" + random.Next(1000000).ToString() + ".jpg";

            Image image = function.Base64ToImage(usuario.imagemPerfil);
            if (image != null)
            {
                if (function.UploadFile(image, nomeImage))
                    nomeImage = function.SetCaminhoRetorno(nomeImage);
                else
                    nomeImage = ConfigurationManager.AppSettings["imagesPathSemImagem"].ToString(); /** Recebe a imagem padrão (SEM IMAGEM) **/
            }
            else
                nomeImage = ConfigurationManager.AppSettings["imagesPathSemImagem"].ToString(); /** Recebe a imagem padrão (SEM IMAGEM) **/

            return nomeImage;
        }

        [HttpGet]
        public IHttpActionResult ValidarCodigo(String codigo)
        {

            IQueryable <Usuario> usuario = null;

            try
            {

                usuario = db.tbUsuarios.Where(u => u.codigoSeguranca == codigo);
                if (usuario == null)
                    return NotFound();

            }
            catch (Exception err)
            {

                return NotFound();
            }

            return Ok(usuario);
        }

        [HttpGet]
        public IHttpActionResult RecuperarSenha(String email)
        {

            try {
                string bodyEmail = function.GenerateRandomString();
                bool emailOK = function.EnviaEmail(email, "Recuperação de Senha Befree", bodyEmail);

                //colocar bodyEmail no banco
                IQueryable<Usuario> usuario = db.tbUsuarios.Where(u => u.email == email);
                if (usuario == null)
                {
                    return NotFound();
                }
                var usuarioEncontrado = usuario.FirstOrDefault();
                usuarioEncontrado.codigoSeguranca = bodyEmail;
                PutUsuario(usuarioEncontrado.idUsuario, usuarioEncontrado);
            }
            catch (Exception err) {

                return NotFound();
            }

            return Ok();
        }

        [ResponseType(typeof(Usuario))]
        [HttpPost]
        public IHttpActionResult AuthenticateUsuarioFacebook(Usuario usuario)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }


            if (db.tbUsuarios.Count(u => u.email == usuario.email) > 0)
            {
                db.tbUsuarios.Add(usuario);
            }
            else
            {

                try
                {
                    db.SaveChanges();
                }
                catch (DbUpdateException)
                {
                    if (UsuarioExists(usuario.idUsuario))
                    {
                        return Conflict();
                    }
                    else
                    {
                        throw;
                    }
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = usuario.idUsuario }, usuario);
        }

    }
}