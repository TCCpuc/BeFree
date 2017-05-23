using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using BeFreeAPI.Models;

namespace BeFreeAPI.Controllers
{
    public class SubCategoriaController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/SubCategoria
        public IQueryable<SubCategoria> GettbSubCategorias()
        {
            return db.tbSubCategorias;
        }

        // GET: api/SubCategoria/5
        [ResponseType(typeof(SubCategoria))]
        public IHttpActionResult GetSubCategoria(int id)
        {
            List<SubCategoria> subCategoria = db.tbSubCategorias.Where(s => s.idSubCategoria == id).ToList();
            if (subCategoria == null)
            {
                return NotFound();
            }

            return Ok(subCategoria);
        }

        // PUT: api/SubCategoria/5
        [ResponseType(typeof(void))]
        [HttpPost]
        public IHttpActionResult PutSubCategoria(int id, SubCategoria subCategoria)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != subCategoria.idSubCategoria)
            {
                return BadRequest();
            }

            db.Entry(subCategoria).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SubCategoriaExists(id))
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

        // POST: api/SubCategoria
        [ResponseType(typeof(SubCategoria))]
        public async Task<IHttpActionResult> PostSubCategoria(SubCategoria subCategoria)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbSubCategorias.Add(subCategoria);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (SubCategoriaExists(subCategoria.idSubCategoria))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = subCategoria.idSubCategoria }, subCategoria);
        }

        // DELETE: api/SubCategoria/5
        [ResponseType(typeof(SubCategoria))]
        public async Task<IHttpActionResult> DeleteSubCategoria(int id)
        {
            SubCategoria subCategoria = await db.tbSubCategorias.FindAsync(id);
            if (subCategoria == null)
            {
                return NotFound();
            }

            db.tbSubCategorias.Remove(subCategoria);
            await db.SaveChangesAsync();

            return Ok(subCategoria);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool SubCategoriaExists(int id)
        {
            return db.tbSubCategorias.Count(e => e.idSubCategoria == id) > 0;
        }
    }
}