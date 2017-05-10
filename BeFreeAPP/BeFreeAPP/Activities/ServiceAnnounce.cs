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

namespace BeFreeAPP.Activities
{
    class ServiceAnnounce : ServiceActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.ServiceAnuncio);

            Button btnVoltar = FindViewById<Button>(Resource.Id.btnServiceAnuncioVoltar);

            btnVoltar.Click += BtnVoltar_Click;

        }

       private void BtnVoltar_Click(object sender, EventArgs e)
        {
            this.Finish();
        }


    }
}