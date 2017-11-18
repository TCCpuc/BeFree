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
using System.Drawing;
using BeFreeAPI.Utils;
using System.Configuration;

namespace BeFreeAPI.Controllers
{
    public class BuscaController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();
        private Function function = new Function();

        // GET: api/Busca
        public IQueryable<Busca> GettbBuscas()
        {
            return db.tbBuscas;
        }

        // GET: api/Busca/5
        [ResponseType(typeof(Busca))]
        public IHttpActionResult GetBusca(int id)
        {
            IQueryable<Busca> busca = db.tbBuscas.Where(b => b.idBusca == id);
            if (busca == null)
            {
                return NotFound();
            }

            return Ok(busca);
        }

        // GET: api/Busca/5
        [ResponseType(typeof(Busca))]
        public IHttpActionResult GetBuscaBySubCategoria(int idSubCategoria)
        {
            IQueryable<Busca> busca = db.tbBuscas.Where(b => b.idSubCategoria == idSubCategoria);
            if (busca == null)
            {
                return NotFound();
            }

            return Ok(busca);
        }

        // GET: api/Busca/5
        [ResponseType(typeof(Busca))]
        public IHttpActionResult GetBuscaByUsuario(int idUsuario)
        {
            IQueryable<Busca> busca = db.tbBuscas.Where(b => b.idUsuario == idUsuario);
            if (busca == null)
            {
                return NotFound();
            }

            return Ok(busca);
        }

        // GET: api/Busca/5
        [ResponseType(typeof(Busca))]
        public IHttpActionResult GetBuscaByLike(String busca)
        {
            String str = "SELECT * " +
                         "FROM tbBusca AS B " +  // +, tbSubCategoria SC, tbCategoria AS C " +
                         "WHERE B.titulo LIKE '%" + busca + "%'" +
                         "   OR B.descricao LIKE '%" + busca + "%' ";

            var buscar = db.tbBuscas.SqlQuery(str);

            return Ok(busca);
        }

        // PUT: api/Busca/5
        [ResponseType(typeof(void))]
        [HttpPost]
        public IHttpActionResult PutBusca(int id, Busca busca)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != busca.idBusca)
            {
                return BadRequest();
            }

            if (!busca.imagemBusca.Contains("http:"))
            {
                busca.imagemBusca = this.SetImagem(busca);
            }

            db.Entry(busca).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BuscaExists(id))
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

        // POST: api/Busca
        [ResponseType(typeof(Busca))]
        public IHttpActionResult PostBusca(Busca busca)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            busca.imagemBusca = this.SetImagem(busca);

            db.tbBuscas.Add(busca);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (BuscaExists(busca.idBusca))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = busca.idBusca }, busca);
        }

        // DELETE: api/Busca/5
        [ResponseType(typeof(Busca))]
        [HttpGet]
        public IHttpActionResult DeleteBusca(int id)
        {
            Busca busca = db.tbBuscas.Find(id);
            if (busca == null)
            {
                return NotFound();
            }

            db.tbBuscas.Remove(busca);
            db.SaveChanges();

            return Ok(busca);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool BuscaExists(int id)
        {
            return db.tbBuscas.Count(e => e.idBusca == id) > 0;
        }

        private string SetImagem(Busca busca) {

            Random random = new Random();

            string nomeImage = "buscas\\" + random.Next(1000000).ToString() + random.Next(3000000).ToString() +  random.Next(3000000).ToString() + ".jpeg";

            Image image = function.Base64ToImage(busca.imagemBusca);
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

        [ResponseType(typeof(VwBusca))]
        public IHttpActionResult GetVwBuscaById(int id)
        {
            String str = "SELECT * " +
                         "FROM vw_busca AS B " +
                         "WHERE B.idBusca = " + id;

            var buscar = db.Database.SqlQuery<VwBusca>(str);

            return Ok(buscar);
        }

        [ResponseType(typeof(VwBusca))]
        public IHttpActionResult GetVwBusca(int usuario)
        {
            String str = "SELECT * " +
                         "FROM vw_busca AS B " +
                         "WHERE B.idUsuario <> " + usuario;

            var buscar = db.Database.SqlQuery<VwBusca>(str);

            return Ok(buscar);
        }

        [ResponseType(typeof(VwBusca))]
        public IHttpActionResult GetVwBuscaByUsuario(int usuario)
        {
            String str = "SELECT * " +
                         "FROM vw_busca AS B " +
                         "WHERE B.idUsuario = " + usuario;

            var buscar = db.Database.SqlQuery<VwBusca>(str);

            return Ok(buscar);
        }
    }
}