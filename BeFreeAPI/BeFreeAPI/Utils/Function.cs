using System;
using System.Collections.Generic;
using System.Configuration;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.Web;

namespace BeFreeAPI.Utils
{
    public class Function
    {
        public Image Base64ToImage(string base64String)
        {
            //base64String.Replace("-", "+").Replace("_", "/").Replace("*", "=");
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
            catch (Exception err)
            {

                return null;
            }
        }

        public string SetImageName(string nomeImage)
        {
            return nomeImage;
        }

        public string SetCaminhoRetorno(string nomeImage)
        {
            Random random = new Random();
            return ConfigurationManager.AppSettings["imagesPathDownload"].ToString() + nomeImage;
        }


        public bool UploadFile(Image img, string target)
        {
            try
            {
                string path = ConfigurationManager.AppSettings["imagesPathUpload"].ToString() + target;
                //string path = ConfigurationManager.AppSettings["imagesPathTeste"].ToString() + target;
                img.Save(path,ImageFormat.Jpeg);
                return true;
            }
            catch (Exception err)
            {
                return false;
            }
        }

        public string GenerateRandomString() {

            var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            var stringChars = new char[8];
            var random = new Random();

            for (int i = 0; i < stringChars.Length; i++)
            {
                stringChars[i] = chars[random.Next(chars.Length)];
            }

            var finalString = new String(stringChars);

            return finalString;
        }

        public bool EnviaEmail(string emailTo, string subject, string body) {

            try
            {
                // Command line argument must the the SMTP host.
                SmtpClient client = new SmtpClient();
                client.Port = 587;
                client.Host = "smtp.gmail.com";
                client.EnableSsl = true;
                client.Timeout = 10000;
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                client.UseDefaultCredentials = false;
                client.Credentials = new System.Net.NetworkCredential("befree.tcc@gmail.com", "BeFreeTcc2018");

                MailMessage mm = new MailMessage("befree.tcc@gmail.com", emailTo, subject, body);
                mm.BodyEncoding = UTF8Encoding.UTF8;
                mm.DeliveryNotificationOptions = DeliveryNotificationOptions.OnFailure;

                client.Send(mm);

            }
            catch (Exception err) {
                return false;
            }

            return true;
        }

    }
}