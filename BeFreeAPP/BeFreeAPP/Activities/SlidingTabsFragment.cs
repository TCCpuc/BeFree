using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Android.Support.V4.View;
using BeFreeAPP.Models;

namespace BeFreeAPP.Activities
{
    public class SlidingTabsFragment : Fragment
    {
        ConectionActivity con = new ConectionActivity();
        private List<Servico> serviceItens = new List<Servico>();
        private SlidingTabScrollView mSlidingTabScrollView;
        private ViewPager mViewPager;

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.Inflate(Resource.Layout.Service, container, false);
        }

        public override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            mSlidingTabScrollView = view.FindViewById<SlidingTabScrollView>(Resource.Id.sliding_tabs);
            mViewPager = view.FindViewById<ViewPager>(Resource.Id.viewpager);
            mViewPager.Adapter = new SamplePagerAdapter();

            mSlidingTabScrollView.ViewPager = mViewPager;
        }

        /*public async  override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            await con.GetServiceList(serviceItens);
            mSlidingTabScrollView = view.FindViewById<SlidingTabScrollView>(Resource.Id.sliding_tabs);
            mViewPager = view.FindViewById<ViewPager>(Resource.Id.viewpager);
            mViewPager.Adapter = new SamplePagerAdapter(this.serviceItens);       
            mSlidingTabScrollView.ViewPager = mViewPager;
        }*/

        public class SamplePagerAdapter : PagerAdapter
        {
            List<string> items = new List<string>();
            private List<Servico> serviceItens;

            public SamplePagerAdapter() : base()
            {
                items.Add("Anuncio");
                items.Add("Busca");
                items.Add("Cadastro");

            }

            public SamplePagerAdapter(List<Servico> serviceItens)
            {
                this.serviceItens = serviceItens;
            }

            public override int Count
            {
                get { return items.Count; }
            }

            public override bool IsViewFromObject(View view, Java.Lang.Object obj)
            {
                return view == obj;
            }


            public override Java.Lang.Object InstantiateItem(ViewGroup container, int position)
            {
                View view = LayoutInflater.From(container.Context).Inflate(Resource.Layout.pager_item, container, false);
                container.AddView(view);
                TextView txtTitle = view.FindViewById<TextView>(Resource.Id.txtServiceListTitulo);
                int pos = position + 1;
                txtTitle.Text = pos.ToString();
                /*
                
                ListView serviceListView = view.FindViewById<ListView>(Resource.Id.ServiceView); 
                ServiceAdapter adapter = new ServiceAdapter(this, serviceItens);
                serviceListView.Adapter = adapter;
                */
                return view;
            }

            public string GetHeaderTitle(int position)
            {
                return items[position];
            }

            public override void DestroyItem(ViewGroup container, int position, Java.Lang.Object obj)
            {
                container.RemoveView((View)obj);
            }
        }
    }
}