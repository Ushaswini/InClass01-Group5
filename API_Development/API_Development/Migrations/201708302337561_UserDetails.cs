namespace API_Development.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UserDetails : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.UserDetails",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Age = c.String(),
                        Weight = c.String(),
                        Address = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            AddColumn("dbo.AspNetUsers", "UserDetails_Id", c => c.Int());
            CreateIndex("dbo.AspNetUsers", "UserDetails_Id");
            AddForeignKey("dbo.AspNetUsers", "UserDetails_Id", "dbo.UserDetails", "Id");
            
        }
        
        public override void Down()
        {
            AddColumn("dbo.AspNetUsers", "BirthDate", c => c.String());
            AddColumn("dbo.AspNetUsers", "LastName", c => c.String());
            AddColumn("dbo.AspNetUsers", "FirstName", c => c.String());
            DropForeignKey("dbo.AspNetUsers", "UserDetails_Id", "dbo.UserDetails");
            DropIndex("dbo.AspNetUsers", new[] { "UserDetails_Id" });
            DropColumn("dbo.AspNetUsers", "UserDetails_Id");
            DropTable("dbo.UserDetails");
        }
    }
}
