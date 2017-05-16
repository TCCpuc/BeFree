using Android.App;
using Android.Widget;
using Android.Runtime;
using Android.OS;
using Android.Views;
using System.Net.Http;
using Android.Content;
using BeFreeAPP.Activities;
using System.Collections.Generic;
using BeFreeAPP.Models;
using System;


namespace BeFreeAPP
{
    [Activity(Label = "BeFreeAPP", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);


            Button btnLogin = FindViewById<Button>(Resource.Id.btnLogIn);
            Button btnCreateAccount = FindViewById<Button>(Resource.Id.btnCreateAccount);

            btnLogin.Click += BtnLogin_Click;
            btnCreateAccount.Click += BtnCreateAccount_Click;
        }

        private void BtnCreateAccount_Click(object sender, EventArgs e)
        {
            Intent criarConta = new Intent(this, typeof(CriarContaActivity));
            StartActivity(criarConta);
        }

        private void BtnLogin_Click(object sender, System.EventArgs e)
        {
            Intent login = new Intent(this, typeof(LoginActivity));
            StartActivity(login);
        }
    }
}

