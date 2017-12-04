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
using System.Net.Http.Formatting;

namespace BeFreeWeb.Controllers
{
    public class ServicoController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: Servico
        public async System.Threading.Tasks.Task<ActionResult> Index()
        {
            List<Servico> model = new List<Servico>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Servico/GettbServicoes/");
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Servico>>(result);
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

        // GET: Servico/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Servico servico = db.Servicoes.Find(id);
            if (servico == null)
            {
                return HttpNotFound();
            }
            return View(servico);
        }

        // GET: Servico/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Servico/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "idServico,titulo,descricao,idUsuario,idSubCategoria,idStatus,imagemServico,idDDD")] Servico servico)
        {
            if (ModelState.IsValid)
            {
                db.Servicoes.Add(servico);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(servico);
        }

        // GET: Servico/Edit/5
        public async System.Threading.Tasks.Task<ActionResult> Edit(int? id)
        {
            List<Servico> model = new List<Servico>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Servico/GetServico/?id=" + id);
                        HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                        string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                        model = JsonConvert.DeserializeObject<List<Servico>>(result);
                    }
                    catch (Exception err)
                    {
                        string erro = err.Message;
                    }

                }
                Servico servico = model[0];
                return View(servico);
            }
            else
                return RedirectToAction("Login");
        }

        // POST: Servico/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Servico servico)
        {
            if (ModelState.IsValid)
            {
                using (HttpClient httpclient = new HttpClient())
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Servico/PutServico/" + servico.idServico);
                    var httpResponseMessage = httpclient.PostAsync(uriBuilder.ToString(), new ObjectContent<Servico>(servico, new JsonMediaTypeFormatter()));
                    var result = httpResponseMessage.Result;
                    if (result.IsSuccessStatusCode)
                        return RedirectToAction("Index");
                    else
                        return View(servico);
                }
            }
            return RedirectToAction("Index");
        }

        // GET: Servico/Delete/5
        public async System.Threading.Tasks.Task<ActionResult> Delete(int? id)
        {
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Servico/DeleteServico/" + id);
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    //model = JsonConvert.DeserializeObject<List<Usuario>>(result);

                }
                catch (Exception err)
                {
                    string erro = err.Message;
                }

            }
            return RedirectToAction("Index");
        }

        // POST: Servico/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Servico servico = db.Servicoes.Find(id);
            db.Servicoes.Remove(servico);
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
