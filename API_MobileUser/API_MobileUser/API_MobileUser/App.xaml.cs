using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime;
using System.Text;


using Xamarin.Forms;
using Plugin.Settings.Abstractions;
using Plugin.Settings;
using Newtonsoft.Json;
using API_MobileUser.Models;

namespace API_MobileUser
{
    public partial class App : Application
    {

        internal static ISettings AppSettings => CrossSettings.Current;

        public static string Token
        {
            get => AppSettings.GetValueOrDefault(nameof(Token), string.Empty);
            set => AppSettings.AddOrUpdateValue(nameof(Token), value);
        }

        public static UserInfoViewModel CurrentUser
        {
            get
            {
               string jsonUser =  AppSettings.GetValueOrDefault(nameof(CurrentUser), string.Empty);
                if (!jsonUser.Equals(string.Empty))
                {
                    return JsonConvert.DeserializeObject<UserInfoViewModel>(jsonUser); ;
                }
                else
                {
                    return null;
                }
            }
            set
            {                
                AppSettings.AddOrUpdateValue(nameof(CurrentUser), JsonConvert.SerializeObject(value));
            }
        }

        public static INavigation Navigation { get; private set; }
        


        public App()
        {
            InitializeComponent();

            if (!Token.Equals(string.Empty))
            {
                
                MainPage = new NavigationPage ( new UserProfilePage() );
                Navigation = MainPage.Navigation;
            }
            else
            {
                MainPage = new NavigationPage(new MainPage());
                Navigation = MainPage.Navigation;
            }            
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
