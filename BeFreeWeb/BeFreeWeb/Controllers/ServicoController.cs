using BeFreeWeb.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Mvc;

namespace BeFreeWeb.Controllers
{
    public class ServicoController : Controller
    {
        // GET: Servico
        public async System.Threading.Tasks.Task<ActionResult> Index()
        {
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                List<Servico> model = new List<Servico>();
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

                    return View(model);

                }
            }

            return RedirectToAction("Login");
        }

        // GET: Servico/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: Servico/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Servico/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Servico/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: Servico/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Servico/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: Servico/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }
    }
}
