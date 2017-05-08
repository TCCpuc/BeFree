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
    public class CategoriaController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/Categoria
        public IQueryable<Categoria> GettbCategorias()
        {
            return db.tbCategorias;
        }

        // GET: api/Categoria/5
        [ResponseType(typeof(Categoria))]
        public IHttpActionResult GetCategoria(int id)
        {
            Categoria categoria = db.tbCategorias.Find(id);
            if (categoria == null)
            {
                return NotFound();
            }

            return Ok(categoria);
        }

        // PUT: api/Categoria/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCategoria(int id, Categoria categoria)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != categoria.idCategoria)
            {
                return BadRequest();
            }

            db.Entry(categoria).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CategoriaExists(id))
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

        // POST: api/Categoria
        [ResponseType(typeof(Categoria))]
        public IHttpActionResult PostCategoria(Categoria categoria)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbCategorias.Add(categoria);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (CategoriaExists(categoria.idCategoria))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = categoria.idCategoria }, categoria);
        }

        // DELETE: api/Categoria/5
        [ResponseType(typeof(Categoria))]
        public IHttpActionResult DeleteCategoria(int id)
        {
            Categoria categoria = db.tbCategorias.Find(id);
            if (categoria == null)
            {
                return NotFound();
            }

            db.tbCategorias.Remove(categoria);
            db.SaveChanges();

            return Ok(categoria);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CategoriaExists(int id)
        {
            return db.tbCategorias.Count(e => e.idCategoria == id) > 0;
        }
    }
}