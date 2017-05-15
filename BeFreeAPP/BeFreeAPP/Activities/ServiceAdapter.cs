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
        private SlidingTabsFragment.SamplePagerAdapter samplePagerAdapter;

        public ServiceAdapter(Context context , List<Servico> itens)
        {
            serviceItens = itens;
            serviceContext = context;
        }

        public ServiceAdapter(SlidingTabsFragment.SamplePagerAdapter samplePagerAdapter, List<Servico> serviceItens)
        {
            this.samplePagerAdapter = samplePagerAdapter;
            this.serviceItens = serviceItens;
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
                row = LayoutInflater.From(serviceContext).Inflate(Resource.Layout.ServiceList, null, false);
            }
            //Aqui que vai filtrar?
            TextView txtTitulo = row.FindViewById<TextView>(Resource.Id.txtServiceListTitulo);
            txtTitulo.Text = serviceItens[position].titulo;
            TextView txtDescricao = row.FindViewById<TextView>(Resource.Id.txtServiceListDescricao);
            txtDescricao.Text = serviceItens[position].descricao;
            TextView txtSubCategoria = row.FindViewById<TextView>(Resource.Id.txtServiceListSubCategoria);
            txtSubCategoria.Text = serviceItens[position].idSubCategoria.ToString();
            
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