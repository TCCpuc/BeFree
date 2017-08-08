package tcc.befree.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.telas.listaDeBuscas.SearchFragment;
import tcc.befree.telas.listaDeServicos.ServiceFragment;
import tcc.befree.telas.perfil.UserPerfil;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int idUsuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_default);
        ab.setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                if (tabLayout.getSelectedTabPosition() == 0) {
                    intent = new Intent(MainActivity.this, CreateServicoActivity.class);
                }
                else{
                    intent = new Intent(MainActivity.this, CreateBuscaActivity.class);
                }
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getBundleExtra("idUsuario");
        try {
            idUsuario = bundle.getInt("idUsuario");
        }catch(Exception e){
            idUsuario = 0;
        }

        setTitle("Befree");
        //Instancia menu drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        //int id = getIntent().getExtras().getInt("bundle");
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        if (viewPager != null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mSectionsPagerAdapter);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupDrawerContent(NavigationView navigationView) {

        //navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
            /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/

        TextView mTextViewNomeUsuario = (TextView)header.findViewById(R.id.nome_usuario_menu);
        TextView mTextViewEmailUsuario = (TextView)header.findViewById(R.id.email_usuario_menu);
        ImageView mImagePerfil = (ImageView) header.findViewById(R.id.img_usuario_menu);

        String nomeUsuario = getIntent().getStringExtra("nomeUsuario");
        String emailUsuario = getIntent().getStringExtra("emailUsuario");
        Picasso.with(this).load(getIntent().getStringExtra("imagemPerfil")).into(mImagePerfil);

        mTextViewNomeUsuario.setText(nomeUsuario);
        mTextViewEmailUsuario.setText(emailUsuario);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @SuppressWarnings("StatementWithEmptyBody")
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        if (id == R.id.menu_anuncios) {

                            id = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",idUsuario);
                            Intent intent = MainActivity.this.getIntent();
                            intent.putExtra("bundle", bundle);
                            /*Intent i = MainActivity.this.getIntent();
                            startActivity(i);*/


                            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                            if (viewPager != null) {
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                viewPager.setAdapter(mSectionsPagerAdapter);
                            }
                            MainActivity.super.setTitle("Meus Anuncios");
                            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setupWithViewPager(viewPager);

                        } else if (id == R.id.menu_perfil) {
                            // ABRIR MEU PERFIL
                            Intent intent = new Intent(MainActivity.this, UserPerfil.class);

                            startActivity(intent);



                        } else if (id == R.id.menu_pagina_inicial) {
                            id = 0;
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id);
                            Intent intent = MainActivity.this.getIntent();
                            intent.putExtra("bundle", bundle);
                            /*Intent i = MainActivity.this.getIntent();
                            startActivity(i);*/


                            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                            if (viewPager != null) {
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                viewPager.setAdapter(mSectionsPagerAdapter);
                            }
                            MainActivity.super.setTitle("Befree");
                            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setupWithViewPager(viewPager);

                        } else if (id == R.id.nav_share) {

                        } else if (id == R.id.nav_send) {

                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Logout")
                    .setMessage("Você tem certeza que deseja sair do Befree?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();

            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {


                Bundle bundle = new Bundle();
                bundle.putString("search",searchQuery);
                Intent intent = MainActivity.this.getIntent();
                intent.putExtra("search", bundle);
                            /*Intent i = MainActivity.this.getIntent();
                            startActivity(i);*/


                ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                if (viewPager != null) {
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(mSectionsPagerAdapter);
                }
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);




               //COLOCA AQUI O METODO DA BUSCA
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;



        //getMenuInflater().inflate(R.menu.main, menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        //SE O DRAWER NAO ABRIR TENTAR RETURN TRUE
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new ServiceFragment();
                case 1:
                    return new SearchFragment();
                default:
                    return new ServiceFragment();
            }
        }


        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Serviço";
                case 1:
                    return "Busca";
            }
            return null;
        }
    }

}
