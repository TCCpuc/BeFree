using Android.App;
using Android.Widget;
using Android.OS;
using System.Net.Http;
using Android.Content;
using BeFreeAPP.Activities;

namespace BeFreeAPP
{
    [Activity(Label = "BeFreeAPP", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView (Resource.Layout.Main);

            Button btnLogin = FindViewById<Button>(Resource.Id.btnLogIn);
            Button btnCreateAccount = FindViewById<Button>(Resource.Id.btnCreateAccount);

            btnLogin.Click += BtnLogin_Click;
        }

        private void BtnLogin_Click(object sender, System.EventArgs e)
        {
            Intent login = new Intent(this, typeof(LoginActivity));
            StartActivity(login);
        }
    }
}

