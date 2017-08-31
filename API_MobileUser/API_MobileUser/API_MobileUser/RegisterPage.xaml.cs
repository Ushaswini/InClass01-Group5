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
    public partial class RegisterPage : ContentPage
    {
        public RegisterPage()
        {
            InitializeComponent();
        }

        private async void Register_Clicked(object sender, EventArgs e)
        {
            var service = DependencyService.Get<IShowAlert>();

            try
            {
                if ((Name.Text != null) && (Age.Text != null) && (Weight.Text != null) && (Address != null) && (Email.Text != null) && (Password.Text != null) && (ConfirmPassword.Text != null))
                {

                    if (!Password.Text.Equals(ConfirmPassword.Text))
                    {                       

                        if (service != null)
                        {
                            service.ShowMessage("Passwords need to match");
                        }
                    }
                    else
                    {
                        var user = new Models.RegisterBindingModel
                        {
                            Name = Name.Text,
                            Age = Age.Text,
                            Weight = Weight.Text,
                            Address = Address.Text,
                            Email = Email.Text,
                            Password = Password.Text,
                            ConfirmPassword = ConfirmPassword.Text
                        };
                        
                        HttpClient client = new HttpClient();

                        StringContent content = new StringContent(JsonConvert.SerializeObject(user), Encoding.UTF8,
                                    "application/json");

                        
                        HttpResponseMessage result = await client.PostAsync(Constants.REGISTER_API, new FormUrlEncodedContent(user.ToMap()));

                        if (result.IsSuccessStatusCode)
                        {
                            Navigation.InsertPageBefore(new MainPage(), this);
                            await Navigation.PopAsync().ConfigureAwait(false);
                        }
                        else
                        {
                            service?.ShowMessage("Not successful");
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
            }catch(Exception exp)
            {
               
              
            }

            
            
        }
    }
}