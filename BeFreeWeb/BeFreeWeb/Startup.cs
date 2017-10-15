using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(BeFreeWeb.Startup))]
namespace BeFreeWeb
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
