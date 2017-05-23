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

namespace BeFreeAPI.Controllers
{
    public class BuscaController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/Busca
        public IQueryable<Busca> GettbBuscas()
        {
            return db.tbBuscas;
        }

        // GET: api/Busca/5
        [ResponseType(typeof(Busca))]
        public IHttpActionResult GetBusca(int id)
        {
            List<Busca> busca = db.tbBuscas.Where(b => b.idBusca == id).ToList();
            if (busca == null)
            {
                return NotFound();
            }

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