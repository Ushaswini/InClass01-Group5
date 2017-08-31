using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace API_MobileUser.Models
{
    public class ChangePasswordBindingModel
    {
        
        public string OldPassword { get; set; }
        
        public string NewPassword { get; set; }
        
        public string ConfirmPassword { get; set; }
        public Dictionary<string, string> ToMap()
        {

            Dictionary<string, string> parameters = new Dictionary<string, string>();

            parameters.Add("OldPassword", this.OldPassword);
            parameters.Add("NewPassword", this.NewPassword);
            parameters.Add("ConfirmPassword", this.ConfirmPassword);
            

            return parameters;
        }
    }


    public class RegisterBindingModel
    {       
        public string Email { get; set; }

        public string Password { get; set; }
        
        public string ConfirmPassword { get; set; }
        
        public string Name { get; set; }
        
        public string Age { get; set; }
        
        public string Weight { get; set; }
        
        public string Address { get; set; }

        public Dictionary<string,string> ToMap()
        {

            Dictionary<string, string> parameters = new Dictionary<string, string>();

            parameters.Add("Name", this.Name);
            parameters.Add("Age", this.Age);
            parameters.Add("Address", this.Address);            
            parameters.Add("Weight", this.Weight);
            parameters.Add("Email", this.Email);
            parameters.Add("Password", this.Password);
            parameters.Add("ConfirmPassword", this.ConfirmPassword);

            return parameters;
        }

    }


    public class UserInfoViewModel
    {

        public string Age { get; set; }
        
        public string Name { get; set; }
        
        public string Address { get; set; }
        
        public string Weight { get; set; }        

        public string Email { get; set; }

        public bool HasRegistered { get; set; }

        public string LoginProvider { get; set; }

        public Dictionary<string, string> ToMap()
        {

            Dictionary<string, string> parameters = new Dictionary<string, string>();

            parameters.Add("Name", this.Name);
            parameters.Add("Age", this.Age);
            parameters.Add("Address", this.Address);
            parameters.Add("Weight", this.Weight);
            parameters.Add("Email", this.Email);
            

            return parameters;
        }
    }

    public class TokenModel
    {
        public string Access_Token { get; set; }
    }
}
