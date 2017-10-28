﻿using System;
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
    public class EventosController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/Eventos
        public IQueryable<Evento> GettbEventos()
        {
            return db.tbEventos;
        }

        // GET: api/Eventos/5
        [ResponseType(typeof(Evento))]
        public IHttpActionResult GetEvento(int id)
        {
            Evento evento = db.tbEventos.Find(id);
            if (evento == null)
            {
                return NotFound();
            }

            return Ok(evento);
        }

        // PUT: api/Eventos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutEvento(int id, Evento evento)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != evento.idEvento)
            {
                return BadRequest();
            }

            db.Entry(evento).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EventoExists(id))
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

        // POST: api/Eventos
        [ResponseType(typeof(Evento))]
        public IHttpActionResult PostEvento(Evento evento)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbEventos.Add(evento);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = evento.idEvento }, evento);
        }

        // DELETE: api/Eventos/5
        [ResponseType(typeof(Evento))]
        public IHttpActionResult DeleteEvento(int id)
        {
            Evento evento = db.tbEventos.Find(id);
            if (evento == null)
            {
                return NotFound();
            }

            db.tbEventos.Remove(evento);
            db.SaveChanges();

            return Ok(evento);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EventoExists(int id)
        {
            return db.tbEventos.Count(e => e.idEvento == id) > 0;
        }
    }
}