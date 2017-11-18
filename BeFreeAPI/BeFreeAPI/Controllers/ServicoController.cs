using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using BeFreeAPI.Models;
using BeFreeAPI.Utils;
using System.Drawing;
using System.Configuration;

namespace BeFreeAPI.Controllers
{
    public class ServicoController : ApiController
    {
        private BeFreeContext db = new BeFreeContext();
        private Function function = new Function();
        // GET: api/Servico
        public IQueryable<Servico> GettbServicoes()
        {
            return db.tbServicoes;
        }        

        // GET: api/Servico/5
        [ResponseType(typeof(Servico))]
        public IHttpActionResult GetServico(int id)
        {
            IQueryable<Servico> servico = db.tbServicoes.Where(s => s.idServico == id);
            if (servico == null)
            {
                return NotFound();
            }

            return Ok(servico); 
        }

        // GET: api/Servico/GetServicoBySubCategoria/?idSubcategoria=1
        [ResponseType(typeof(Servico))]
        public IHttpActionResult GetServicoBySubCategoria(int idSubcategoria)
        {
            IQueryable<Servico> servico = db.tbServicoes.Where(s => s.idSubCategoria == idSubcategoria);
            if (servico == null)
            {
                return NotFound();
            }

            return Ok(servico);
        }

        // GET: api/Servico/GetServicoBySubCategoria/?idSubcategoria=1
        [ResponseType(typeof(Servico))]
        public IHttpActionResult GetServicoByUsuario(int idUsuario)
        {
            IQueryable<Servico> servico = db.tbServicoes.Where(s => s.idUsuario == idUsuario);
            if (servico == null)
            {
                return NotFound();
            }

            return Ok(servico);
        }

        // GET: api/Servico/GetServicoLike/?busca=1
        [ResponseType(typeof(Servico))]
        public IHttpActionResult GetServicoByLike(String busca)
        {

            
            String str = "SELECT * " +
                         "FROM tbServico AS S " +  // +, tbSubCategoria SC, tbCategoria AS C " +
                         "WHERE S.titulo LIKE '%" + busca + "%'" +
                         "   OR S.descricao LIKE '%" + busca + "%' ";
            /*
            "   OR SC.descricao LIKE '%" + busca + "%' " +
            "   OR C.descricao LIKE '%" + busca + "%') " +
            "  AND SC.idSubcategoria = S.idSubCategoria " +
            "  AND C.idCategoria = SC.idCategoria";
            */

            var servico = db.tbServicoes.SqlQuery(str);

            return Ok(servico);
        }

        // PUT: api/Servico/5
        [ResponseType(typeof(void))]
        [HttpPost]
        public IHttpActionResult PutServico(int id, Servico servico)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != servico.idServico)
            {
                return BadRequest();
            }

            if (!servico.imagemServico.Contains("http:")) {
                servico.imagemServico = this.SetImagem(servico);
            }

            db.Entry(servico).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ServicoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Servico
        [ResponseType(typeof(Servico))]
        public IHttpActionResult PostServico(Servico servico)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            servico.imagemServico = this.SetImagem(servico);

            db.tbServicoes.Add(servico);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (ServicoExists(servico.idServico))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = servico.idServico }, servico);
        }

        // DELETE: api/Servico/5
        [ResponseType(typeof(Servico))]
        [HttpGet]
        public IHttpActionResult DeleteServico(int id)
        {
            Servico servico = db.tbServicoes.Find(id);
            if (servico == null)
            {
                return NotFound();
            }

            db.tbServicoes.Remove(servico);
            db.SaveChanges();

            return Ok(servico);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ServicoExists(int id)
        {
            return db.tbServicoes.Count(e => e.idServico == id) > 0;
        }

        private string SetImagem(Servico servico)
        {

            Random random = new Random();

            string nomeImage = "servicos\\" + random.Next(3000000).ToString() + random.Next(3000000).ToString() + random.Next(3000000).ToString() + ".jpeg";

            Image image = function.Base64ToImage(servico.imagemServico);
            if (image != null)
            {
                if (function.UploadFile(image, nomeImage))
                    nomeImage = function.SetCaminhoRetorno(nomeImage);
                else
                    nomeImage = ConfigurationManager.AppSettings["imagesPathSemImagem"].ToString(); /** Recebe a imagem padrão (SEM IMAGEM) **/
            }
            else
                nomeImage = ConfigurationManager.AppSettings["imagesPathSemImagem"].ToString(); /** Recebe a imagem padrão (SEM IMAGEM) **/

            return nomeImage;
        }

        [ResponseType(typeof(vwServico))]
        public IHttpActionResult GetVwServicosById(int id)
        {

            String str = "SELECT * " +
                         "FROM vw_servico AS S " +
                         "WHERE S.idServico = " + id;

            var buscar = db.Database.SqlQuery<vwServico>(str);

            return Ok(buscar);
        }

        [ResponseType(typeof(vwServico))]
        public IHttpActionResult GetVwServicos(int usuario)
        {

            String str = "SELECT * " +
                         "FROM vw_servico AS S " +
                         "WHERE S.idUsuario <> " + usuario;

            var buscar = db.Database.SqlQuery<vwServico>(str);

            return Ok(buscar);
        }

        [ResponseType(typeof(vwServico))]
        public IHttpActionResult GetVwServicosByUsuario(int usuario)
        {

            String str = "SELECT * " +
                         "FROM vw_servico AS S " +
                         "WHERE S.idUsuario = " + usuario;

            var buscar = db.Database.SqlQuery<vwServico>(str);

            return Ok(buscar);
        }
    }
}