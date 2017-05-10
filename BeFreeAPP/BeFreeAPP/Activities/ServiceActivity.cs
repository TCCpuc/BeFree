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

    [Activity(Label = "ServiceActivity")]
    class ServiceActivity : Activity
    {
        private List<Servico> serviceItens { set; get; }
        private ListView serviceListView;
        protected override async void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Service);

            serviceListView = FindViewById<ListView>(Resource.Id.ServiceView);
            serviceItens = new List<Servico>();

            using (HttpClient client = new HttpClient())
            {

                Uri uri = new Uri("http://befree.somee.com/BeFreeAPI/api/Servico/");
                var response = await client.GetAsync(uri);
                if (response.IsSuccessStatusCode)
                {
                    var result = response.Content.ReadAsStringAsync().Result;
                    var json = JsonConvert.DeserializeObject(result);
                    serviceItens = JsonConvert.DeserializeObject<List<Servico>>(json.ToString());
                }
            }

            ServiceAdapter adapter = new ServiceAdapter(this, serviceItens);

            serviceListView.Adapter = adapter;

            serviceListView.ItemClick += serviceListView_ItemClick;


        }

        private void serviceListView_ItemClick(object sender, AdapterView.ItemClickEventArgs e)
        {

            //ServiceAnnounce sv = new ServiceAnnounce();
            SetContentView(Resource.Layout.ServiceAnuncio);

            TextView txtTitulo = FindViewById<TextView>(Resource.Id.txtServiceAnuncioTitulo);
            txtTitulo.Text = serviceItens[e.Position].titulo;
            TextView txtSubCategoria = FindViewById<TextView>(Resource.Id.txtServiceAnuncioSubCategoria);
            txtSubCategoria.Text = serviceItens[e.Position].idSubCategoria.ToString();
            TextView txtDescricao = FindViewById<TextView>(Resource.Id.txtServiceAnuncioDescricao);
            txtDescricao.Text = serviceItens[e.Position].descricao;

            Button btnVoltar = FindViewById<Button>(Resource.Id.btnServiceAnuncioVoltar);

            btnVoltar.Click += BtnVoltar_Click;
            
          
        }
        private void BtnVoltar_Click(object sender, EventArgs e)
        {
            this.Finish();
        }
    }
}