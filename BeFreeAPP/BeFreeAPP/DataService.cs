using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using System.Threading.Tasks;
using System.Net.Http;
using Newtonsoft.Json;
using BeFreeAPP.Models;

namespace BeFreeAPP
{
    class DataService
    {

        public async Task<List<Object>> GetObjAsync(string url)
        {
            using (HttpClient httpClient = new HttpClient())
            {
                List<Object> models = new List<Object>();
                try
                {

                    UriBuilder uriBuilder = new UriBuilder(url);
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    models = JsonConvert.DeserializeObject<List<Object>>(result);
                }
                catch (Exception err)
                {
                    string erro = err.Message.ToString();
                }
                return models;
            }
        }
    }
}