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
    public class DDDController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/DDD
        public IQueryable<DDD> GettbDDDs()
        {
            return db.tbDDDs;
        }

        // GET: api/DDD/5
        [ResponseType(typeof(DDD))]
        public IHttpActionResult GettbDDD(int id)
        {
            DDD tbDDD = db.tbDDDs.Find(id);
            if (tbDDD == null)
            {
                return NotFound();
            }

            return Ok(tbDDD);
        }

        // PUT: api/DDD/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PuttbDDD(int id, DDD tbDDD)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tbDDD.idDDD)
            {
                return BadRequest();
            }

            db.Entry(tbDDD).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!tbDDDExists(id))
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

        // POST: api/DDD
        [ResponseType(typeof(DDD))]
        public IHttpActionResult PosttbDDD(DDD tbDDD)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbDDDs.Add(tbDDD);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = tbDDD.idDDD }, tbDDD);
        }

        // DELETE: api/DDD/5
        [ResponseType(typeof(DDD))]
        public IHttpActionResult DeletetbDDD(int id)
        {
            DDD tbDDD = db.tbDDDs.Find(id);
            if (tbDDD == null)
            {
                return NotFound();
            }

            db.tbDDDs.Remove(tbDDD);
            db.SaveChanges();

            return Ok(tbDDD);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool tbDDDExists(int id)
        {
            return db.tbDDDs.Count(e => e.idDDD == id) > 0;
        }
    }
}