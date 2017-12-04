using BeFreeWeb.Models;
using BeFreeWeb.Utils;
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
        Functions functions = new Functions();

        public async Task<ActionResult> Index()
        {
            if (Session["IsAuthenticated"].ToString() == "true")
            {


                Session["qtdUsuarios"] = await functions.GetQtdUsuarios();
                Session["qtdServicos"]  = await functions.GetQtdServicos();
                Session["qtdBuscas"]  = await functions.GetQtdBuscas();
                Session["qtdDenuncias"] = await functions.GetQtdDenuncias();



                return View();
            }
            else
                return RedirectToAction("Login");
        }


        public ActionResult Login()
        {
            return View();
        }

        public ActionResult Log()
        {
            return View();
        }

        public ActionResult Denuncia()
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
            Session["imagemPerfil"] = model[0].imagemPerfil;
            Session["nomeUsuario"] = model[0].nomeUsuario;
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