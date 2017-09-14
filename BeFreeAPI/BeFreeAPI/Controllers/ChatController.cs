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

namespace BeFreeAPI.Controllers
{
    public class ChatController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();
        private Function function = new Function();

        // GET: api/Chat
        public IQueryable<Chat> GettbChats()
        {
            return db.tbChats;
        }

        // GET: api/Chat/5
        [ResponseType(typeof(Chat))]
        public IHttpActionResult GettbChat(int id)
        {
            IQueryable<Chat> tbChat = db.tbChats.Where(c => c.ID == id);
            if (tbChat == null)
            {
                return NotFound();
            }

            return Ok(tbChat);
        }

        // PUT: api/Chat/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PuttbChat(int id, Chat tbChat)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tbChat.ID)
            {
                return BadRequest();
            }

            db.Entry(tbChat).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!tbChatExists(id))
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

        // POST: api/Chat
        [ResponseType(typeof(Chat))]
        public IHttpActionResult PosttbChat(Chat tbChat)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbChats.Add(tbChat);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = tbChat.ID }, tbChat);
        }

        // DELETE: api/Chat/5
        [ResponseType(typeof(Chat))]
        public IHttpActionResult DeletetbChat(int id)
        {
            Chat tbChat = db.tbChats.Find(id);
            if (tbChat == null)
            {
                return NotFound();
            }

            db.tbChats.Remove(tbChat);
            db.SaveChanges();

            return Ok(tbChat);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool tbChatExists(int id)
        {
            return db.tbChats.Count(e => e.ID == id) > 0;
        }

        // GET: api/Chat/5
        [ResponseType(typeof(Chat))]
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public IHttpActionResult ChatExiste(int usuarioUm, int usuarioDois)
        {
            String str = "SELECT * " +
                         "FROM tbChat AS C " +
                         "WHERE (C.USUARIO_2 = " + usuarioUm + " AND C.USUARIO_1 =  " + usuarioDois + ") " +
                         "   OR (C.USUARIO_1 = " + usuarioUm + " AND USUARIO_2 = " + usuarioDois + ")";

            var buscar = db.tbChats.SqlQuery(str);

            return Ok(buscar);
        }

        // GET: api/Chat/5
        [ResponseType(typeof(Chat))]
        public IHttpActionResult GetChatsDoUsuario(int usuario)
        {
            String str = "SELECT * " +
                         "FROM tbChat AS C " +
                         "WHERE C.USUARIO_1 = " + usuario + " OR C.USUARIO_2 = " + usuario;

            var buscar = db.tbChats.SqlQuery(str);

            return Ok(buscar);
        }

    }
}