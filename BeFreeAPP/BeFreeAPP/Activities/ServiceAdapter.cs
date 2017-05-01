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
    public class ServiceAdapter : BaseAdapter<Servico>
    {

        private List<Servico> serviceItens;
        private Context serviceContext;

        public ServiceAdapter(Context context , List<Servico> itens)
        {
            serviceItens = itens;
            serviceContext = context;
        }

        public override int Count
        {
            get
            {
                return serviceItens.Count;
            }
        }

        public override Servico this[int position]
        {
            get
            {
                return serviceItens[position];
            }
        }

        public override long GetItemId(int position)
        {
            return position;
        }

        public override View GetDropDownView(int position, View convertView, ViewGroup parent)
        {
            return base.GetDropDownView(position, convertView, parent);
        }


        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;

            if(row == null)
            {
                row = LayoutInflater.From(serviceContext).Inflate(Resource.Layout.Service_row, null, false);
            }

            TextView txtNome = row.FindViewById<TextView>(Resource.Id.servico_row_txt);
            txtNome.Text = serviceItens[position].nome;
            TextView txtCidade = row.FindViewById<TextView>(Resource.Id.cidade_row_txt);
            txtCidade.Text = serviceItens[position].cidade;
            TextView txtBairro = row.FindViewById<TextView>(Resource.Id.bairro_row_txt);
            txtBairro.Text = serviceItens[position].bairro;
            TextView txtSubCategoria = row.FindViewById<TextView>(Resource.Id.subcategoria_row_txt);
            txtSubCategoria.Text = serviceItens[position].subcategoria;


            return row;

        }


       /* public void setServiceItens(string Item)
        {
            serviceItens.Add(Item);
        }
        public List<string> getServiceItens()
        {
            return serviceItens;
        }
        */

    }
}