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
    [Activity(Label = "CriarContaActivity")]
    public class CriarContaActivity : Activity
    {

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.CriarConta);

            Button btnCriarContaEfetiva = FindViewById<Button>(Resource.Id.btnCriarContaEfetiva);
            btnCriarContaEfetiva.Click += BtnCriarContaEfetiva_Click;
        }

        private async void BtnCriarContaEfetiva_Click(object sender, EventArgs e)
        {
            EditText edtCriarContaNome = FindViewById<EditText>(Resource.Id.edtCriarContaNome);
            EditText edtCriarContaEmail = FindViewById<EditText>(Resource.Id.edtCriarContaEmail);
            EditText edtCriarContaCPF = FindViewById<EditText>(Resource.Id.edtCriarContaCPF);
            EditText edtCriarContaSenha = FindViewById<EditText>(Resource.Id.edtCriarContaSenha);
            EditText edtCriarContaSenhaConf = FindViewById<EditText>(Resource.Id.edtCriarContaSenhaConf);

            Usuario usuario = new Usuario();

            usuario.nomeUsuario = edtCriarContaNome.Text;
            usuario.email = edtCriarContaEmail.Text;
            usuario.cpf = edtCriarContaCPF.Text;
            usuario.senha = edtCriarContaSenha.Text;

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    Uri uri = new Uri("http://befree.somee.com/BeFreeAPI/api/Usuarios/");
                    var json = JsonConvert.SerializeObject(usuario);
                    HttpResponseMessage response = await client.PostAsync(uri, new StringContent(json, Encoding.UTF8, "application/json"));
                    if (response.IsSuccessStatusCode)
                    {
                        Toast.MakeText(this, "Login realizado com sucesso", ToastLength.Short).Show();
                    }

                }
            }
            catch (Exception err)
            {
                string erro = err.Message;
            }
        }
    }
}