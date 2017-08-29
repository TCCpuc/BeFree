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
            IQueryable<Busca> busca = db.tbBuscas.Where(b => b.idBusca ==id);
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
            /*
            "   OR SC.descricao LIKE '%" + busca + "%' " +
            "   OR C.descricao LIKE '%" + busca + "%') " +
            "  AND SC.idSubcategoria = S.idSubCategoria " +
            "  AND C.idCategoria = SC.idCategoria";
            */

            var buscar = db.tbBuscas.SqlQuery(str);

            return Ok(busca);
        }

        // PUT: api/Busca/5
        [ResponseType(typeof(void))]
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

            Image image = function.Base64ToImage(busca.imagemBusca);
            string nomeImage = function.SetImageName("buscas/" + busca.titulo.ToString().Replace(" ", "_") + "_" + busca.descricao.ToString().Replace(" ", "_"));
            bool UploadOk = function.UploadFile(image, nomeImage);

            busca.imagemBusca = nomeImage;

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
    }
}