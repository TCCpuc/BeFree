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
using System.Data.SqlClient;

namespace BeFreeAPI.Controllers
{
    public class UsuariosController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();

        // GET: api/Usuarios
        public IQueryable<Usuario> GettbUsuarios()
        {
            return db.tbUsuarios;
        }

        // GET: api/Usuarios/5
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult GetUsuario(int id)
        {
            List<Usuario> usuario = db.tbUsuarios.Where(u => u.idUsuario == id).ToList();
            if (usuario == null)
            {
                return NotFound();
            }

            return Ok(usuario);
        }

        
        // GET: api/Usuarios/[nome ou email]
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult GetUsuarioByEmail(string email)
        {

            var usuario = db.tbUsuarios.Where(u => u.email == email);//db.tbUsuarios.SqlQuery(query, parameters);
            if (usuario == null)
            {
                return NotFound();
            }

            return Ok(usuario);
        }

    

        // PUT: api/Usuarios/5
        [ResponseType(typeof(void))]
        [HttpPost]
        public IHttpActionResult PutUsuario(int id, Usuario usuario)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != usuario.idUsuario)
            {
                return BadRequest();
            }

            db.Entry(usuario).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsuarioExists(id))
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

        // POST: api/Usuarios
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult PostUsuario(Usuario usuario)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tbUsuarios.Add(usuario);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (UsuarioExists(usuario.idUsuario))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = usuario.idUsuario }, usuario);
        }

        // DELETE: api/Usuarios/5
        [ResponseType(typeof(Usuario))]
        public IHttpActionResult DeleteUsuario(int id)
        {
            Usuario usuario = db.tbUsuarios.Find(id);
            if (usuario == null)
            {
                return NotFound();
            }

            db.tbUsuarios.Remove(usuario);
            db.SaveChanges();

            return Ok(usuario);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UsuarioExists(int id)
        {
            return db.tbUsuarios.Count(e => e.idUsuario == id) > 0;
        }
    }
}