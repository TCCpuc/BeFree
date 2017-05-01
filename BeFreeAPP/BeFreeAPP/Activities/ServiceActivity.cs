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

    [Activity(Label = "ServiceActivity")]
    class ServiceActivity : Activity
    {
        private List<Servico> serviceItens;
        private ListView serviceListView;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.Service);
            serviceListView = FindViewById<ListView>(Resource.Id.ServiceView);

            serviceItens = new List<Servico>();

            serviceItens.Add(new Servico()
            {
                nome = "Pintor Rodape",
                categoria = "Construcao Civil",
                subcategoria = "Pintor",
                cidade = "Campinas",
                bairro = "Cidade Universitaria",
                descricao = "Pinto o rodape",
                id_servico = 001,
                cpf = 12345665489
            });
            serviceItens.Add(new Servico()
            {
                nome = "Trocador de Pneu",
                categoria = "Veiculos",
                subcategoria = "Manutencao",
                cidade = "Campinas",
                bairro = "Cidade Universitaria",
                descricao = "Troco Pneu",
                id_servico = 002,
                cpf = 12343335489
            });
            serviceItens.Add(new Servico()
            {
                nome = "Aulas de Matematia",
                categoria = "Professor",
                subcategoria = "Professor particular",
                cidade = "Campinas",
                bairro = "Cidade Universitaria",
                descricao = "Dou aulas de Matematica",
                id_servico = 003,
                cpf = 12345895489
            });
            serviceItens.Add(new Servico()
            {
                nome = "Fabrico Picole",
                categoria = "Outros",
                subcategoria = "alimenticio",
                cidade = "Campinas",
                bairro = "Cidade Universitaria",
                descricao = "Fabrico sorvete",
                id_servico = 004,
                cpf = 12345665490
            });

            ServiceAdapter adapter = new ServiceAdapter(this, serviceItens);

            serviceListView.Adapter = adapter;

            serviceListView.ItemClick += serviceListView_ItemClick;


        }

        private void serviceListView_ItemClick(object sender, AdapterView.ItemClickEventArgs e)
        {
            SetContentView(Resource.Layout.Service_anuncio);

            TextView txtTitulo = FindViewById<TextView>(Resource.Id.service_anuncio_titulo);
            txtTitulo.Text = serviceItens[e.Position].nome;
            TextView txtSubCategoria = FindViewById<TextView>(Resource.Id.service_anuncio_categoria);
            txtSubCategoria.Text = serviceItens[e.Position].subcategoria;
            TextView txtCidade = FindViewById<TextView>(Resource.Id.service_anuncio_cidade);
            txtCidade.Text = serviceItens[e.Position].cidade;
            TextView txtBairro = FindViewById<TextView>(Resource.Id.service_anuncio_bairro);
            txtBairro.Text = serviceItens[e.Position].bairro;
            TextView txtDescricao = FindViewById<TextView>(Resource.Id.service_anuncio_descricao);
            txtDescricao.Text = serviceItens[e.Position].descricao;

            Button btnVoltar = FindViewById<Button>(Resource.Id.service_anuncio_button_voltar);

            btnVoltar.Click += BtnVoltar_Click;
        }

        private void BtnVoltar_Click(object sender, EventArgs e)
        {
            this.Finish();
        }
    }
}