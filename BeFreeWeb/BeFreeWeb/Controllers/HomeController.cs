using BeFreeWeb.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace BeFreeWeb.Controllers
{
    public class HomeController : Controller
    {
        public async Task<ActionResult> Index()
        {
            List<Usuario> model = new List<Usuario>();
            if (Session["IsAuthenticated"].ToString() == "true")
            {

                using (HttpClient httpClient = new HttpClient())
                {
                    try
                    {
                        UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/GetUsuario/" + Session["IdUsuarioAtual"]);
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


        public ActionResult Login()
        {
            return View();
        }

        public ActionResult Users()
        {
            return View();
        }

        public ActionResult Adverts()
        {
            return View();
        }

        // GET: Emitente/Details/5
        public async Task<ActionResult> EfetuarLogin(Usuario usuario)
        {
            List<Usuario> model = new List<Usuario>();            
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/GetUsuarioByEmail/?email=" + usuario.email);
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    model = JsonConvert.DeserializeObject<List<Usuario>>(result);
                }
                catch (Exception err)
                {
                    string erro = err.Message;
                }

                if (model.Count == 0)
                {
                    ModelState.AddModelError("", "Usuário inválido! Favor tentar novamente.");
                    return RedirectToAction("Login", model);
                }

                if (model[0].senha != usuario.senha)
                {
                    ModelState.AddModelError("", "Senha inválida! Favor tentar novamente.");
                    return RedirectToAction("Login", model);
                }

            }

            Session["IsAuthenticated"] = "true";
            Session["IdUsuarioAtual"] = model[0].idUsuario;
            return RedirectToAction("Index");
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}