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
        public ActionResult Edit(Usuario usuario)
        {
            if (ModelState.IsValid)
            {
                using (HttpClient httpclient = new HttpClient())
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/PutUsuario/" + usuario.idUsuario);
                    var httpResponseMessage = httpclient.PostAsync(uriBuilder.ToString(), new ObjectContent<Usuario>(usuario, new JsonMediaTypeFormatter()));
                    var result = httpResponseMessage.Result;
                    if (result.IsSuccessStatusCode)
                        return RedirectToAction("Index");
                    else
                        return View(usuario);
                }
            }
            return RedirectToAction("Index");
        }

        // GET: Usuario/Delete/5
        public async System.Threading.Tasks.Task<ActionResult> Delete(int? id)
        {
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/DeleteUsuario/" + id);
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

        // POST: Usuario/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async System.Threading.Tasks.Task<ActionResult> DeleteConfirmed(int id)
        {
            return View();
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
