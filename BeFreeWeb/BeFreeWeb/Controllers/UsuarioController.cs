﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using BeFreeWeb.Models;
using System.Net.Http;
using System.Configuration;
using Newtonsoft.Json;

namespace BeFreeWeb.Controllers
{
    public class UsuarioController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        public async System.Threading.Tasks.Task<ActionResult> Index()
        {

            List<Usuario> model = new List<Usuario>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/GettbUsuarios/");
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Usuario>>(result);

                    }
                    catch (Exception err)
                    {
                        string erro = err.Message;
                    }

                }
                return View(model);
            }
            else
                return RedirectToAction("Login");
        }


        // GET: Usuario/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Usuario usuario = db.Usuarios.Find(id);
            if (usuario == null)
            {
                return HttpNotFound();
            }
            return View(usuario);
        }

        // GET: Usuario/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Usuario/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "idUsuario,nomeUsuario,cpf,idCidade,idEstado,bairro,logradouro,numero,cep,dataNascimento,dataCadastro,ativo,senha,email,ddd,imagemPerfil")] Usuario usuario)
        {
            if (ModelState.IsValid)
            {
                db.Usuarios.Add(usuario);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(usuario);
        }

        // GET: Usuario/Edit/5
        public async System.Threading.Tasks.Task<ActionResult> Edit(int? id)
        {
            List<Usuario> model = new List<Usuario>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/GetUsuario/?id=" + id);
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Usuario>>(result);

                    }
                    catch (Exception err)
                    {
                        string erro = err.Message;
                    }

                }
                Usuario usuario = model[0];
                return View(usuario);
            }
            else
                return RedirectToAction("Login");
        }

        // POST: Usuario/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "idUsuario,nomeUsuario,cpf,idCidade,idEstado,bairro,logradouro,numero,cep,dataNascimento,dataCadastro,ativo,senha,email,ddd,imagemPerfil")] Usuario usuario)
        {
            if (ModelState.IsValid)
            {
                db.Entry(usuario).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(usuario);
        }

        // GET: Usuario/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Usuario usuario = db.Usuarios.Find(id);
            if (usuario == null)
            {
                return HttpNotFound();
            }
            return View(usuario);
        }

        // POST: Usuario/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Usuario usuario = db.Usuarios.Find(id);
            db.Usuarios.Remove(usuario);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
