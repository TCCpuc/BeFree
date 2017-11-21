using BeFreeWeb.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Http;
using System.Web;

namespace BeFreeWeb.Utils
{
    public class Functions
    {
        public async System.Threading.Tasks.Task<int> GetQtdUsuarios()
        {
            List<Usuario> model = new List<Usuario>();
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Usuarios/GettbUsuarios/");
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    model = JsonConvert.DeserializeObject<List<Usuario>>(result);

                }
                catch (Exception err) { return 0; }
            }
            return model.Count;
        }

        public async System.Threading.Tasks.Task<int> GetQtdServicos()
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
                catch (Exception err) { return 0; }
            }
            return model.Count;
        }

        public async System.Threading.Tasks.Task<int> GetQtdBuscas()
        {
            List<Busca> model = new List<Busca>();
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Busca/GettbBuscas/");
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    model = JsonConvert.DeserializeObject<List<Busca>>(result);

                }
                catch (Exception err) { return 0; }
            }
            return model.Count;
        }

        public async System.Threading.Tasks.Task<int> GetQtdDenuncias()
        {
            List<Denuncia> model = new List<Denuncia>();
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    UriBuilder uriBuilder = new UriBuilder(ConfigurationManager.AppSettings["UrlApi"] + "Denuncia/GettbDenuncias/");
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    model = JsonConvert.DeserializeObject<List<Denuncia>>(result);

                }
                catch (Exception err) { return 0; }
            }
            return model.Count;
        }

    }
}