using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using BeFreeWeb.Models;
using System.Configuration;
using System.Net.Http;
using Newtonsoft.Json;

namespace BeFreeWeb.Controllers
{
    public class DenunciaController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: Denuncia
        public async System.Threading.Tasks.Task<ActionResult> Index()
        {

            List<Denuncia> model = new List<Denuncia>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {
                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Denuncia/GettbDenuncias/");
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Denuncia>>(result);

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

        // GET: Denuncia/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Denuncia denuncia = db.Denuncias.Find(id);
            if (denuncia == null)
            {
                return HttpNotFound();
            }
            return View(denuncia);
        }

        // GET: Denuncia/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Denuncia/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "idDenuncia,causa,idServico,idBusca,idUsuarioDenunciante")] Denuncia denuncia)
        {
            if (ModelState.IsValid)
            {
                db.Denuncias.Add(denuncia);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(denuncia);
        }

        // GET: Denuncia/Edit/5
        public async System.Threading.Tasks.Task<ActionResult> Aceitar(int? id)
        {
            return null;
        }

        // POST: Denuncia/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "idDenuncia,causa,idServico,idBusca,idUsuarioDenunciante")] Denuncia denuncia)
        {
            if (ModelState.IsValid)
            {
                db.Entry(denuncia).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(denuncia);
        }

        // GET: Denuncia/Delete/5
        public ActionResult Recusar(int? id)
        {
            return RedirectToAction("Index");
        }

        // POST: Denuncia/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Denuncia denuncia = db.Denuncias.Find(id);
            db.Denuncias.Remove(denuncia);
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
