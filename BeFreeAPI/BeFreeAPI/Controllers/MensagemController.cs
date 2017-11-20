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
    public class MensagemController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/Mensagem
        public IQueryable<Mensagem> GettbMensagems()
        {
            return db.tbMensagems;
        }

        // GET: api/Mensagem/5
        [ResponseType(typeof(Mensagem))]
        public IHttpActionResult GetMensagem(int id)
        {
            IQueryable<Mensagem> mensagem = db.tbMensagems.Where(m => m.ID == id);
            if (mensagem == null)
            {
                return NotFound();
            }

            return Ok(mensagem);
        }

        // GET: api/Mensagem/5
        [ResponseType(typeof(Mensagem))]
        public IHttpActionResult GetMensagensDoChat(int id)
        {

            String query = "UPDATE tbMensagem " +
                           "SET lida = 1 " +
                           "WHERE CHAT = " + id +
                           "  AND lida = 0";

            var buscar = db.Database.ExecuteSqlCommand(query);

            IQueryable<Mensagem> mensagem = db.tbMensagems.Where(m => m.CHAT == id);
            if (mensagem == null)
            {
                return NotFound();
            }

            return Ok(mensagem);
        }

        // PUT: api/Mensagem/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutMensagem(int id, Mensagem mensagem)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != mensagem.ID)
            {
                return BadRequest();
            }

            db.Entry(mensagem).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }

            catch (DbUpdateConcurrencyException)
            {
                if (!MensagemExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
            catch (Exception err)
            {
                String erro = err.Message;
                return BadRequest();
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Mensagem
        [ResponseType(typeof(Mensagem))]
        public IHttpActionResult PostMensagem(Mensagem mensagem)
        {

            mensagem.DATA = null;
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbMensagems.Add(mensagem);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = mensagem.ID }, mensagem);
        }

        // DELETE: api/Mensagem/5
        [ResponseType(typeof(Mensagem))]
        public IHttpActionResult DeleteMensagem(int id)
        {
            Mensagem mensagem = db.tbMensagems.Find(id);
            if (mensagem == null)
            {
                return NotFound();
            }

            db.tbMensagems.Remove(mensagem);
            db.SaveChanges();

            return Ok(mensagem);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool MensagemExists(int id)
        {
            return db.tbMensagems.Count(e => e.ID == id) > 0;
        }
    }
}