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

        public async Task<List<Usuario>> GetUsuariosAsync(string url)
        {
            Task<List<Usuario>> clsUsuario = null;
            using (HttpClient client = new HttpClient())
            {
                var uri = new Uri(url);
                var response = await client.GetAsync(url);
                if (response.IsSuccessStatusCode)
                {
                    var result = response.Content.ReadAsStringAsync().Result;
                    var _clsUsuario = JsonConvert.DeserializeObject(result);
                    clsUsuario = JsonConvert.DeserializeObject<Task<List<Usuario>>>(_clsUsuario.ToString());
                    return await clsUsuario;
                }
                return await clsUsuario;
            }
        }
    }
}
