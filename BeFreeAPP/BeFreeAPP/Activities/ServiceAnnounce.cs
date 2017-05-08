using System;
using Android.OS;
using Android.Widget;

namespace BeFreeAPP.Activities
{
    class ServiceAnnounce : ServiceActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.Service_anuncio);

            Button btnVoltar = FindViewById<Button>(Resource.Id.service_anuncio_button_voltar);

            btnVoltar.Click += BtnVoltar_Click;

        }

       private void BtnVoltar_Click(object sender, EventArgs e)
        {
            this.Finish();
        }


    }
}