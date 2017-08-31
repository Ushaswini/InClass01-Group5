using API_MobileUser.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Plugin.Settings.Abstractions;
using Plugin.Settings;

namespace API_MobileUser
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class UserProfilePage : ContentPage
    {
        UserInfoViewModel user;

        public  UserProfilePage()
        {
            InitializeComponent();
            

        }
        protected override void OnAppearing()
        {
            base.OnAppearing();
            InitilizeData();
        }
        private void InitilizeData() {

            this.user = App.CurrentUser;
            Name.Text = user.Name;
            Address.Text = user.Address;
            Age.Text = user.Age;
            Weight.Text = user.Weight;
            Email.Text = user.Email;
            Title = "Welcome " + user.Name + " !";
        }

        private async void Edit_Clicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new EditProfilePage(user));
        }

        private async void Logout_Clicked(object sender, EventArgs e)
        {

            var service = DependencyService.Get<IShowAlert>();

            try
            {
                HttpClient client = new HttpClient();
                client.DefaultRequestHeaders.Add("Authorization", App.Token);

                var result = await client.PostAsync(Constants.LOGOUT_PROFILE_API, null);

                var jsonResult = await result.Content.ReadAsStringAsync();
                user = JsonConvert.DeserializeObject<UserInfoViewModel>(jsonResult);

                App.AppSettings.Remove(nameof(App.Token));
                App.AppSettings.Remove(nameof(App.CurrentUser));

                Navigation.InsertPageBefore(new MainPage(), this);
                await Navigation.PopAsync().ConfigureAwait(false);

                //await Navigation.PopAsync();
                
                //await Navigation.PushAsync(new MainPage());

            }catch(Exception exp)
            {               

                if (service != null)
                {
                    service.ShowMessage(exp.Message);
                }
            }
            
        }

        protected override bool OnBackButtonPressed()
        {
            return false;
        }


    }
}