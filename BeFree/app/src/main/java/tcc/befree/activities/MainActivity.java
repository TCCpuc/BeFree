package tcc.befree.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Categoria;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Busca;
import tcc.befree.models.DDD;
import tcc.befree.models.Evento;
import tcc.befree.models.Mensagem;
import tcc.befree.models.MensagensNaoLidas;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Dialog.AdvancedSearchDialog;
import tcc.befree.telas.Dialog.LoadingDialog;
import tcc.befree.telas.listaDeBuscas.SearchFragment;
import tcc.befree.telas.listaDeServicos.ServiceFragment;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    private DrawerLayout drawer;
    private AppBarLayout appbar;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Button search_advanced_button;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private AdvancedSearchDialog dialog;
    private ServiceFragment serviceFragment;
    private SearchFragment searchFragment;
    private ViewPager viewPager;
    private String categoriaBuscaAvancada = "Todos";
    private String dddBuscaAvancada = "Todos";
    private String subcategoriaBuscaAvancada = "Todos";
    private String buscaSimples = "";
    private ApiModels api;
    private Usuarios usuario;
    private NavigationView navigationView;
    private ArrayList<Categoria> categorias;
    private ArrayList<Integer> subCategoriasDaCategoria;
    private ArrayList<DDD> ddds;
    private ArrayList<SubCategoria> subCategorias;
    private ArrayList<Evento> gender;
    private LoadingDialog loginDialog;
    private MensagensNaoLidas mensagensNotificacao;
    private int eventosNotificacao;
    private int ultimaPosicao = 0;
    private int abaAtual = 0;
    private int currentPage;
    private int idcategoriaBuscaAvancada = 0;
    private int idDDDBuscaAvancada = 0;
    private int idSubCategoriaBuscaAvancada = 0;
    private int id = 0;
    private int activityBrequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.container);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        search_advanced_button = (Button) findViewById(R.id.search_advanced_button);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        eventosNotificacao = 0;
        api = new ApiModels();
        Intent it = this.getIntent();
        Bundle loginActivityIntent = it.getExtras();
        String x [] = loginActivityIntent.getString("arrayUsuario").split("%");
        id = Integer.parseInt(x[0]);
        subCategoriasDaCategoria = new ArrayList<>();
        searchFragment = new SearchFragment();
        serviceFragment = new ServiceFragment();

        startLoadingDialog();
        threadLoadingUpdate();

    }

    private void setNavItemCount(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? (String.valueOf(count) + " ") : null);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        //navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
            /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/

        TextView mTextViewNomeUsuario = (TextView)header.findViewById(R.id.nome_usuario_menu);
        TextView mTextViewEmailUsuario = (TextView)header.findViewById(R.id.email_usuario_menu);
        CircleImageView mImagePerfil = (CircleImageView) header.findViewById(R.id.img_usuario_menu);

        String nomeUsuario = usuario.nomeUsuario;
        String emailUsuario = usuario.email;
        Picasso.with(this).load(usuario.imagemPerfil).into(mImagePerfil);

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

                            Bundle bundle = new Bundle();
                            bundle.putInt("id",usuario.idUsuario);
                            Intent intent = MainActivity.this.getIntent();
                            intent.putExtra("bundle", bundle);
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
                            Intent intent = new Intent(MainActivity.this, UserPerfilActivity.class);
                            intent.putExtra("arrayUsuario", usuario.toString());
                            startActivityForResult(intent, activityBrequestCode);

                        } else if (id == R.id.menu_calendario) {
                            // ABRIR Agenda
                            Intent intent = new Intent(MainActivity.this, GenderActivity.class);
                            intent.putExtra("idUsuario", usuario.idUsuario);
                            startActivityForResult(intent, activityBrequestCode);

                        } else if (id == R.id.menu_chat) {
                            // ABRIR CHAT
                            Bundle bundle = new Bundle();
                            bundle.putInt("idUsuario",usuario.idUsuario);
                            Intent intent = new Intent(MainActivity.this, ListChatActivity.class);
                            intent.putExtra("bundle", bundle);
                            startActivityForResult(intent, activityBrequestCode);

                        } else if (id == R.id.menu_sobre) {
                            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivityForResult(intent, activityBrequestCode);

                        } else if (id == R.id.menu_pagina_inicial) {
                            id = 0;
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id);
                            Intent intent = MainActivity.this.getIntent();
                            intent.putExtra("bundle", bundle);
                            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                            if (viewPager != null) {
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                viewPager.setAdapter(mSectionsPagerAdapter);
                            }
                            MainActivity.super.setTitle("Befree");
                            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setupWithViewPager(viewPager);

                        } else if (id == R.id.menu_logoff) {
                            drawer.closeDrawer(GravityCompat.START);
                            logoff();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            logoff();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.activityBrequestCode && resultCode == RESULT_OK){
            startLoadingDialog();
            threadLoadingUpdate();
        }
        threadUpdateNotification();
    }

    private void logoff(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Você tem certeza que deseja sair do Befree?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("Não", null)
                .show();
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

                if(hasFocus){
                    search_advanced_button.setVisibility(View.VISIBLE);
                }else {
                    search_advanced_button.setVisibility(View.GONE);
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                //Busca simples
                buscaSimples = searchQuery;
                busca();
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
    }

    private void busca() {
        for (Busca b : searchFragment.results) {
            //Busca por titulo e descrição
            b.mostrar = b.titulo.toLowerCase().contains(buscaSimples.toLowerCase())
                    || b.descricao.toLowerCase().contains(buscaSimples.toLowerCase());

            if (!"Todos".equals(categoriaBuscaAvancada) && b.mostrar){
                b.mostrar = b.mostrar && subCategoriasDaCategoria.contains(b.idSubCategoria);
            }

            if (!"Todos".equals(dddBuscaAvancada)){
                b.mostrar = b.mostrar && b.idDDD == idDDDBuscaAvancada;
            }

            if (!"Todos".equals(subcategoriaBuscaAvancada)){
                b.mostrar = b.mostrar && b.idSubCategoria == idSubCategoriaBuscaAvancada;
            }
        }
        searchFragment.setRealizouBusca(true);

        for (Servico s : serviceFragment.results) {
            //Busca por titulo e descrição
            s.setMostrar(s.getTitulo().toLowerCase().contains(buscaSimples.toLowerCase()) || s.getDescricao().toLowerCase().contains(buscaSimples.toLowerCase()));


            if (!"Todos".equals(categoriaBuscaAvancada) && s.isMostrar()){
                s.setMostrar(s.isMostrar() && subCategoriasDaCategoria.contains(s.getIdSubCategoria()));
            }

            if (!"Todos".equals(dddBuscaAvancada)){
                s.setMostrar(s.isMostrar() && s.getIdDDD() == idDDDBuscaAvancada);
            }

            if (!"Todos".equals(subcategoriaBuscaAvancada)){
                s.setMostrar(s.isMostrar() && s.getIdSubCategoria() == idSubCategoriaBuscaAvancada);
            }
        }
        serviceFragment.setRealizouBusca(true);

        refreshLista();
    }

    private void refreshLista() {
        new Thread(){
            @Override
            public void run() {
                abaAtual = viewPager.getCurrentItem();
                viewPager = (ViewPager) findViewById(R.id.container);
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (viewPager != null) {
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(mSectionsPagerAdapter);
                }
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(abaAtual);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the current view position from the ViewPager by
     * extending SimpleOnPageChangeListener class and adding your method
     */
    public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        public final int getCurrentPage() {
            return currentPage;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==1) {
                return searchFragment;
            }
            else {
                return serviceFragment;
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


    private void threadUpdateNotification(){
        new Thread(){
            @Override
            public void run() {
                Evento ev;
                mensagensNotificacao = new MensagensNaoLidas();
                eventosNotificacao = 0;
                mensagensNotificacao = api.getNumMensagemNaoLida(id);
                gender = api.getEventosbyIdUsuario(id);
                for(int x = 0; x < gender.size(); x++){
                    ev = gender.get(x);
                    if(oldDate(ev.getDtEvento())){
                        if(!ev.isAvaliado()){
                            eventosNotificacao++;
                        }
                    }else{
                        if(ev.getSituacaoEvento() == 0){
                            if (ev.getIdUsuarioContratante() != id){
                                eventosNotificacao++;
                            }
                        }
                    }
                }
                threadUINotification();
            }
        }.start();
    }

    private void threadUINotification(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setNavItemCount(R.id.menu_calendario, eventosNotificacao);
                setNavItemCount(R.id.menu_chat, mensagensNotificacao.getNumeroMensagens());
            }
        });
    }


    private void threadLoadingUpdate(){
        new Thread(){
            @Override
            public void run() {
                usuario = api.getUsuarioById(id);
            }
        }.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(){
            @Override
            public void run() {
                categorias = api.getCategorias();
                subCategorias = api.getSubCategorias();
                ddds = api.getDDDs();
                threadLoadingUI();
            }
        }.start();
    }

    private void threadLoadingUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchFragment.setIdUsuario(id);
                serviceFragment.setIdUsuario(id);

                setSupportActionBar(toolbar);

                search_advanced_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = new AdvancedSearchDialog(MainActivity.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.show();
                        Button saveButton = (Button)dialog.findViewById(R.id.advanced_search_dialog_button_ok);
                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                categoriaBuscaAvancada = ((Spinner)dialog.findViewById(R.id.advanced_search_dialog_categoria_spinner)).getSelectedItem().toString();
                                subcategoriaBuscaAvancada = ((Spinner)dialog.findViewById(R.id.advanced_search_dialog_sub_categoria_spinner)).getSelectedItem().toString();
                                dddBuscaAvancada = ((Spinner)dialog.findViewById(R.id.advanced_search_dialog_ddd_spinner)).getSelectedItem().toString();


                                for (Categoria c: categorias) {
                                    if (c.descricao.equals(categoriaBuscaAvancada)) {
                                        idcategoriaBuscaAvancada = c.idCategoria;
                                        break;
                                    }
                                }

                                subCategoriasDaCategoria = api.getSubCategoriasDaCategoria(idcategoriaBuscaAvancada);

                                for (DDD ddd: ddds) {
                                    if (dddBuscaAvancada.contains(ddd.descricao)) {
                                        idDDDBuscaAvancada = ddd.id;
                                        break;
                                    }
                                }


                                for (SubCategoria s: subCategorias) {
                                    if (s.descricao.equals(subcategoriaBuscaAvancada)) {
                                        idSubCategoriaBuscaAvancada = s.idSubCategoria;
                                        break;
                                    }
                                }
                                dialog.dismiss();
                                busca();
                            }
                        });
                    }
                });

                final ActionBar ab = getSupportActionBar();
                ab.setHomeAsUpIndicator(R.drawable.ic_menu_default);
                ab.setDisplayHomeAsUpEnabled(true);


                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = null;
                        Bundle bundle = new Bundle();
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                        if (tabLayout.getSelectedTabPosition() == 0) {
                            intent = new Intent(MainActivity.this, CreateServicoActivity.class);
                        }
                        else{
                            intent = new Intent(MainActivity.this, CreateBuscaActivity.class);
                        }
                        bundle.putInt("idUsuario",usuario.idUsuario);
                        intent.putExtra("idUsuario", bundle);
                        startActivity(intent);
                    }
                });

                setTitle("Befree");

                if (navigationView != null) {
                    setupDrawerContent(navigationView);
                }
                //int id = getIntent().getExtras().getInt("bundle");
                refreshLista();
                threadUpdateNotification();
                stopLoadingDialog();
            }
        });
    }

    private void startLoadingDialog(){
        loginDialog = new LoadingDialog(MainActivity.this);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.show();
    }

    private void stopLoadingDialog(){
        loginDialog.dismiss();
    }

    public boolean oldDate(String data){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today [] = dateFormat.format(date).split("/");
        String oldDay [] = data.split("/");
        int oDia = Integer.parseInt(oldDay[0]);
        int oMes = Integer.parseInt(oldDay[1]);
        int oAno = Integer.parseInt(oldDay[2]);
        int tDia = Integer.parseInt(today[0]);
        int tMes = Integer.parseInt(today[1]);
        int tAno = Integer.parseInt(today[2]);

        if(oAno < tAno){
            return true;
        }else if(oMes < tMes && oAno == tAno ){
            return true;
        }else if(oDia < tDia && oMes == tMes && oAno == tAno){
            return true;
        }else {
            return false;
        }
    }
}
