using System;
using System.Collections.Generic;
using System.Configuration;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;

namespace BeFreeAPI.Utils
{
    public class Function
    {
        public Image Base64ToImage(string base64String)
        {            
            base64String.Replace("-", "+").Replace("_", "/").Replace("*", "=");
            try
            {
                // Convert base 64 string to byte[]
                byte[] imageBytes = Convert.FromBase64String(base64String);
                // Convert byte[] to Image
                using (var ms = new MemoryStream(imageBytes, 0, imageBytes.Length))
                {
                    Image image = Image.FromStream(ms, true);
                    return image;

                }
            }
            catch (Exception err) {

                return null;
            }
        }

        public string SetImageName(string nomeImage)
        {
            return nomeImage ;
        }

        public string SetCaminhoRetorno (string nomeImage)
        {
            Random random = new Random();
            return ConfigurationManager.AppSettings["imagesPathDownload"].ToString() + nomeImage;
        }


        public bool UploadFile(Image img, string target)
        {
            try
            {
                string path = ConfigurationManager.AppSettings["imagesPathUpload"].ToString();
                img.Save(path + target);
                return true;
            }
            catch (Exception err)
            {
                return false;
            }
        }
    }
}