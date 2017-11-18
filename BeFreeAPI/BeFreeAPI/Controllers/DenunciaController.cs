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
    public class DenunciaController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/Denuncia
        public IQueryable<Denuncia> GettbDenuncias()
        {
            return db.tbDenuncias;
        }

        // GET: api/Denuncia/5
        [ResponseType(typeof(Denuncia))]
        public IHttpActionResult GetDenuncia(int id)
        {
            var denuncia = db.tbDenuncias.Where(d => d.idDenuncia == id);
            if (denuncia == null)
            {
                return NotFound();
            }

            return Ok(denuncia);
        }

        // PUT: api/Denuncia/5
        [ResponseType(typeof(void))]
        [HttpPost]
        public IHttpActionResult PutDenuncia(int id, Denuncia denuncia)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != denuncia.idDenuncia)
            {
                return BadRequest();
            }

            db.Entry(denuncia).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DenunciaExists(id))
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

        // POST: api/Denuncia
        [ResponseType(typeof(Denuncia))]
        public IHttpActionResult PostDenuncia(Denuncia denuncia)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbDenuncias.Add(denuncia);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = denuncia.idDenuncia }, denuncia);
        }

        // DELETE: api/Denuncia/5
        [ResponseType(typeof(Denuncia))]
        [HttpGet]
        public IHttpActionResult DeleteDenuncia(int id)
        {
            Denuncia denuncia = db.tbDenuncias.Find(id);
            if (denuncia == null)
            {
                return NotFound();
            }

            db.tbDenuncias.Remove(denuncia);
            db.SaveChanges();

            return Ok(denuncia);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool DenunciaExists(int id)
        {
            return db.tbDenuncias.Count(e => e.idDenuncia == id) > 0;
        }
    }
}