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
using BeFreeAPP.Models;
using System.Net.Http;
using Newtonsoft.Json;

namespace BeFreeAPP.Activities
{
    class ConectionActivity : Activity
    {
        public async System.Threading.Tasks.Task<List<Servico>> GetServiceList(List<Servico> Lista)
        {
            using (HttpClient client = new HttpClient())
            {
                Uri uri = new Uri("http://befree.somee.com/BeFreeAPI/api/Servico/");
                var response = await client.GetAsync(uri);
                if (response.IsSuccessStatusCode)
                {
                    var result = response.Content.ReadAsStringAsync().Result;
                    var json = JsonConvert.DeserializeObject(result);
                    Lista = JsonConvert.DeserializeObject<List<Servico>>(json.ToString());
                }
            }
            return Lista;
        }
    }
}