using System;
using System.Drawing;
using System.IO;
using System.Web.Mvc;
using ZXing;


namespace WebFrame.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult QRcodeDecode(){ return View();}


        [HttpPost]
        public ActionResult QRcodeDecode(FormCollection form)
        {
            try{
                byte[] imgBytes = Convert.FromBase64String(form["img"]);
                Stream stream = new MemoryStream(imgBytes);
                Result result = new BarcodeReader().Decode(new Bitmap(stream));
                return Content(Uri.EscapeDataString(result.Text));
            }catch{ return Content("no");}
        }
        public ActionResult Result(string id) { return View(); }
    }
}