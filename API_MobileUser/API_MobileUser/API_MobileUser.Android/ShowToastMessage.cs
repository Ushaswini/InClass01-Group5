using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using API_MobileUser;

namespace API_MobileUser.Droid
{
    class ShowToastMessage : IShowAlert
    {
        public void ShowMessage(string message)
        {
            Toast.MakeText(Xamarin.Forms.Forms.Context, message, ToastLength.Short).Show();
        }
    }
}