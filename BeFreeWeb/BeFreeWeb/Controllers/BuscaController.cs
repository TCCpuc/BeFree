using System;
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
    public class BuscaController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: Busca
        public async System.Threading.Tasks.Task<ActionResult> Index()
        {
            List<Busca> model = new List<Busca>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Busca/GettbBuscas/");
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Busca>>(result);
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

        // GET: Busca/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Busca busca = db.Buscas.Find(id);
            if (busca == null)
            {
                return HttpNotFound();
            }
            return View(busca);
        }

        // GET: Busca/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Busca/Create
        // Para se proteger de mais ataques, ative as propriedades específicas a que você quer se conectar. Para 
        // obter mais detalhes, consulte https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "idBusca,titulo,descricao,idUsuario,idSubCategoria,idStatus,imagemBusca,idDDD")] Busca busca)
        {
            if (ModelState.IsValid)
            {
                db.Buscas.Add(busca);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(busca);
        }

        // GET: Busca/Edit/5
        public async System.Threading.Tasks.Task<ActionResult> Edit(int? id)
        {
            List<Busca> model = new List<Busca>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Busca/GetBusca/?id=" + id);
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Busca>>(result);
                    }
                    catch (Exception err)
                    {
                        string erro = err.Message;
                    }

                }
                Busca busca = model[0];
                return View(busca);
            }
            else
                return RedirectToAction("Login");
        }

        // POST: Busca/Edit/5
        // Para se proteger de mais ataques, ative as propriedades específicas a que você quer se conectar. Para 
        // obter mais detalhes, consulte https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "idBusca,titulo,descricao,idUsuario,idSubCategoria,idStatus,imagemBusca,idDDD")] Busca busca)
        {
            if (ModelState.IsValid)
            {
                db.Entry(busca).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(busca);
        }

        // GET: Busca/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Busca busca = db.Buscas.Find(id);
            if (busca == null)
            {
                return HttpNotFound();
            }
            return View(busca);
        }

        // POST: Busca/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Busca busca = db.Buscas.Find(id);
            db.Buscas.Remove(busca);
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
