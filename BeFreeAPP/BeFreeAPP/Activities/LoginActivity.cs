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
using System.Net.Http;
using BeFreeAPP.Models;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace BeFreeAPP.Activities
{
    [Activity(Label = "LoginActivity")]
    public class LoginActivity : Activity
    {
        DataService dataService = new DataService();
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.Login);

            Button btnLogin = FindViewById<Button>(Resource.Id.btnLogin);
            EditText edtUsuario = FindViewById<EditText>(Resource.Id.edtUsuario);
            EditText edtSenha = FindViewById<EditText>(Resource.Id.edtSenha);

            btnLogin.Click += BtnLogin_Click;

        }

        private async void BtnLogin_Click(object sender, EventArgs e)
        {
            string url = "http://localhost:1994/api/Usuarios";

            //List <Usuario> usuarios =  (<List<Usuario>)dataService.GetObjAsync(url);
            using (HttpClient httpClient = new HttpClient())
            {
                List<Usuario> models = new List<Usuario>();

                try
                {

                    UriBuilder uriBuilder = new UriBuilder(url);
                    HttpResponseMessage httpResponseMessage = await httpClient.GetAsync(uriBuilder.ToString());
                    string result = httpResponseMessage.Content.ReadAsStringAsync().Result;
                    models = JsonConvert.DeserializeObject<List<Usuario>>(result);
                }
                catch (Exception err)
                {
                    string erro = err.Message.ToString();
                }
            }
            /*Task<List<Usuario>> usuarios = dataService.GetObjAsync(url);*/
        }
    }
}