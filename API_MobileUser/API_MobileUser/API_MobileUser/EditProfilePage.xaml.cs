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

namespace API_MobileUser
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class EditProfilePage : ContentPage
    {

        UserInfoViewModel user;
        public EditProfilePage(UserInfoViewModel user)
        {
            InitializeComponent();
            this.user = user;
            InitilizeData();
        }

        private void InitilizeData()
        {
            Name.Text = user.Name;
            Address.Text = user.Address;
            Age.Text = user.Age;
            Weight.Text = user.Weight;
            Email.Text = user.Email;
        }

        private async void Save_Clicked(object sender, EventArgs e)
        {
            // save in settings as well

            var service = DependencyService.Get<IShowAlert>();

            if ((Name.Text != null) && (Age.Text != null) && (Weight.Text != null) && (Address != null) && (Email.Text != null)){
                var user = new UserInfoViewModel
                {
                    Name = Name.Text,
                    Age = Age.Text,
                    Weight = Weight.Text,
                    Address = Address.Text,
                    Email = Email.Text,
                };

                try
                {
                    HttpClient client = new HttpClient();
                    client.DefaultRequestHeaders.Add("Authorization", App.Token);

                    HttpResponseMessage result = await client.PostAsync(Constants.UPDATE_PROFILE_API, new FormUrlEncodedContent(user.ToMap()));

                    if (result.IsSuccessStatusCode)
                    {                       
                        App.CurrentUser = user;
                        App.AppSettings.AddOrUpdateValue("CurrentUser", JsonConvert.SerializeObject(user));
                        service?.ShowMessage("Saved Changes");
                        //Navigation.InsertPageBefore(new UserProfilePage(user), this);
                        //await Navigation.PopAsync().ConfigureAwait(false);
                    }
                    else
                    {                      
                        if (service != null)
                        {
                            service.ShowMessage("Saving failed");
                        }
                    }
                }
                catch (Exception exp)
                {                    
                    if (service != null)
                    {
                        service?.ShowMessage(exp.Message);
                    }
                }
            }
            else
            {
                if (service != null)
                {
                    service.ShowMessage("Required all fields");
                }
            }      

        }

        private async void ChangePassword_Clicked(object sender, EventArgs e)
        {
            var service = DependencyService.Get<IShowAlert>();

            if ((OldPassword.Text != null) && (NewPassword.Text != null) && (ConfirmNewPassword.Text != null))
            {
                if (!NewPassword.Text.Equals(ConfirmNewPassword.Text))
                {
                    service?.ShowMessage("Passwords must match");
                }
                else
                {
                    try
                    {
                        HttpClient client = new HttpClient();
                        client.DefaultRequestHeaders.Add("Authorization", App.Token);

                        var model = new Models.ChangePasswordBindingModel{
                            OldPassword = OldPassword.Text,
                            NewPassword=NewPassword.Text,
                            ConfirmPassword=ConfirmNewPassword.Text
                        };

                        HttpResponseMessage result = await client.PostAsync(Constants.CHANGE_PASSWORD_API, new FormUrlEncodedContent(model.ToMap()));

                        if (result.IsSuccessStatusCode)
                        {
                            service?.ShowMessage("Changed Password");
                            await Logout();
                        }
                        else
                        {
                            if (service != null)
                            {
                                service.ShowMessage("Couldn't logout. Logout explicitly.");
                            }
                        }
                    }
                    catch (Exception exp)
                    {
                        if (service != null)
                        {
                            service?.ShowMessage(exp.Message);
                        }
                    }
                }
            }
            else
            {
                service?.ShowMessage("Required all fields");
            }
        }


        private async Task Logout()
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

                await Navigation.PopToRootAsync();
                await Navigation.PushAsync(new MainPage());

            }
            catch (Exception exp)
            {

                if (service != null)
                {
                    service.ShowMessage(exp.Message);
                }
            }
        }
    }
}