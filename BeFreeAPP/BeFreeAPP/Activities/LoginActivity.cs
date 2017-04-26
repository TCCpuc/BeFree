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

            btnLogin.Click += BtnLogin_Click;

        }

        private async void BtnLogin_Click(object sender, EventArgs e)
        {
            EditText edtUsuario = FindViewById<EditText>(Resource.Id.edtUsuario);
            EditText edtSenha = FindViewById<EditText>(Resource.Id.edtSenha);
            List<Usuario> usuarios = null;
            using (HttpClient client = new HttpClient())
            {
                ;
                Uri uri = new Uri("http://192.168.219.57:8080/api/Usuarios/");
                var response = await client.GetAsync(uri);
                if (response.IsSuccessStatusCode)
                {
                    var result = response.Content.ReadAsStringAsync().Result;
                    var _clsUsuario = JsonConvert.DeserializeObject(result);
                    usuarios = JsonConvert.DeserializeObject<List<Usuario>>(_clsUsuario.ToString());

                    if (usuarios.Where(u => u.nomeUsuario == edtUsuario.Text).Count() > 0) {


                    }
                }
            }

        }
    }
}